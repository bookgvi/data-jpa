package com.example.data.datajpa.service;

import com.example.data.datajpa.entity.*;
import com.example.data.datajpa.repository.SingerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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

    @Override
    public Iterable<?> findWithCriteria(String firstName, String lastName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Singer> criteriaQuery = cb.createQuery(Singer.class);
        CriteriaQuery<Tuple> criteriaQuery = cb.createTupleQuery();
        Root<Singer> singerRoot = criteriaQuery.from(Singer.class);
        Join<Singer, Album> albumJoin = singerRoot.join(Singer_.albums, JoinType.LEFT);
//        singerRoot.fetch(Singer_.albums, JoinType.LEFT);
        Join<Singer, Instrument> instrumentJoin = singerRoot.join(Singer_.instruments, JoinType.LEFT);
//        singerRoot.fetch(Singer_.instruments, JoinType.LEFT);
        Predicate andCriteria = cb.conjunction();
        if (firstName != null) {
            Predicate p = cb.equal(singerRoot.get(Singer_.firstName), firstName);
            andCriteria = cb.and(andCriteria, p);
        }
        if (lastName != null) {
            Predicate p = cb.equal((singerRoot.get(Singer_.lastName)), lastName);
            andCriteria = cb.and(andCriteria, p);
        }
//        criteriaQuery.multiselect(singerRoot, cb.count(albumJoin)).distinct(true)
//                .where(andCriteria)
//                .groupBy(singerRoot)
//                .having(cb.equal(cb.count(albumJoin), 1));
        criteriaQuery.multiselect(
                singerRoot.get(Singer_.firstName),
                singerRoot.get(Singer_.lastName),
                singerRoot.get(Singer_.birthDate),
//                albumJoin.get(Album_.id),
                albumJoin.get(Album_.title),
                instrumentJoin.get(Instrument_.title)
                ).where(andCriteria);
        Iterable<?> resultCollection = entityManager.createQuery(criteriaQuery).getResultList();
        return resultCollection;
    }
}
