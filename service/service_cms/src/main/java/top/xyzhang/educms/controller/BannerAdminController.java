package top.xyzhang.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;
import top.xyzhang.educms.entity.CrmBanner;
import top.xyzhang.educms.service.CrmBannerService;

/**
 * banner后台管理
 *
 * @author testjava
 * @since 2021-03-21
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.page(pageBanner, null);
        return R.ok().data("items", pageBanner.getRecords())
                .data("total", pageBanner.getTotal());
    }

    @GetMapping("getBanner/{id}")
    public R getBannerById(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

    /**
     * 添加
     * @param banner
     * @return
     */
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @DeleteMapping("remove/{id}")
    public R removeBanner(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
}

