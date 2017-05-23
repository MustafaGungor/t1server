package com.mebitech.tgs.persistence.entities;

import java.util.Date;
import java.util.Map;
import javax.persistence.*;

import org.codehaus.jackson.map.ObjectMapper;
import com.mebitech.tgs.core.api.persistence.entities.IDosya;
import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;
import com.mebitech.core.api.persistence.enums.CrudType;

@Entity
@Table(name = "DOSYA")
public class DosyaImpl implements IDosya {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OLUSTURMA_TARIHI", nullable = false)
    private Date createDate;
    @Column(name = "SILINDI")
    private Boolean deleted;
    @Column(name = "ADI", columnDefinition = "VARCHAR(50)")
    private String adi;
    @Transient
    private CrudType[] crudTypes;
    @Transient
    private String[] entityAttributes;

    public DosyaImpl() {
    }

    public DosyaImpl(Long id, Date createDate, Boolean deleted
            , String adi) {
        super();
        this.id = id;
        this.deleted = deleted;
        this.createDate = createDate;
        this.adi = adi;
    }

    public DosyaImpl(IDosya dosya) {
        this.deleted = dosya.getDeleted();
        this.id = dosya.getId();
        this.createDate = dosya.getCreateDate();
        this.adi = dosya.getAdi();
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAdi() {
        return this.adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    @Override
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "DosyaImpl [id=" + id + "]" +
                ", createDate=" + createDate + "]";
    }

    @Override
    public Map<String, String> getProperties() {
//TODO: Return Table Column Name;
        return null;
    }

    @Override
    public void applyEntitySecurity(IEntitySecurity entitySecurity) {
        crudTypes = entitySecurity.getCrudType();
        entityAttributes = entitySecurity.getEntityAttributes();
    }
}