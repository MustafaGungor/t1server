package com.mebitech.tgs.persistence.dao;

import com.mebitech.persistence.dao.ABaseDao;
import com.mebitech.tgs.core.api.persistence.dao.IUcakTipiDao;
import com.mebitech.tgs.persistence.entities.UcakTipiImpl;

@SuppressWarnings("unchecked")
public class UcakTipiDaoImpl extends ABaseDao<UcakTipiImpl> implements IUcakTipiDao {
    public UcakTipiDaoImpl() {
        super(UcakTipiImpl.class);
    }
}
