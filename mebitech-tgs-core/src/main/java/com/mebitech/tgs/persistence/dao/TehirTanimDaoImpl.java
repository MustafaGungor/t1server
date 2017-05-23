package com.mebitech.tgs.persistence.dao;

import com.mebitech.persistence.dao.ABaseDao;
import com.mebitech.tgs.core.api.persistence.dao.ITehirTanimDao;
import com.mebitech.tgs.persistence.entities.TehirTanimImpl;

@SuppressWarnings("unchecked")
public class TehirTanimDaoImpl extends ABaseDao<TehirTanimImpl> implements ITehirTanimDao {
    public TehirTanimDaoImpl() {
        super(TehirTanimImpl.class);
    }
}
