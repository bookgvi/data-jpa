package com.example.data.datajpa.repository;

import com.example.data.datajpa.entity.SingerInstrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SingerInstrumentRepository extends JpaRepository<SingerInstrument, String>, JpaSpecificationExecutor<SingerInstrument> {

}