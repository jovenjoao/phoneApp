package com.masmovil.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.masmovil.user.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserApplicationWebTest {

	@Autowired
	public MongoTemplate mongoTemplate;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void prepareTest() {
		mongoTemplate.remove(User.class);

		User user1 = new User();
		user1.setEmail("a@a.es");

		mongoTemplate.save(user1);
	}

	@Test
	public void getUser_whenUserExist_shouldReturnUser() throws Exception {

		mockMvc.perform(get("/user/a@a.es")).andExpect(status().isOk()).andExpect(content().json("{ email: 'a@a.es'}"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getUser_whenUserNotExist_shouldReturnUser() throws Exception {

		mockMvc.perform(get("/user/b@a.es")).andExpect(status().is(404)).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void createUser_whenUserNotExists_shouldCreateUser () throws Exception {
		
		mockMvc.perform(post("/user").content("{\"email\":\"b@a.es\"}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("{ email: 'b@a.es'}"))
				.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void createUser_whenUserExists_shouldReturnError () throws Exception {
		
		mockMvc.perform(post("/user").content("{\"email\":\"a@a.es\"}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isConflict())
				.andDo(MockMvcResultHandlers.print());
		
	}
	
	
}
