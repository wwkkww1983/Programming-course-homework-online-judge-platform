package com.xloya.pldp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.xloya.pldp.mapper") //扫描自定义的Mapper接口，并注入对应的SqlSession实例
public class PldpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PldpApplication.class, args);
    }

}
