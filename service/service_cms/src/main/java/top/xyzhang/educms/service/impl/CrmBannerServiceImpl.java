package top.xyzhang.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xyzhang.educms.entity.CrmBanner;
import top.xyzhang.educms.mapper.CrmBannerMapper;
import top.xyzhang.educms.service.CrmBannerService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-21
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    /**
     * 查询全部Banner信息
     * @return
     */
//    @Cacheable(key = "'selectIndexList'",value = "banner") // Redis缓存
    @Override
    public List<CrmBanner> getAllBanner() {
        // 根据id降序排列 取前两条
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
