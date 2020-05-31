package com.ftn.osa.projekat_osa.utillity;

import com.ftn.osa.projekat_osa.model.Folder;

import java.util.Stack;

public interface FolderHelper {

    /**
     * Dobija path sa imenima foldera od root foldera do trazenog foldera.
     * Funkcija je rekurzivna.
     * @param folder folder do kojeg se trazi path
     * @param stack referenca na stack u koji se dodaje
     * @return stack kao path do foldera
     */
    public static Stack<String>getFolderPathStack(Folder folder, Stack<String> stack){
        stack.push(folder.getName());
        if(folder.getParentFolder() != null) getFolderPathStack(folder.getParentFolder(), stack);
        return stack;
    }

    public static Folder getRootFolder(Folder folder){
        if(folder.getParentFolder() != null) return getRootFolder(folder.getParentFolder());
        else return folder;
    }
}
