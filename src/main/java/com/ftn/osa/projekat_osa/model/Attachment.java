package com.ftn.osa.projekat_osa.model;

import java.util.Base64;

public class Attachment extends Identifiable {
    private Base64 data;
    private String mime_type;
    private String name;

    public Attachment(Long id, Base64 data, String mime_type, String name) {
        super(id);
        this.data = data;
        this.mime_type = mime_type;
        this.name = name;
    }

    public Attachment() {

    }

    public Base64 getData() {
        return data;
    }

    public void setData(Base64 data) {
        this.data = data;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
