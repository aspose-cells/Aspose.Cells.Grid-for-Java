package com.example.gridjsdemo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.aspose.gridjs.Config;
@ComponentScan(basePackages = { "com.example.gridjsdemo", "com.aspose.gridjs" })
//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

	public static void main(String[] args) throws Exception {
         //Config.RedactionUseClientGenerateId=true;
		 SpringApplication.run(DemoApplication.class, args);
	}
	
}
