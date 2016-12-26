package com.tt.web.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tt on 2016/12/16.
 */
@Configuration
public class KapchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() throws IOException {
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("kapcha.properties"));
        Config config = new Config(props);
        
        DefaultKaptcha ret = new DefaultKaptcha();
        ret.setConfig(config);
        return ret;

    }

}
