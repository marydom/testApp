package com.etnetera.hr.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

	List<JavaScriptFramework> findByName(String name);

	List<JavaScriptFramework> findByDeprecationDateGreaterThanEqual(LocalDate date);

	List<JavaScriptFramework> findByHypeLevelGreaterThanOrderByHypeLevelDesc(Integer hypeLevel);

}
