package top.liubaiblog.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.liubaiblog.admin.interceptor.FormRepeatInterceptor;
import top.liubaiblog.admin.interceptor.user.LoginInterceptor;

/**
 * @author 刘佳俊
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public FormRepeatInterceptor formRepeatInterceptor() {
        return new FormRepeatInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login");

        registry.addInterceptor(formRepeatInterceptor())
                .addPathPatterns("/**");
    }


}
