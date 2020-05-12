package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;

import com.ftn.osa.projekat_osa.model.Tag;

public interface TagServiceInterface {
	public List<Tag> getAll();
	public Tag getOne(Long tagID);
	public Tag save(Tag tag);
	public void remove(Long tagId);
}
