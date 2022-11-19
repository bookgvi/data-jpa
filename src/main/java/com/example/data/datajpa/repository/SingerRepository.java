package com.example.data.datajpa.repository;

import com.example.data.datajpa.entity.Singer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//public interface SingerRepository extends JpaRepository<Singer, Long>, JpaSpecificationExecutor<Singer> {
public interface SingerRepository {
    Iterable<Singer> findAll();
    Singer findById(long id);
    Iterable<Singer> findAllWitAlbum();
}