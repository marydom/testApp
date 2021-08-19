package com.etnetera.hr.data;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Etnetera
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndVersion", columnNames = { "name", "version" }) })
public class JavaScriptFramework {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 30)
	@NotBlank
	@Size(min = 1, max = 30)
	private String name;

	@Column(nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate deprecationDate;

	@Column(columnDefinition = "INTEGER DEFAULT 0")
	@Min(0)
	@Max(100)
	private int hypeLevel;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 1)
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public LocalDate getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(LocalDate deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	public int getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(int hypeLevel) {
		this.hypeLevel = hypeLevel;
	}

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}

	public JavaScriptFramework(String name, String version, LocalDate deprecationDate, int hypeLevel) {
		this.name = name;
		this.version = version;
		this.deprecationDate = deprecationDate;
		this.hypeLevel = hypeLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + ", version" + version + ", deprecationDate=" + deprecationDate + ", hypeLevel=" + hypeLevel + "]";
	}

}
