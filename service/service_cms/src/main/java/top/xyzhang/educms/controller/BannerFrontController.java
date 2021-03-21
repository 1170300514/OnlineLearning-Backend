package top.xyzhang.educms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xyzhang.commonutils.R;
import top.xyzhang.educms.entity.CrmBanner;
import top.xyzhang.educms.service.CrmBannerService;

import java.util.List;

/**
 * 前台Banner展示
 * @author testjava
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/educms/bannerFront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.getAllBanner();
        return R.ok().data("allbanners",list);
    }
}

