package com.example.data.datajpa.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "singer_instrument")
public class SingerInstrument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "singer_id", nullable = false)
    private Long singerId;

    @Column(name = "instrument_id", nullable = false)
    private Long instrumentId;

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SingerInstrument{" +
                "singerId=" + singerId + '\'' +
                "instrumentId=" + instrumentId + '\'' +
                '}';
    }
}
