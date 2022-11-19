package com.example.data.datajpa.repository;

import com.example.data.datajpa.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {

}