package com.ftn.osa.projekat_osa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Tag;
import com.ftn.osa.projekat_osa.repository.TagRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.TagServiceInterface;

@Service
public class TagService implements TagServiceInterface {

    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag getOne(Long tagId) {
        return tagRepository.getOne(tagId);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void remove(Long tagId) {
        tagRepository.deleteById(tagId);
    }

}
