package com.etnetera.hr;
 
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import javax.validation.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
 
/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Application.class)
@AutoConfigureMockMvc
public class JavaScriptFrameworkTests {
 
    @Autowired
    private MockMvc mvc;
 
    @Autowired
    private ObjectMapper mapper;
    
    @Autowired
    private JavaScriptFrameworkController controller;
 
    private long vueId;
    
    @Before
    public void before() {
 	   controller.deleteFrameworks();
	   
 	   vueId = controller.createOrSaveFramework(new JavaScriptFramework("Vue.js", "3.2", LocalDate.of(2021, 12, 31), 1)).getId();
 	   controller.createOrSaveFramework(new JavaScriptFramework("Angular", "12", LocalDate.of(2023, 12, 31), 0));
       controller.createOrSaveFramework(new JavaScriptFramework("Angular", "11", LocalDate.of(2000, 12, 31), 1));
 	   controller.createOrSaveFramework(new JavaScriptFramework("Angular", "11-patch27", LocalDate.of(2021, 12, 31), 1));
 	   controller.createOrSaveFramework(new JavaScriptFramework("jQuery", "3.6.0", LocalDate.of(2021, 12, 31), 67));

    }
    
    @Test
    public void testGetFrameworkById() throws Exception {
        mvc.perform(get("/frameworks/" + vueId).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().json("{'name':'Vue.js', 'version':'3.2', 'deprecationDate':'2021-12-31', 'hypeLevel':1}"));
    }

	@Test
	public void testFindByName() throws Exception {
        mvc.perform(get("/frameworks/with-name/Angular").accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk()).andExpect(content().json("[{'name':'Angular'}, {'name':'Angular'}, {'name':'Angular'}]"));
	}


	@Test
	public void testFindByDeprecationDateAfter() throws Exception {
        mvc.perform(get("/frameworks/actuals-after/2021-12-31").accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk()).andExpect(content().json("[{'deprecationDate':'2023-12-31'}]"));
	}

    @Test
    public void testCreateUpdateDeleteFramework() throws Exception {
    	//original number of frameworks
    	Iterable<JavaScriptFramework> frameworks = controller.getFrameworks();
    	int count = 0;
    	for(JavaScriptFramework f : frameworks) {
    		count++;
    	}
    	
    	//number of frameworks + one new
    	JavaScriptFramework framework = new JavaScriptFramework("Vue.js", "3.3", LocalDate.of(2024, 12, 31), 90);
        ResultActions resultActions = mvc.perform(post("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(framework)).accept(MediaType.APPLICATION_JSON));
        int id = JsonPath.read(resultActions.andReturn().getResponse().getContentAsString(), "id");
        resultActions.andExpect(status().isOk()).andExpect(content().json("{'name':'Vue.js', 'version':'3.3', 'deprecationDate':'2024-12-31', 'hypeLevel':90}"));
        frameworks = controller.getFrameworks();
        int saveCount = 0;
   	 	for(JavaScriptFramework f : frameworks) {
   	 		saveCount++;
   	 	}
        assertThat(saveCount).isEqualTo(count+1);
        
        //same number of frameworks - one changed
        framework = new JavaScriptFramework("Vue.js", "3.3", LocalDate.of(2023, 12, 31), 80);
        mvc.perform(put("/frameworks/" + id).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(framework)).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().json("{'name':'Vue.js', 'version':'3.3', 'deprecationDate':'2023-12-31', 'hypeLevel':80}"));
        frameworks = controller.getFrameworks();
        int updateCount = 0;
   	 	for(JavaScriptFramework f : frameworks) {
   	 		updateCount++;
   	 	}
        assertThat(updateCount).isEqualTo(saveCount);
        
        //original number of frameworks - new one deleted
        mvc.perform(delete("/frameworks/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        assertThatCode(() -> controller.getFrameworkById((long) id)).isInstanceOf(NoSuchElementException.class);
        //controller.getFrameworkById((long) id);
        frameworks = controller.getFrameworks();
        int deleteCount = 0;
   	 	for(JavaScriptFramework f : frameworks) {
   	 		deleteCount++;
   	 	}
        assertThat(deleteCount).isEqualTo(count);
        
    }

	
	@Test
	public void testNameValidation() throws Exception {
		JavaScriptFramework framework = new JavaScriptFramework(null, "1", LocalDate.of(2021, 11, 30), 45);
		assertThatValidationFails(framework);
		framework = new JavaScriptFramework("", "1", LocalDate.of(2021, 11, 30), 45);
		assertThatValidationFails(framework);
		framework = new JavaScriptFramework("AngularAngularAngularAngularAngular", "1", LocalDate.of(2021, 11, 30), 45);
		assertThatValidationFails(framework);

	}

	@Test
	public void testVersionValidation() throws Exception {
		JavaScriptFramework framework = new JavaScriptFramework("Angular", null, LocalDate.of(2021, 11, 30), 45);
		assertThatValidationFails(framework);
		framework = new JavaScriptFramework("Angular", "", LocalDate.of(2021, 11, 30), 45);
		assertThatValidationFails(framework);
	}

	@Test
	public void testHypeLevelValidation() throws Exception {
		JavaScriptFramework framework = new JavaScriptFramework("Angular", "1.0", LocalDate.of(2021, 11, 30), -1);
		assertThatValidationFails(framework);
		framework = new JavaScriptFramework("Angular", "1.1", LocalDate.of(2021, 11, 30), 101);
		assertThatValidationFails(framework);
	}

	@Test
	public void testDeprecationDateValidation() throws Exception {
		JavaScriptFramework framework = new JavaScriptFramework("Angular", "1.0", null, 1);
		assertThatValidationFails(framework);
	}

	private void assertThatValidationFails(JavaScriptFramework framework) {
		assertThatCode(() -> controller.createOrSaveFramework(framework)).hasRootCauseInstanceOf(ConstraintViolationException.class);
	}
	
}
