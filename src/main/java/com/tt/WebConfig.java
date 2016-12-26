package com.tt;

import com.tt.ext.web.MultipartFileResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.util.List;

/**
 * Created by tt on 2016/11/20.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tt.web")
public class WebConfig extends WebMvcConfigurerAdapter{
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver("/WEB-INF/views/",".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        MultipartFileResolver resolver = new MultipartFileResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxInMemorySize(1024*1024);
        resolver.setMaxUploadSize(1024*1024*20000);
        resolver.setMaxUploadSizePerFile(1024*1024*5000);
        resolver.setUploadTempDir(new FileSystemResource("/tmp"));

        return resolver;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/WEB-INF/resources/");//TODO 设置静态资源缓存时间.setCachePeriod(31556926);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.extendHandlerExceptionResolvers(exceptionResolvers);
    }
}
