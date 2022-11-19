package com.example.data.datajpa.repository;

import com.example.data.datajpa.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstrumentRepository extends JpaRepository<Instrument, String>, JpaSpecificationExecutor<Instrument> {

}