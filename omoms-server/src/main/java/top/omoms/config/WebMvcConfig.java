package top.omoms.config;

import org.springframework.context.annotation.Configuration;
import top.omoms.intercepter.AuthInterceptor;
import jakarta.annotation.Resource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    private static final List<String> EXCLUDE_PATH_PATTERNS =  new ArrayList<>(Arrays.asList(
            "/auth/**",
            "/index/**",
            "/course/intro",
            "/course/part/list",
            "/course/part/info"
    ));


    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH_PATTERNS)
        ;
    }

}
