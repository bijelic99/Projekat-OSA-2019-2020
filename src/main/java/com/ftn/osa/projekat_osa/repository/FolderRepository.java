package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Folder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {

	List<Folder> findByParentFolder(Folder folder);
}
