package com.list.cat.model;

import java.sql.Blob;
import java.sql.Date;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "Cat")
public class Cat {
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String url;
	@Lob
	@Column(name = "gambar") 
	private String gambar;
	private Integer width;
	private Integer height;
	
	public Cat() {
		
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getGambar() {
		return gambar;
	}


	public void setGambar(String gambar) {
		this.gambar = gambar;
	}


	public Cat(long id, String name, String url, String path, Integer width, Integer height, String username, String gambar) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.width = width;
		this.height = height;
		this.gambar = gambar;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cat other = (Cat) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
