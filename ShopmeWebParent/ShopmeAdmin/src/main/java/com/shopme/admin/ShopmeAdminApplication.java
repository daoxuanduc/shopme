package com.shopme.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.shopme.common.entity", "com.shopme.admin.user"}) //Error: Not a managed type: class com.shopme.common.entity.Role
public class ShopmeAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopmeAdminApplication.class, args);
	}

}
