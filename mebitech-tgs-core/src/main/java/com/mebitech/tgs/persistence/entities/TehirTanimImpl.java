package com.mebitech.tgs.persistence.entities;

import java.util.Date;
import java.util.Map;
import javax.persistence.*;

import org.codehaus.jackson.map.ObjectMapper;
import com.mebitech.tgs.core.api.persistence.entities.ITehirTanim;
import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;
import com.mebitech.core.api.persistence.enums.CrudType;

@Entity
@Table(name = "TEHIR_TANIM")
public class TehirTanimImpl implements ITehirTanim {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OLUSTURMA_TARIHI", nullable = false)
    private Date createDate;
    @Column(name = "SILINDI")
    private Boolean deleted;
    @Column(name = "KODU", columnDefinition = "VARCHAR(10)")
    private String kodu;
    @Column(name = "ADI", columnDefinition = "VARCHAR(100)")
    private String adi;
    @Transient
    private CrudType[] crudTypes;
    @Transient
    private String[] entityAttributes;

    public TehirTanimImpl() {
    }

    public TehirTanimImpl(Long id, Date createDate, Boolean deleted
            , String kodu, String adi) {
        super();
        this.id = id;
        this.deleted = deleted;
        this.createDate = createDate;
        this.kodu = kodu;
        this.adi = adi;
    }

    public TehirTanimImpl(ITehirTanim tehirTanim) {
        this.deleted = tehirTanim.getDeleted();
        this.id = tehirTanim.getId();
        this.createDate = tehirTanim.getCreateDate();
        this.kodu = tehirTanim.getKodu();
        this.adi = tehirTanim.getAdi();
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

    public String getKodu() {
        return this.kodu;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
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
        return "TehirTanimImpl [id=" + id + "]" +
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