package com.etnetera.hr.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.etnetera.hr.data.JavaScriptFramework;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JavaScriptFrameworkRepositoryTest {

   @Autowired
    private JavaScriptFrameworkRepository repository;

   @Before
   public void before() {
	   repository.deleteAll();
	   
	   repository.save(new JavaScriptFramework("Vue.js", "3.2", LocalDate.of(2021, 12, 31), 1));
       repository.save(new JavaScriptFramework("Vue.js", "3.1", LocalDate.of(2020, 12, 31), 100));
       repository.save(new JavaScriptFramework("Angular", "12", LocalDate.of(2023, 12, 31), 0));
       repository.save(new JavaScriptFramework("Angular", "11", LocalDate.of(2000, 12, 31), 1));
       repository.save(new JavaScriptFramework("Angular", "11-patch27", LocalDate.of(2021, 12, 31), 1));
       repository.save(new JavaScriptFramework("jQuery", "3.6.0", LocalDate.of(2021, 12, 31), 67));

   }
   
	@Test
	public void testFindByName() {
		List<JavaScriptFramework> frameworks = repository.findByName("Angular");
		assertThat(frameworks.size()).isEqualTo(3);
		assertThat(frameworks).allMatch(f -> "Angular".equals(f.getName()));
	}

	@Test
	public void testFindByDeprecationDateAfter() {
		LocalDate date = LocalDate.of(2021, 12, 31);
		List<JavaScriptFramework> frameworks = repository.findByDeprecationDateAfter(date);
		assertThat(frameworks.size()).isEqualTo(1);
		assertThat(frameworks).allMatch(f -> date.isBefore(f.getDeprecationDate()));
	}

}
