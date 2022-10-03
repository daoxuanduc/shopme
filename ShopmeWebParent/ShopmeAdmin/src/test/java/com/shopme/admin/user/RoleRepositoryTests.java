package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.shopme.admin.user.repositories.RoleRepository;
import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest  //
@AutoConfigureTestDatabase(replace = Replace.NONE) //for testing real DB, not in-memory DB
@Rollback(false)
public class RoleRepositoryTests {
	@Autowired //let Spring framework inject an instance of RoleRepository interface
	//sau khi tạo interface RoleRepository, Spring sẽ quét các class cùng cấp hoặc thấp hơn để tìm các
	//instance đánh dấu @Component (bao gồm cả @Repository phục vụ truy xuất dữ liệu). @Autowired ở đây để lấy được các instance này
	private RoleRepository roleRepository;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role saveRole = roleRepository.save(roleAdmin);
		
		assertThat(saveRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson = new Role("Salesperson", "manage product price, "
				+ "customers, shipping, orders and sales report");
		
		Role roleEditor = new Role("Editor", "manage categories, brands, "
				+ "products, articles and menus");
		
		Role roleShiper = new Role("Shipper", "view products, view orders, "
				+ "and update order status");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");

		roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShiper, roleAssistant));
	}
}
