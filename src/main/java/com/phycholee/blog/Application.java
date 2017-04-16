package com.phycholee.blog;

import com.phycholee.blog.authorization.config.Constants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by PhychoLee on 2016/11/3 19:39.
 * Description:
 */
@SpringBootApplication
@ComponentScan
@EnableTransactionManagement    //开启事务注解
@MapperScan("com.phycholee.blog.dao")
public class Application{

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

    //设置session超时
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.setSessionTimeout(Constants.TOKEN_EXPIRES_MINUTES * 60);//单位为S
            }
        };
    }
}
