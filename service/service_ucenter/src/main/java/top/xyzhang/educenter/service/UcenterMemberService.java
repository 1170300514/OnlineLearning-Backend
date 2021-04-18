package top.xyzhang.educenter.service;

import top.xyzhang.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xyzhang.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xyzhang
 * @since 2021-03-23
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);

    UcenterMember saveWxLogin(String openid, String nickname, String headimgurl);

    Integer countRegister(String day);
}
