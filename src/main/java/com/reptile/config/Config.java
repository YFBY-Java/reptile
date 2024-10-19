package com.reptile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 注释：配置类
 * </p>
 * Since: 2024/7/28
 * Author: ChaserFire
 */
@Configuration
public class Config {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
