package com.example.data.datajpa.repository;

import com.example.data.datajpa.entity.Album;
import com.example.data.datajpa.entity.Singer;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Repository
//public interface SingerRepository extends JpaRepository<Singer, Long>, JpaSpecificationExecutor<Singer> {
public interface SingerRepository {
    Iterable<Singer> findAll();
    Optional<Singer> findById(long id);
    Iterable<Singer> findAllWitAlbum();
    Singer addOrUpdate(Singer singer);
    Iterable<?> findWithCriteria(String firstName, String lastName);
}