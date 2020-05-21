package com.ftn.osa.projekat_osa.mail_utill;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Arrays;

public interface MailUtilityHelper {
    static InternetAddress[] stringToInternetAddress(String recipients) {
        return Arrays.stream(recipients.split(","))
                .map(s -> s.replaceAll(" ", ""))
                .map(s -> {
                    try {
                        return new InternetAddress(s);
                    } catch (AddressException e) {
                        return null;
                    }
                })
                .filter(internetAddress -> internetAddress != null)
                .toArray(InternetAddress[]::new);
    }
}
