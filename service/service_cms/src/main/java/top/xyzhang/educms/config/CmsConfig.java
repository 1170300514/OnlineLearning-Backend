package top.xyzhang.educms.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("top.xyzhang.educms.mapper")
public class CmsConfig {
}
