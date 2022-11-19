package com.example.data.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "album")
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "singer_id", nullable = false)
//    private Long singerId;
//
    @Column(name = "title", nullable = false)
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;

    @JsonIgnore
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "singer_id")
    private Singer singer;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "singer_instrument",
            joinColumns = @JoinColumn(name="singer_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument_id"))
    private Set<Singer> singers = new HashSet<>();

    public Set<Singer> getSingers() {
        return singers;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

//    public void setSingerId(Long singerId) {
//        this.singerId = singerId;
//    }
//
//    public Long getSingerId() {
//        return singerId;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id + '\'' +
//                "singerId=" + singerId + '\'' +
                "title=" + title + '\'' +
                "releaseDate=" + releaseDate + '\'' +
                "version=" + version + '\'' +
                '}';
    }
}
