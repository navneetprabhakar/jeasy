package com.navneet.jeasy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navneet.jeasy.config.RuleRegistry;
import com.navneet.jeasy.models.RuleEngineRequest;
import com.navneet.jeasy.models.RuleEngineRequest.DataSet;
import com.navneet.jeasy.rules.RemarkRules;
import com.navneet.jeasy.rules.ScoreCardRules;
import com.navneet.jeasy.service.RuleEngineService;
import com.navneet.jeasy.service.impl.RuleEngineServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Junit Test cases for @{@link com.navneet.jeasy.controller.RuleEngineController}
 * @author navneetprabhakar
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest
class JeasyApplicationTests {

	/**
	 * Load Bean classes for Autowiring
	 */
	@TestConfiguration
	static class RuleEngineServiceConfiguration{

		@Bean
		public RuleRegistry ruleRegistry(){
			return new RuleRegistry();
		}
		@Bean
		public RuleEngineService ruleEngineService(){
			return new RuleEngineServiceImpl();
		}

		@Bean
		public ScoreCardRules scoreCardRules(){
			return new ScoreCardRules();
		}

		@Bean
		public RemarkRules remarkRules(){
			return new RemarkRules();
		}
	}

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuleEngineService ruleEngineService;

	@Autowired
	private RuleRegistry ruleRegistry;

	private static final ObjectMapper mapper=new ObjectMapper();


	/**
	 * This test case runs the positive scenario 1
	 * @throws Exception
	 */
	@Test
	public void positiveTest1() throws Exception {
		RuleEngineRequest request= prepareRequest();
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/execute")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("A"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marksObtained").value(160))
				.andExpect(MockMvcResultMatchers.jsonPath("$.total").value(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.percentage").value(80))
				.andExpect(MockMvcResultMatchers.jsonPath("$.remarks").value("Very good Effort"));
	}


	/**
	 * This test case runs the positive scenario 2
	 * @throws Exception
	 */
	@Test
	public void positiveTest2() throws Exception {
		RuleEngineRequest request= prepareRequest();
		request.getMarksheet().get(0).setMarks(90);
		request.getMarksheet().remove(1);
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/execute")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("O"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marksObtained").value(90))
				.andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100))
				.andExpect(MockMvcResultMatchers.jsonPath("$.percentage").value(90))
				.andExpect(MockMvcResultMatchers.jsonPath("$.remarks").value("Outstanding Effort"));
	}

	/**
	 * This test case runs the positive scenario 3
	 * @throws Exception
	 */
	@Test
	public void positiveTest3() throws Exception {
		RuleEngineRequest request= prepareRequest();
		request.getMarksheet().get(0).setMarks(70);
		request.getMarksheet().get(0).setTotalMarks(100);
		request.getMarksheet().remove(1);
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/execute")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("B"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marksObtained").value(70))
				.andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100))
				.andExpect(MockMvcResultMatchers.jsonPath("$.percentage").value(70))
				.andExpect(MockMvcResultMatchers.jsonPath("$.remarks").value("Good Effort"));
	}

	/**
	 * This test case runs the positive scenario 4
	 * @throws Exception
	 */
	@Test
	public void positiveTest4() throws Exception {
		RuleEngineRequest request= prepareRequest();
		request.getMarksheet().get(0).setMarks(60);
		request.getMarksheet().get(0).setTotalMarks(100);
		request.getMarksheet().remove(1);
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/execute")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.grade").value("C"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marksObtained").value(60))
				.andExpect(MockMvcResultMatchers.jsonPath("$.total").value(100))
				.andExpect(MockMvcResultMatchers.jsonPath("$.percentage").value(60))
				.andExpect(MockMvcResultMatchers.jsonPath("$.remarks").value("Good Effort"));
	}


	/**
	 * This test case runs the 422 scenario
	 * @throws Exception
	 */
	@Test
	public void negativeTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/execute")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new RuleEngineRequest()))
				.accept(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	/**
	 * This method prepares the request object to be sent to the API
	 * @return
	 */
	private RuleEngineRequest prepareRequest(){
		List<DataSet> marksheet=new ArrayList<>();
		marksheet.add(new DataSet("MATHS",80,100));
		marksheet.add(new DataSet("SCIENCE",80,100));
		return RuleEngineRequest.builder()
				.marksheet(marksheet)
				.ruleIds(Arrays.asList(1,2))
				.build();
	}

}
