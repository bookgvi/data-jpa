package com.example.data.datajpa.service;

import com.example.data.datajpa.entity.Album;
import com.example.data.datajpa.entity.Singer;
import com.example.data.datajpa.repository.SingerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
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
    public Optional<Singer> findById(long id) {
        TypedQuery<Singer> query = entityManager.createNamedQuery(Singer.FIND_SINGER_BY_ID, Singer.class);
        query.setParameter("id", id);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Iterable<Singer> findAllWitAlbum() {
        logger.info("find all singer and albums");
        return entityManager.createNamedQuery(Singer.FIND_ALL_WITH_ALBUM, Singer.class).getResultList();
    }

    @Override
    public Singer addOrUpdate(Singer singer) {
        for (Album album : singer.getAlbums()) {
            singer.addAlbum(album);
        }
        if (singer.getId() != null) {
            Singer mergedSinger = entityManager.merge(singer);
            entityManager.remove(mergedSinger);
            logger.info("SInger with id " + singer.getId() + " updated");
        }
        logger.info("New Singer has been created");
        entityManager.persist(singer);
        return singer;
    }
}
