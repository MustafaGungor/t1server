package com.mebitech.tgs.persistence.dao;

import com.mebitech.persistence.dao.ABaseDao;
import com.mebitech.tgs.core.api.persistence.dao.IUcakDao;
import com.mebitech.tgs.persistence.entities.UcakImpl;

@SuppressWarnings("unchecked")
public class UcakDaoImpl extends ABaseDao<UcakImpl> implements IUcakDao {
    public UcakDaoImpl() {
        super(UcakImpl.class);
    }
}
