package net.codejava.config;

import net.codejava.interceptor.AdminInterceptor;
import net.codejava.interceptor.EmployeeInterceptor;
import net.codejava.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Đăng ký LoginInterceptor cho tất cả các đường dẫn trừ các đường dẫn ngoại lệ
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/admin/login",
                        "/admin/chklogin",
                        "/admin/register",
                        "/admin/register/**",
                        "/admin/verify", 
                        "/employees/login",
		                "/employees/chklogin",
		                "/employees/register",
		                "/employees/register/**",
		                "/employees/verify"  ,
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/error"
                );
        
        // Đăng ký AdminInterceptor cho các đường dẫn admin trừ các đường dẫn ngoại lệ
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/admin/login",
                        "/admin/chklogin",
                        "/admin/register",
                        "/admin/register/**",
                        "/admin/verify"    // Thêm đường dẫn verify vào ngoại lệ
                );
        registry.addInterceptor(new EmployeeInterceptor())
		        .addPathPatterns("/admin/**")
		        .excludePathPatterns(
		                "/employees/login",
		                "/employees/chklogin",
		                "/employees/register",
		                "/employees/register/**",
		                "/employees/verify"  
        );
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Cấu hình các tài nguyên tĩnh (CSS, JS, Images)
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}

