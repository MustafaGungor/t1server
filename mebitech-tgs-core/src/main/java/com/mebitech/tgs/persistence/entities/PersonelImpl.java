package com.mebitech.tgs.persistence.entities;

import java.util.Date;
import java.util.Map;
import javax.persistence.*;

import org.codehaus.jackson.map.ObjectMapper;
import com.mebitech.tgs.core.api.persistence.entities.IPersonel;
import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;
import com.mebitech.core.api.persistence.enums.CrudType;

@Entity
@Table(name = "PERSONEL")
public class PersonelImpl implements IPersonel {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OLUSTURMA_TARIHI", nullable = false)
    private Date createDate;
    @Column(name = "SILINDI")
    private Boolean deleted;
    @Column(name = "KODU", columnDefinition = "VARCHAR(10)",unique = true)
    private String kodu;
    @Column(name = "ADI", columnDefinition = "VARCHAR(100)")
    private String adi;
    @Column(name = "SICIL_NO", columnDefinition = "VARCHAR(50)")
    private String sicilNo;
    @Column(name = "SOYADI", columnDefinition = "VARCHAR(100)")
    private String soyadi;
    @Column(name = "GOREVI", columnDefinition = "VARCHAR(100)")
    private String gorevi;
    @Transient
    private CrudType[] crudTypes;
    @Transient
    private String[] entityAttributes;

    public PersonelImpl() {
    }

    public PersonelImpl(Long id, Date createDate, Boolean deleted
            , String kodu, String adi, String sicilNo, String soyadi, String gorevi) {
        super();
        this.id = id;
        this.deleted = deleted;
        this.createDate = createDate;
        this.kodu = kodu;
        this.adi = adi;
        this.sicilNo = sicilNo;
        this.soyadi = soyadi;
        this.gorevi = gorevi;
    }

    public PersonelImpl(IPersonel personel) {
        this.deleted = personel.getDeleted();
        this.id = personel.getId();
        this.createDate = personel.getCreateDate();
        this.kodu = personel.getKodu();
        this.adi = personel.getAdi();
        this.sicilNo = personel.getSicilNo();
        this.soyadi = personel.getSoyadi();
        this.gorevi = personel.getGorevi();
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

    public String getSicilNo() {
        return this.sicilNo;
    }

    public void setSicilNo(String sicilNo) {
        this.sicilNo = sicilNo;
    }

    public String getSoyadi() {
        return this.soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getGorevi() {
        return this.gorevi;
    }

    public void setGorevi(String gorevi) {
        this.gorevi = gorevi;
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
        return "PersonelImpl [id=" + id + "]" +
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