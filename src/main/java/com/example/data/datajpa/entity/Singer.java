package com.example.data.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "singer")
@NamedQueries({
        @NamedQuery(name = Singer.FIND_ALL,
                query = "select new com.example.data.datajpa.entity.Singer(s.id, s.firstName, s.lastName, s.birthDate) from Singer s"),
        @NamedQuery(name = Singer.FIND_SINGER_BY_ID,
                query = "select s from Singer s " +
                        "left join fetch s.albums a " +
                        "left join fetch s.instruments i " +
                        "where 1 = 1 " +
                        "and s.id = :id"),
        @NamedQuery(name = Singer.FIND_ALL_WITH_ALBUM,
                query = "select s from Singer s " +
                        "left join fetch s.albums a " +
                        "left join fetch s.instruments i"
        )
})
@SqlResultSetMapping(name = "singerResult",
        entities = @EntityResult(entityClass = Singer.class))
public class Singer implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Singer.findAll";
    public static final String FIND_SINGER_BY_ID = "Singer.findById";
    public static final String FIND_ALL_WITH_ALBUM = "Singer.findAllWithAlbum";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonIgnore
    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(
            mappedBy = "singer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private final Set<Album> albums = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany
    @JoinTable(name = "singer_instrument",
            joinColumns = @JoinColumn(name = "instrument_id"),
            inverseJoinColumns = @JoinColumn(name = "singer_id"))
    private Set<Instrument> instruments = new HashSet<>();

    public Singer() {
    }

    public Singer(Long id, String firstName, String lastName, Date birthDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public boolean addAlbum(Album album) {
        album.setSinger(this);
        return getAlbums().add(album);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "Singer{" +
                "id=" + id + '\'' +
                "firstName=" + firstName + '\'' +
                "lastName=" + lastName + '\'' +
                "version=" + version + '\'' +
                "birthDate=" + birthDate + '\'' +
                '}';
    }
}
