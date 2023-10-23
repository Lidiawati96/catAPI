package com.list.cat.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.list.cat.model.Cat;

@Service
public class CatService {
 private static List<Cat> Cats = new ArrayList();
 private static long idCounter = 0;
 
	@Autowired
	private CatJpaRepository CatJpaRepository;
 
 	public List<Cat> findAll(){
 		return Cats;
 	}
 
	public Cat deleteById(long id) {
		Cat Cat = findById(id);
		if(Cat==null) return null;
		if(Cats.remove(Cat)) {
			return Cat;
		}
		return null;
	}

	public Cat findById(long id) {
		for( Cat Cat:Cats) {
			if(Cat.getId() == id ) {
				return Cat;
			}
		}
		return null;
	}
	
	public Cat saveByUrl(Cat Cat) {
		findAll();
		long id = 0;
		if(!Cats.isEmpty()) {
			id = Cats.get(Cats.size()-1).getId();
		}
		try {
			Cat.setGambar(Base64.getEncoder().encodeToString(convertURLToBlob(Cat.getUrl())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cats.add(Cat);
		CatJpaRepository.save(Cat);
		return Cat;
	}
	
	public Cat saveByFile(MultipartFile file) {
		findAll();
		Cat Cat = new Cat();
		long id = 0;
		if(!Cats.isEmpty()) {
			id = Cats.get(Cats.size()-1).getId();
		}
		try {
			Cat.setGambar(Base64.getEncoder().encodeToString(convertFileContentToBlob(file)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cat.setName(file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf(".")));
		Cats.add(Cat);
		CatJpaRepository.save(Cat);
		return Cat;
	}
	
	public static void storeFile(MultipartFile file,String path) {
	      String fileNamePieces = file.getOriginalFilename() ;
	      File picture = new File(path);
	      if (!picture.exists())
	        picture.mkdirs();
	      try {
	        file.transferTo(picture);
	      } catch (FileNotFoundException e) {
	        e.printStackTrace();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	}
	
	public static void saveFile(String url,String path) {
		InputStream in;
		try {
			File picture = new File(path);
			if (!picture.exists())
		        picture.mkdirs();
			in = new URL(url).openStream();
			Files.copy(in, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static byte[] convertFileContentToBlob(MultipartFile file) throws IOException {
		InputStream inputStream = file.getInputStream();
		byte[] fileContent = new byte[inputStream.available()];
		try {
			inputStream.read(fileContent);
	   } catch (IOException e) {
		throw new IOException("Unable to convert file to byte array. " + 
	              e.getMessage());
	   } finally {
		if (inputStream != null) {
	           inputStream.close();
		}
	   }
	   return fileContent;
	}

	public static byte[] convertURLToBlob(String url) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		InputStream inputStream = null;
		inputStream = new URL(url).openStream();
		byte[] fileContent = new byte[inputStream.available()];
		try {
			while ((nRead = inputStream.read(fileContent, 0, fileContent.length)) != -1) {
				  buffer.write(fileContent, 0, nRead);
			}
	   } catch (IOException e) {
		throw new IOException("Unable to convert file to byte array. " + 
	              e.getMessage());
	   } finally {
		if (inputStream != null) {
	           inputStream.close();
		}
	   }
	   return buffer.toByteArray();
	}
}

