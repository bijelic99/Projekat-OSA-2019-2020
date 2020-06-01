package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Folder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByParentFolder(Folder folder);

    @Query("select f.folders from Folder f where f.id = :id")
    Set<Folder> getInnerFolders(@Param("id") Long id);

    @Query("select f from Folder f join f.messages m where m.id = :id")
    Optional<Folder> getFolderThatContainsMessage(@Param("id") Long messageId);
}
