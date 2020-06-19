package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;
import java.util.Set;

import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.model.Tag;

public interface TagServiceInterface {
    public List<Tag> getAll();

    public Tag getOne(Long tagID);

    public Tag save(Tag tag);

    public void remove(Long tagId);

    public Set<Tag> getUsersTags(Long userId);

    public Tag addUserTag(Long userId, Tag tag);
}
