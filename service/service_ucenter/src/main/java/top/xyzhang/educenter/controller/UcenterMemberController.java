package top.xyzhang.educenter.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.JwtUtils;
import top.xyzhang.commonutils.R;
import top.xyzhang.educenter.entity.UcenterMember;
import top.xyzhang.educenter.entity.vo.RegisterVo;
import top.xyzhang.educenter.service.UcenterMemberService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xyzhang
 * @since 2021-03-23
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    /**
     * 登录
     */
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member) {
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 注册
     * @param registerVo
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    /**
     * 查询某一天的注册人数
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day) {
        int num = memberService.countRegister(day);
        return R.ok().data("num", num);
    }
}


