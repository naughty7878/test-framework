package com.test.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 全局跨域配置
 * 注意：前端从网关进行调用时需要配置
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter(){

        // 配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 配置跨域
        corsConfiguration.addAllowedHeader("*");        //允许任意请求头跨域
        corsConfiguration.addAllowedMethod("*");        //允许任何请求方式跨域
        corsConfiguration.addAllowedOriginPattern("*"); //允许任意请求来源跨域
        corsConfiguration.setAllowCredentials(true);    //允许携带cookie信息跨域
        corsConfiguration.setMaxAge(3600L);             //允许跨域时间(秒)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}
