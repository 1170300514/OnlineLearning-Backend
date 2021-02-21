package top.xyzhang.servicebase.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xyzhang.commonutils.R;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // 指定出现什么异常会执行该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 使该方法能够返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }
}
