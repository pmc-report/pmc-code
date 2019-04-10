package gean.pmc_report_api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gean.pmc_report_common.common.xss.XssFilter;

import javax.servlet.DispatcherType;

/**
 * Filter配置
 *
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }
}
