package com.heida.bysjsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:applicationContext-solrj.xml"})
public class BysjSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(BysjSearchApplication.class, args);
	}

}
