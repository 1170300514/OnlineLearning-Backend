package top.xyzhang.eduorder.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import top.xyzhang.commonutils.R;
import top.xyzhang.eduorder.service.PayLogService;

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
        return R.ok();
    }
}

