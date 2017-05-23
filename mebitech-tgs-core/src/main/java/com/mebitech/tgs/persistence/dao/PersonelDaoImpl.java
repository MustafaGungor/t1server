package com.mebitech.tgs.persistence.dao;

import com.mebitech.persistence.dao.ABaseDao;
import com.mebitech.tgs.core.api.persistence.dao.IPersonelDao;
import com.mebitech.tgs.persistence.entities.PersonelImpl;

@SuppressWarnings("unchecked")
public class PersonelDaoImpl extends ABaseDao<PersonelImpl> implements IPersonelDao {
    public PersonelDaoImpl() {
        super(PersonelImpl.class);
    }
}
