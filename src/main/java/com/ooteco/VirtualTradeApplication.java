package com.ooteco;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@MapperScan("com.ooteco.mapper")
public class VirtualTradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualTradeApplication.class, args);
	}
}
