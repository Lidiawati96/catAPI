package com.list.cat.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.list.cat.model.Cat;
import com.list.cat.request.ReqCat;
import com.list.cat.service.CatJpaRepository;
import com.list.cat.service.CatService;

@CrossOrigin
@RestController
public class CatJpaResource {
	
	@Autowired
	private CatJpaRepository CatJpaRepository;
	
	@Autowired
	private CatService CatService;
	
	@GetMapping("/cats")
	public List<Cat> getAllCats(){
		return CatJpaRepository.findAll();
	}
	
	@GetMapping("/cats/{id}")
	public Cat getCat(@PathVariable long id){
		return CatJpaRepository.findById(id).get();
	}
	
	@DeleteMapping("/cats/{id}")
	public ResponseEntity<Void> deleteCat(
			@PathVariable long id){
		//Cat Cat = Catservice.deleteById(id);
		CatJpaRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
		
		//return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/cats/{id}")
	public ResponseEntity<Cat> updateCat(
			@PathVariable long id,@RequestBody Cat Cat){
		Cat CatUpdated = CatJpaRepository.save(Cat);
		return new ResponseEntity<Cat>(Cat,HttpStatus.OK);
	}
	
	@PostMapping("/insertCats")
	public ResponseEntity<Void> saveCat(
			@RequestBody ReqCat ReqCat){
		Cat Cat = new Cat();
		Cat.setName(ReqCat.getId());
		Cat.setWidth(ReqCat.getWidth());
		Cat.setHeight(ReqCat.getHeight());
		Cat.setUrl(ReqCat.getUrl());
		Cat createdCat = CatService.saveByUrl(Cat);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdCat.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/UploadCats", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> uploadCat( 
			@RequestPart("filesUpload") MultipartFile filesUpload){
		Cat createdCat = CatService.saveByFile(filesUpload);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(createdCat.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/getTotalKucing")
	public Integer getTotalKucing(){
		return CatJpaRepository.findAll().size();
	}
}

