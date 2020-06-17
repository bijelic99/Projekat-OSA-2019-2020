package com.ftn.osa.projekat_osa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "attachments")
public class Attachment extends JpaEntity {

    @Column(nullable = false)
    @Lob
    private String data;
    @Column(nullable = false)
    private String mimeType;
    @Column(columnDefinition = "varchar(255) default ''")
    private String name;

    public Attachment(Long id, String data, String mimeType, String name) {
        super(id);
        this.data = data;
        this.mimeType = mimeType;
        this.name = name;
    }

    public Attachment() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMime_type() {
        return mimeType;
    }

    public void setMime_type(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
