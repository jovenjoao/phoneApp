package com.masmovil.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.management.InstanceNotFoundException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.masmovil.order.service.PhoneService;
import com.masmovil.order.service.UserService;
import com.masmovil.order.service.model.Phone;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderApplicationWebTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhoneService phoneService;

	@MockBean
	private UserService userService;

	
	@Test
	public void saveOrderTest_validRequest_shouldReturnOrderSaved() throws Exception {

		Phone p =new Phone();
		p.setPrice(10d);
		
		Mockito.when(phoneService.getPhoneData(Mockito.anyString())).thenReturn(p);
		Mockito.when(userService.getUser("a@a.es")).thenThrow(new InstanceNotFoundException());
		
		mockMvc.perform(post("/order")
				.content("{\"email\":\"a@a.es\",\"name\":\"a\",\"surname\":\"b\",\"phones\":[\"ref_1\",\"ref_2\",\"ref_3\"]}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(
						"{\"userId\":\"a@a.es\",\"phones\":[\"ref_1\",\"ref_2\",\"ref_3\"],\"prize\":30.0}"))
				.andDo(MockMvcResultHandlers.print());

	}
	
	/*@Test
	public void saveOrderTest_invalidRequest_shouldReturnException() throws Exception {

		mockMvc.perform(post("/order")
				.content("{\"email\":\"a@a.es\",\"surname\":\"b\",\"phones\":[\"ref_1\",\"ref_2\",\"ref_3\"]}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().json(
						"[{ id: 'p1',name: 'n1',description:'d1',price:1.0},{ id: 'p2',name: 'n2',description:'d2',price:2.0}]"))
				.andDo(MockMvcResultHandlers.print());

	}*/

}
