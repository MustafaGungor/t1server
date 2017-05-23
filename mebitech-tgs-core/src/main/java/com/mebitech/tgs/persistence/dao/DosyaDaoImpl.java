package com.mebitech.tgs.persistence.dao;

import com.mebitech.persistence.dao.ABaseDao;
import com.mebitech.tgs.core.api.persistence.dao.IDosyaDao;
import com.mebitech.tgs.persistence.entities.DosyaImpl;

@SuppressWarnings("unchecked")
public class DosyaDaoImpl extends ABaseDao<DosyaImpl> implements IDosyaDao {
    public DosyaDaoImpl() {
        super(DosyaImpl.class);
    }
}
