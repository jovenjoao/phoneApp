package com.masmovil.phone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.masmovil.phone.model.Phone;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneApplicationWebTest {

	@Autowired
	public MongoTemplate mongoTemplate;
	
    @Autowired
    private MockMvc mockMvc;

	
	@BeforeEach
	public void prepareTest () {
		mongoTemplate.remove(Phone.class);

		Phone p1 = new Phone ();
		p1.setId("p1");		
		p1.setName("n1");
		p1.setDescription("d1");
		p1.setPrice(1d);
		
		mongoTemplate.save(p1);
		
		Phone p2 = new Phone ();
		p2.setId("p2");		
		p2.setName("n2");
		p2.setDescription("d2");
		p2.setPrice(2d);
		
		mongoTemplate.save(p2);
	}
	
	@Test
	public void getAllPhones () throws Exception {
		
		mockMvc.perform(get("/phone"))
		.andExpect(status().isOk()).andExpect(content().json("[{ id: 'p1',name: 'n1',description:'d1',price:1.0},{ id: 'p2',name: 'n2',description:'d2',price:2.0}]")).andDo(MockMvcResultHandlers.print());
	}
	
	
	@Test
	public void getPhoneData_whenPhoneExists_shouldReturnPhone () throws Exception {
		
		mockMvc.perform(get("/phone/p1"))
		.andExpect(status().isOk()).andExpect(content().json("{ id: 'p1',name: 'n1',description:'d1',price:1.0}")).andDo(MockMvcResultHandlers.print());
	}
	
	
	@Test
	public void getPhoneData_whenPhoneNotExists_shouldReturn404 () throws Exception {
		
		mockMvc.perform(get("/phone/p3"))
		.andExpect(status().is(404)).andDo(MockMvcResultHandlers.print());
	}

}
