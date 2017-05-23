package com.mebitech.tgs.core.api.persistence.entities;

import java.util.Date;
import java.util.Map;

import com.mebitech.core.api.persistence.entities.IEntity;
import com.mebitech.core.api.persistence.entities.security.IEntitySecurity;

public interface IUcakTipi extends IEntity<IEntitySecurity> {
    String toJson();

    Map<String, String> getProperties();

    void applyEntitySecurity(IEntitySecurity entitySecurity);

    Boolean getDeleted();

    String getKodu();

    String getAdi();
}