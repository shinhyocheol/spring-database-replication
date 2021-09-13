//package com.rplication.app;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.replication.app.api.product.domain.entity.Product;
//import com.replication.app.api.product.domain.repository.ProductRepository;
//import com.replication.app.api.product.dto.ProductRegistDto;
//import com.replication.app.api.product.service.ProductService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class SpringDatabaseReplicationApplicationTests {
//
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Autowired
//	private WebApplicationContext context;
//
//	@BeforeAll
//	public void setUp() {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//				.addFilters(new CharacterEncodingFilter("UTF-8", true))
//				.alwaysDo(print())
//				.build();
//	}
//
//
////	@Test
////	void contextLoads_데이터베이스_리플리케이션_슬레이브() throws Exception {
////
////		mockMvc.perform(get("/api/products"))
////			.andDo(print())
////			.andExpect(status().isOk());
////
////	}
//
//	@Test
//	void contextLoads_데이터베이스_리플리케이션_마스터() {
//
//		ProductRegistDto registerDto = new ProductRegistDto();
//		registerDto.setTitle("테스트");
//		registerDto.setContent("테스트");
//
//		String paramString = objectMapper.writeValueAsString(registerDto);// 파라미터 JsonString 직렬화
//
//
//		mockMvc.perform(post("/api/products")
//			.content(paramString)
//			.contentType(MediaType.APPLICATION_JSON_VALUE)
//			.accept(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect(status().isOk());
//
//	}
//
//}
