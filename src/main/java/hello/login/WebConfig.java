package hello.login;
import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    //인터셉터 등록.
    @Override
    public void addInterceptors(InterceptorRegistry registry){

        //log 인터셉터 등록
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")            //모든 경로 허용.
                .excludePathPatterns("/css/**", "/*.ico","/error");   //제외 경로

        //login 인터셉터 등록
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"
                );
    }

    //LogFilter 등록
   // @Bean
    public FilterRegistrationBean logFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

    //LoginCheckFilter 등록
    //@Bean
    public FilterRegistrationBean loginCheckFilter() {

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);               //필터 순서 2번째.
        filterRegistrationBean.addUrlPatterns("/*");    //모든 곳에 다 적용 ( LoginCheckFilter의 witeList에 있는 url은 제외 됨)

        return filterRegistrationBean;
    }
}