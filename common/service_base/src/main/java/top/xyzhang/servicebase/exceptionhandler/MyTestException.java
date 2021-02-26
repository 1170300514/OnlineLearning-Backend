package top.xyzhang.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyTestException extends RuntimeException {

    // 异常状态码
    private Integer code;

    // 异常信息
    private String msg;
}
