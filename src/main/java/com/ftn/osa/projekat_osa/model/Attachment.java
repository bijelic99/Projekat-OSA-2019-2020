package com.ftn.osa.projekat_osa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ATTACHMENTS")
public class Attachment extends Identifiable {

    @Column
    private String base64Data;

    @Column
    private String type;

    @Column
    private String name;


    public Attachment(){
        this(null,null,null,null);
    }
    public Attachment(Integer id, String base64Data, String type, String name) {
        super(id);
        this.base64Data = base64Data;
        this.type = type;
        this.name = name;
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
