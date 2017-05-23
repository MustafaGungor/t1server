package com.mebitech.tgs.persistence.dao;

import com.mebitech.persistence.dao.ABaseDao;
import com.mebitech.tgs.core.api.persistence.dao.IUcusTuruDao;
import com.mebitech.tgs.persistence.entities.UcusTuruImpl;

@SuppressWarnings("unchecked")
public class UcusTuruDaoImpl extends ABaseDao<UcusTuruImpl> implements IUcusTuruDao {
    public UcusTuruDaoImpl() {
        super(UcusTuruImpl.class);
    }
}
