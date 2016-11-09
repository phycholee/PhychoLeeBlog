package com.phycholee.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
}
