package com.tt.ext.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tt on 2016/12/6.
 */
@Entity
@Table(name = "roles")
public class Authority implements GrantedAuthority {
    @Id
    private Integer id;
    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
