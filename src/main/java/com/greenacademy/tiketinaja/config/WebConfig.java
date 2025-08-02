package com.greenacademy.tiketinaja.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.greenacademy.tiketinaja.interceptor.AdminInterceptor;
import com.greenacademy.tiketinaja.interceptor.AuthInterceptor;
import com.greenacademy.tiketinaja.interceptor.OrganizerInterceptor;



@Configuration
public class WebConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;
    private OrganizerInterceptor organizerInterceptor;
    private AdminInterceptor adminInterceptor;

    public WebConfig(AuthInterceptor authInterceptor, OrganizerInterceptor organizerInterceptor, AdminInterceptor adminInterceptor) {
        this.authInterceptor = authInterceptor;
        this.organizerInterceptor = organizerInterceptor;
        this.adminInterceptor = adminInterceptor;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/uploads/**","/auth/login", "/auth/register", "/auth/verify/**", "/auth/new-verify", "/public/**", "/midtrans/**");
        registry.addInterceptor(organizerInterceptor)
                .addPathPatterns("/organizer/**")
                .excludePathPatterns("/organizer/register");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

}
