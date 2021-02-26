package top.xyzhang.eduservice.controller;

import org.springframework.web.bind.annotation.*;
import top.xyzhang.commonutils.R;

@RestController
@RequestMapping("/eduService/user")
@CrossOrigin // 解决跨域问题
public class EduLoginController {

    // login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]")
                .data("name", "myName")
                .data("avatar", "https://api.prodless.com/avatar.png");
    }
}
