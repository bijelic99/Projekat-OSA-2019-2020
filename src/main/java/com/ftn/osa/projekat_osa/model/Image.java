package com.ftn.osa.projekat_osa.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends JpaEntity {
    @Lob
    private byte[] image;
    private String type;

    public Image(Long id, byte[] image, String type) {
        super(id);
        this.image = image;
        this.type = type;
    }

    public Image(){
        super();
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
