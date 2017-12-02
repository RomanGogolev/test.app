package org.some.test.task.impl.service;

import org.apache.log4j.Logger;
import org.some.test.task.api.entity.User;
import org.some.test.task.api.services.UserRepository;
import org.some.test.task.impl.entity.ProfileEntity;
import org.some.test.task.impl.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by thefp on 02.12.2017.
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User get(String phone) {
        List<UserEntity> resultList = entityManager.createNamedQuery(User.GET_USER_BY_PHONE, UserEntity.class).setParameter(1, phone).getResultList();
        return resultList.size()>0?resultList.get(0):null;
    }

    @Override
    @Transactional
    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        entityManager.flush();
        ProfileEntity profileEntity = new ProfileEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        profileEntity.setUser(userEntity);
        entityManager.persist(profileEntity);
        logger.info("Register new user with phone "+user.getPhone());
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void delete(String phone) {
        entityManager.createQuery("delete from UserEntity u where u.username = ?1").setParameter(1,phone).executeUpdate();
    }
}
