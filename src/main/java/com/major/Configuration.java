package com.major;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "com.major")

public class Configuration {


    @Bean("gson")
    Gson createGson(){//将外部的Gson添加进容器中。
        return new Gson();
    }





}

