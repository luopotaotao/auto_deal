package com.tt.model;



import javax.persistence.*;

@Entity
@Table(name = "b_dept")

public class Dept implements java.io.Serializable {
    private Integer id;
    private String code;
    private String name;
    private Byte economy_typ;
    private String certificate_code;
    private Byte register_type;
    private String logo;
    private String note;
    private boolean isDeleted;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "economy_typ")
    public Byte getEconomy_typ() {
        return economy_typ;
    }

    public void setEconomy_typ(Byte economy_typ) {
        this.economy_typ = economy_typ;
    }

    @Basic
    @Column(name = "certificate_code")
    public String getCertificate_code() {
        return certificate_code;
    }

    public void setCertificate_code(String certificate_code) {
        this.certificate_code = certificate_code;
    }

    @Basic
    @Column(name = "register_type")
    public Byte getRegister_type() {
        return register_type;
    }

    public void setRegister_type(Byte register_type) {
        this.register_type = register_type;
    }

    @Basic
    @Column(name = "logo")
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "deleted")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
