package com.ftn.osa.projekat_osa.utillity;

import com.ftn.osa.projekat_osa.model.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;

public class MessageComparatorByDateTime implements Comparator<Message> {
    private static MessageComparatorByDateTime messageComparatorByDateTime;

    @Override
    public int compare(Message o1, Message o2) {
        LocalDateTime ldt1 = o1.getDateTime();
        LocalDateTime ldt2 = o2.getDateTime();
        if(ldt1 == null && ldt2 == null) return 0;
        else if(ldt1 != null && ldt2 == null) return 1;
        else if(ldt1 == null && ldt2 != null) return -1;
        else return ldt1.isAfter(ldt2) ? -1 : ldt2.isAfter(ldt1) ? 1 : 0;
    }

    public static int compareObjects(Message o1, Message o2){
        if(messageComparatorByDateTime == null) messageComparatorByDateTime = new MessageComparatorByDateTime();
        return messageComparatorByDateTime.compare(o1, o2);
    }
}
