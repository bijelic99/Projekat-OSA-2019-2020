package com.ftn.osa.projekat_osa.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.MailServiceInterface;
import com.ftn.osa.projekat_osa.utillity.FolderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.repository.FolderRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;

import javax.mail.MessagingException;

@Service
public class FolderService implements FolderServiceInterface {

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    MailServiceInterface mailServiceInterface;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Folder getOne(Long folderID) {
        return folderRepository.getOne(folderID);
    }

    @Override
    public List<Folder> getAll() {
        return folderRepository.findAll();
    }

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public void remove(Long folderID) {
        folderRepository.deleteById(folderID);
    }

    @Override
    public Set<Folder> getInnerFolders(Long id) {
        return folderRepository.getInnerFolders(id);
    }

    /**
     * <p>
     *     Funkcija sluzi za sinhronizaciju foldera sa backendom i mail serverom.
     * </p>
     * <p>
     *     Izvrsava se u sledecim koracima:
     *     <ol>
     *         <li>
     *             Salje folderId MailService-u gde se fetchuju novi mailovi i folderi sa mail servera i ubacuju u bazu,
     *             vraca update-ovani folder
     *         </li>
     *         <li>
     *             Filtrira one mailove i foldere koji su potrebni korisniku i vraca ih u mapi
     *         </li>
     *     </ol>
     * </p>
     * @param id id foldera
     * @param data mapa koja se sastoji iz polja "latestMessageTimestamp" tipa LocalDateTime i "folderList" tipa List&lt;Long&gt;
     * @return  Mapa sa poljima "folders" tipa List&lt;Folder&gt; i "messages" tipa List&lt;Message&gt;
     * @throws NullPointerException ukoliko je folder list null
     */
    @Override
    public Map<String, Object> syncFolder(Long id, Map<String, Object> data) throws ResourceNotFoundException, MessagingException, WrongProtocolException {

        String strLatestMessageTimestamp = (String) data.get("latestMessageTimestamp");
        LocalDateTime latestMessageTimestamp = strLatestMessageTimestamp != null ? LocalDateTime.parse(strLatestMessageTimestamp, DateTimeFormatter.ISO_DATE_TIME) : null;
        List<Long> folderList = ((List<Integer>) data.get("folderList")).stream()
                .map(integer -> Long.valueOf(integer)).collect(Collectors.toList());

        if(folderList == null) throw new NullPointerException("Folder list cannot be null");

        Folder rootFolder = FolderHelper.getRootFolder(folderRepository.getOne(id));
        Optional<Account> optionalAccount = accountRepository.getAccountFromAccountFolder(rootFolder.getId());
        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            mailServiceInterface.getNewMessages(account.getId());

            Folder f = folderRepository.getOne(id);

            List<Message> messages = f.getMessages().stream()
                    .filter(message -> latestMessageTimestamp == null || message.getDateTime().isAfter(latestMessageTimestamp))
                    .collect(Collectors.toList());

            List<Folder> folders = f.getFolders().stream()
                    .filter(folder -> !folderList.contains(folder.getId()))
                    .collect(Collectors.toList());

            Map<String, Object> map = new HashMap<>();
            map.put("messages", messages);
            map.put("folders", folders);

            return map;
        }
        else throw new ResourceNotFoundException("Account not found");




    }


}
