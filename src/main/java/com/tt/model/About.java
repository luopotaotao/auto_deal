package com.tt.model;

import javax.persistence.*;

/**
 * Created by tt on 2016/12/4.
 */
@Entity
@Table(name = "b_about")
public class About {
    @Id
    @GeneratedValue
    private Integer id;

    @Lob
    @Column(name = "content",columnDefinition = "text")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
