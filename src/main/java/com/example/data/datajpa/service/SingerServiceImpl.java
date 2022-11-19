package com.example.data.datajpa.service;

import com.example.data.datajpa.entity.Singer;
import com.example.data.datajpa.repository.SingerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Service
public class SingerServiceImpl implements SingerRepository {
    private final Logger logger = LoggerFactory.getLogger(SingerServiceImpl.class);
    @PersistenceContext
    private final EntityManager entityManager;

    public SingerServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Iterable<Singer> findAll() {
        return entityManager.createNamedQuery(Singer.FIND_ALL, Singer.class).getResultList();
    }

    @Override
    public Singer findById(long id) {
        return null;
    }

    @Override
    public Iterable<Singer> findAllWitAlbum() {
        return entityManager.createNamedQuery(Singer.FIND_ALL_WITH_ALBUM, Singer.class).getResultList();
    }
}
