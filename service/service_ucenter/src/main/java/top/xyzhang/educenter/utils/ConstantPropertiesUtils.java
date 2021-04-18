package top.xyzhang.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${callback.redirect}")
    private String CallBackRedirect;

    public static String CALLBACKRE_DIRECT;

    @Override
    public void afterPropertiesSet() throws Exception {
        CALLBACKRE_DIRECT = CallBackRedirect;
    }
}
