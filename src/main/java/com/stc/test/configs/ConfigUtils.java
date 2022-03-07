package com.stc.test.configs;

import com.stc.vietnamstringutils.VietnameseStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigUtils {
    @Bean
    public VietnameseStringUtils vietnameseStringUtils() {
        return new VietnameseStringUtils();
    }
}
