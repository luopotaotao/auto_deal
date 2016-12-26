package com.tt.ext.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tt.model.BaseModel;
import com.tt.model.Dept;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tt on 2016/12/6.
 */
@Entity
@Table(name = "my_user_details")
@JsonIgnoreProperties(value = {"password","authorities","dept"})
public class MyUserDetails extends BaseModel implements Serializable, org.springframework.security.core.userdetails.UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;



    @ManyToOne
    @JoinColumn(name = "dept_id",insertable = false,updatable = false)
    private Dept dept;
//    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
//    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Transient
    private Set<Authority> authorities;

    @ManyToOne
    @JoinColumn(name = "role")
    private Authority authority;

    public MyUserDetails() {
    }

    public MyUserDetails(String username, String password, String realName, String email, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled, Dept dept,Authority authority) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.email = email;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.dept = dept;
        this.authority = authority;
    }
    @Override
    public Set<Authority> getAuthorities() {
        if(authority==null){
            return null;
        }else{
            if(authorities==null){
                authorities = new HashSet<>();
            }else {
                authorities.clear();
            }
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }


    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "MyUserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                ", dept=" + dept +
                ", authority=" + authority +
                '}';
    }
}
