package top.xyzhang.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import top.xyzhang.eduorder.entity.Order;
import top.xyzhang.eduorder.entity.PayLog;
import top.xyzhang.eduorder.mapper.PayLogMapper;
import top.xyzhang.eduorder.service.OrderService;
import top.xyzhang.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xyzhang.eduorder.utils.ConstantPropertiesUtils;
import top.xyzhang.eduorder.utils.HttpClient;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author xyzhang
 * @since 2021-04-17
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    /**
     * 生成微信支付二维码
     * @param orderNo
     * @return
     */
    @Override
    public Map createNative(String orderNo) {
        try {
            //1 订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            //2 map传递生成二维码需要的信息
            Map<String, String> map = new HashMap<>();
            map.put("appid", ConstantPropertiesUtils.APPID);
            map.put("mch_id", ConstantPropertiesUtils.PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee()
                    .multiply(new BigDecimal("100")).longValue()+"");
            // TODO: 更换为实际域名
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", ConstantPropertiesUtils.NOTIFY_URL);
            map.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //4 设置xml格式的参数
            client.setXmlParam(WXPayUtil
                    .generateSignedXml(map, ConstantPropertiesUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //返回结果
            String xmlContent = client.getContent();
            // 将xml转换为map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xmlContent);
            Map ans = new HashMap<>();
            ans.put("out_trade_no", orderNo);
            ans.put("course_id", order.getCourseId());
            ans.put("total_fee", order.getTotalFee());
            ans.put("result_code", resultMap.get("result_code"));
            ans.put("code_url", resultMap.get("code_url"));
            return ans;
        } catch (Exception e) {
            throw new MyTestException(20001, "生成微信支付二维码失败");
        }
    }

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1 封装参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", ConstantPropertiesUtils.APPID);
            map.put("mch_id", ConstantPropertiesUtils.PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", orderNo);

            //2 发送HttpClient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil
                    .generateSignedXml(map, ConstantPropertiesUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String content = client.getContent();
            Map<String, String> xmlToMap = WXPayUtil.xmlToMap(content);
            // TODO: test xml
            System.out.println("=========微信返回数据xmlToMap"+xmlToMap);
            return xmlToMap;
        } catch (Exception e) {
            throw new MyTestException(20001, "查询订单支付状态失败");
        }
    }

    /**
     * 更新订单表
     * @param map
     */
    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        // 获取订单id
        String orderNo = map.get("out_trade_no");
        // 根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);

        // 将订单信息更新为已支付
        if (order.getStatus().intValue() == 1) return;
        order.setStatus(1);
        orderService.updateById(order);

        // 记录支付日志
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo); // 订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1); // 支付类型
        payLog.setTotalFee(order.getTotalFee());  // 总金额

        payLog.setTradeState(map.get("trade_state")); // 支付状态
        payLog.setTransactionId(map.get("transaction_id")); // 流水号
        payLog.setAttr(JSONObject.toJSONString(map)); // 其他属性
        baseMapper.insert(payLog);
    }
}
