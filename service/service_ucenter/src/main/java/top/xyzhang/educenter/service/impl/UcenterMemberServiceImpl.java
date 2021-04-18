package top.xyzhang.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import top.xyzhang.commonutils.JwtUtils;
import top.xyzhang.commonutils.MD5;
import top.xyzhang.educenter.entity.UcenterMember;
import top.xyzhang.educenter.entity.vo.RegisterVo;
import top.xyzhang.educenter.mapper.UcenterMemberMapper;
import top.xyzhang.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.xyzhang.servicebase.exceptionhandler.MyTestException;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xyzhang
 * @since 2021-03-23
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 登录
     * @param member
     * @return
     */
    @Override
    public String login(UcenterMember member) {
        // 获取登录手机号密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyTestException(20001, "登录失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) {
            throw new MyTestException(20001, "手机号不存在 登录失败");
        }

        // 判断密码 数据库中存储的密码是MD5加密后的密码
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new MyTestException(20001, "密码错误 登录失败");
        }

        // 判断是否被禁用
        if (ucenterMember.getIsDisabled()) {
            throw new MyTestException(20001, "账户被禁用");
        }

        //登录成功 生成token字符串 使用jwt工具
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    /**
     * 注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册的数据
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)) {
            throw new MyTestException(20001, "注册失败");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)) {
            throw new MyTestException(20001,"注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new MyTestException(20001,"注册失败");
        }

        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    /**
     * 根据openid查询
     * @param openid
     * @return
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    /**
     * 微信登陆
     * @param openid
     * @param nickname
     * @param headimgurl
     */
    @Override
    public UcenterMember saveWxLogin(String openid, String nickname, String headimgurl) {
        UcenterMember member = new UcenterMember();
        member.setOpenid(openid);
        member.setNickname(nickname);
        member.setAvatar(headimgurl);
        baseMapper.insert(member);
        return member;
    }

    /**
     * 计算某一天的注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegister(String day) {
        Integer num = baseMapper.countRegisterDay(day);
        return num;
    }
}
