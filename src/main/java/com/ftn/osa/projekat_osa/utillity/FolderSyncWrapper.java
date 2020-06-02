package com.ftn.osa.projekat_osa.utillity;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.android_dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;

public class FolderSyncWrapper{
    private List<MessageDTO> messages = new ArrayList<>();
    private List<FolderDTO> folders = new ArrayList<>();

    public FolderSyncWrapper() {
    }

    public FolderSyncWrapper(List<MessageDTO> messages, List<FolderDTO> folders) {
        this.messages = messages;
        this.folders = folders;
    }

    public List<FolderDTO> getFolders() {
        return folders;
    }

    public void setFolders(List<FolderDTO> folders) {
        this.folders = folders;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
