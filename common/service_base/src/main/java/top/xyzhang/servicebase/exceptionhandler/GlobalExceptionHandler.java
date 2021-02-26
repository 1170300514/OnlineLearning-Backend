package top.xyzhang.servicebase.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.xyzhang.commonutils.R;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j // 将错误日志输出到文件中
public class GlobalExceptionHandler {

    // 指定出现什么异常会执行该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 使该方法能够返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了 ArithmeticException 异常处理");
    }

    // 自定义异常处理
    @ExceptionHandler(MyTestException.class)
    @ResponseBody
    public R error(MyTestException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
