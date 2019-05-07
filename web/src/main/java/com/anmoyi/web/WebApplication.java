package com.anmoyi.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//scanBasePackages="com.ticket"  解决serviceImpl不能注入的问题
@SpringBootApplication(scanBasePackages={"com.anmoyi"})
//生成dao对应的mapper
@MapperScan("com.anmoyi.model.dao")

public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
