package top.xyzhang.eduorder.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.service.PayLogService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author xyzhang
 * @since 2021-04-17
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        // 返回信息 包括二维码地址
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("=====查询订单状态map集合"+map);
        if (map == null) {
            return R.error().message("支付出错");
        }
        // 若返回map中不为空 通过map获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {
            // 添加记录到支付表 更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

