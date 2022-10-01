package com.shopme.admin.user;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.admin.user.repositories.UserRepository;
import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userDucDX = new User("daoxuanduc3006@gmail.com","12345","Duc","Dao Xuan");
		userDucDX.addRole(roleAdmin);
		
		User savedUser = repo.save(userDucDX);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userLyly = new User("maihuongly2000@gmail.com","lyly123","Ly","Mai Huong");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		userLyly.addRole(roleEditor);
		userLyly.addRole(roleAssistant);
		
		User savedUser = repo.save(userLyly);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userDuc = repo.findById(2).get();
		System.out.println(userDuc);
		assertThat(userDuc).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userDuc = repo.findById(2).get();
		userDuc.setEnabled(true);
		userDuc.setEmail("ducdx@hblab.vn");
		
		repo.save(userDuc);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userLy = repo.findById(3).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userLy.getRoles().remove(roleEditor);
		userLy.addRole(roleSalesperson);
		
		repo.save(userLy);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 3;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "daoxuanduc3699@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 2;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, false);
	}

	@Test
	public void testEnableUser() {
		Integer id = 2;
		repo.updateEnabledStatus(id, true);
	}

	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);

		List<User> listUsers =  page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isEqualTo(pageSize);
	}

	@Test
	public void testSearchUser() {
		String keyword = "bruce";

		int pageNumber = 0;
		int pageSize = 4;

		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);

		List<User> listUsers =  page.getContent();

		listUsers.forEach(user -> System.out.println(user));

		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
