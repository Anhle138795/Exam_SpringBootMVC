package net.codejava.config;

import net.codejava.interceptor.AdminInterceptor;
import net.codejava.interceptor.CustomerInterceptor;
import net.codejava.interceptor.EmployeeInterceptor;
import net.codejava.interceptor.LoginInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new LoginInterceptor())
        .excludePathPatterns(
                "/admin/login",
                "/admin/chklogin",
                "/admin/forgot-password",
                "/admin/reset-password",
                "/admin/register",
                "/admin/register/**",
                "/admin/verify", 
                "/employees/login",
                "/employees/chklogin",
                "/employees/forgot-password",
                "/employees/reset-password",
                "/employees/register",
                "/employees/register/**",
                "/employees/verify"  ,
                "/customer/login",
                "/customer/chklogin",
                "/customer/forgot-password",
                "/customer/reset-password",
                "/customer/register",
                "/customer/register/**",
                "/customer/verify",
                "/css/**",
                "/js/**",
                "/images/**",
                "/error"
        );
    	
        registry.addInterceptor(new CustomerInterceptor())
            .addPathPatterns("/customer/**")
            .excludePathPatterns(
                "/customer/login",
                "/customer/chklogin",
                "/customer/forgot-password",
                "/customer/reset-password",
                "/customer/register",
                "/customer/register/**",
                "/customer/verify",
                "/css/**",
                "/js/**",
                "/images/**",
                "/error"
            );

        registry.addInterceptor(new AdminInterceptor())
            .addPathPatterns("/admin/**")
            .excludePathPatterns(
                "/admin/login",
                "/admin/chklogin",
                "/admin/forgot-password",
                "/admin/reset-password",
                "/admin/register",
                "/admin/register/**",
                "/admin/verify",
                "/css/**",
                "/js/**",
                "/images/**",
                "/error"
            );


        registry.addInterceptor(new EmployeeInterceptor())
            .addPathPatterns("/employees/**")
            .excludePathPatterns(
                "/employees/login",
                "/employees/chklogin",
                "/employees/forgot-password",
                "/employees/reset-password",
                "/employees/register",
                "/employees/register/**",
                "/employees/verify",
                "/css/**",
                "/js/**",
                "/images/**",
                "/error"
            );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:uploads/")
        .setCachePeriod(3600)
        .resourceChain(true);
    }
}