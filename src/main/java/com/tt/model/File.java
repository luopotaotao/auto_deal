package com.tt.model;

import javax.persistence.*;

/**
 * Created by tt on 2016/11/28.
 */
@Entity
@Table(name="b_file")
public class File extends BaseModel{
    private String uuid;
    private String name;

    public File() {
    }

    public File(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Id
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
