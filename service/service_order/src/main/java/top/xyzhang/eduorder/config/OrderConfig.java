package top.xyzhang.eduorder.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("top.xyzhang.eduorder.mapper")
public class OrderConfig {
}
