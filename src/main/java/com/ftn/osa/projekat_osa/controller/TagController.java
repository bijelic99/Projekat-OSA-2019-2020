package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.osa.projekat_osa.android_dto.TagDTO;
import com.ftn.osa.projekat_osa.model.Tag;
import com.ftn.osa.projekat_osa.service.serviceInterface.TagServiceInterface;

@RestController
@RequestMapping(value="api/tags")
public class TagController {

	@Autowired
	private TagServiceInterface tagService;
	
	@GetMapping
	public ResponseEntity<List<TagDTO>> getTags(){
		List<Tag> tags = tagService.getAll();
		
		List<TagDTO> tagsDTO = new ArrayList<TagDTO>();
		for(Tag tag: tags) {
			tagsDTO.add(new TagDTO(tag));
		}
		return new ResponseEntity<List<TagDTO>>(tagsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<TagDTO> getTag(@PathVariable("id") Long id){
		Tag tag = tagService.getOne(id);
		if(tag == null) {
			return new ResponseEntity<TagDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
	}
	
	@PostMapping("application/json")
	public ResponseEntity<TagDTO> saveTag(@RequestBody TagDTO tagDTO){
		Tag tag = tagDTO.getJpaEntity();
		
		tag = tagService.save(tag);
		return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
	}
	
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tagDTO){
		Tag tag = tagService.getOne(tagDTO.getId());
		
		if(tag == null) {
			return new ResponseEntity<TagDTO>(HttpStatus.BAD_REQUEST);
		}
		
		Tag jpaEntityFromDto = tagDTO.getJpaEntity();
		
		tag.setName(jpaEntityFromDto.getName());
		
		tag = tagService.save(tag);
		
		return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable("id") Long tagId){
		Tag tag = tagService.getOne(tagId);
		if(tag != null) {
			tagService.remove(tagId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}
