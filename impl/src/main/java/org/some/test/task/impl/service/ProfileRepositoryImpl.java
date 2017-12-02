package org.some.test.task.impl.service;

import org.apache.log4j.Logger;
import org.some.test.task.api.entity.Profile;
import org.some.test.task.api.services.ProfileRepository;
import org.some.test.task.impl.entity.ProfileEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by thefp on 02.12.2017.
 */
@Component
public class ProfileRepositoryImpl implements ProfileRepository {

    private static final Logger logger = Logger.getLogger(ProfileRepositoryImpl.class);


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Profile get(String phone) {
        return entityManager.createNamedQuery(Profile.GET_PROFILE_BY_USER_PHONE, ProfileEntity.class).setParameter(1,phone).getSingleResult();
    }

    @Override
    @Transactional
    public void update(Profile profile) {
        entityManager.merge(profile);
        entityManager.flush();
        logger.info("User with phone "+profile.getUser().getPhone()+" update his profile email:"+
                profile.getEmail()+" fio:"+
                profile.getFio()+" hrefToImg:"+
                profile.getHrefToImg());
    }
}
