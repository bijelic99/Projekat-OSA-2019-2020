package com.ftn.osa.projekat_osa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Tag;
import com.ftn.osa.projekat_osa.repository.TagRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.TagServiceInterface;

@Service
public class TagService implements TagServiceInterface {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;
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
        userRepository.findUserForTag(tagId).ifPresent(user -> {
            user.setUserTags(user.getUserTags().stream().filter(ut -> ut.getId() != tagId).collect(Collectors.toSet()));
            userRepository.save(user);
        });
        tagRepository.deleteById(tagId);
    }

    @Override
    public Set<Tag> getUsersTags(Long userId) {
        return tagRepository.getUsersTags(userId);
    }

    @Override
    public Tag addUserTag(Long userId, Tag tag) {
        User user = userRepository.getOne(userId);
        tag = tagRepository.save(tag);
        user.getUserTags().add(tag);
        userRepository.save(user);

        return tag;
    }

}
