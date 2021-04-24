package top.xyzhang.eduorder.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${wx.pay.appid}")
    private String appid;
    @Value("${wx.pay.partner}")
    private String partner;
    @Value("${wx.pay.partnerkey}")
    private String partnerkey;
    @Value("${wx.pay.notifyurl}")
    private String notifyurl;

    public static String APPID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APPID = appid;
        PARTNER = partner;
        PARTNER_KEY = partnerkey;
        NOTIFY_URL = notifyurl;
    }
}
