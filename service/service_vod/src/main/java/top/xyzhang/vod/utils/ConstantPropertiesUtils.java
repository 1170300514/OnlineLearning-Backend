package top.xyzhang.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String Key_ID;
    public static String Key_Secret;

    @Override
    public void afterPropertiesSet() throws Exception {
        Key_ID = keyid;
        Key_Secret = keysecret;
    }
}
