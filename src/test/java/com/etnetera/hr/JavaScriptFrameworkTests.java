package com.etnetera.hr;
 
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
 
/**
 * Class used for Spring Boot/MVC based tests.
 * 
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(JavaScriptFrameworkController.class)
public class JavaScriptFrameworkTests {
 
    @Autowired
    private MockMvc mvc;
 
    @MockBean
    private JavaScriptFrameworkController controller;
 
    @Test
    public void test() throws Exception {
        given(controller.getFrameworkById(Long.valueOf(1L)))
        .willReturn(new JavaScriptFramework("Angular", "v1", LocalDate.of(2000, 1, 2), 51));
        mvc.perform(get("/frameworks/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().json("{'name':'Angular', 'version':'v1', 'deprecationDate':'2000-01-02', 'hypeLevel':51}"));
    }

}
