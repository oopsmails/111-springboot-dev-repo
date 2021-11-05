package com.oopsmails.springboot.playaroud.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Cacheable
@JsonIgnoreProperties(ignoreUnknown=true)
@NamedEntityGraph(name = "graph.ChessPlayerTournaments", attributeNodes = @NamedAttributeNode("tournaments"))
public class ChessPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "player_sequence", initialValue = 100)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "playerWhite")
    @JsonIgnore
    private Set<ChessGame> gamesWhite;

    @OneToMany(mappedBy = "playerBlack")
    @JsonIgnore
    private Set<ChessGame> gamesBlack;

    // don't use FetchType.EAGER
    @ManyToMany(mappedBy = "players") //, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @JsonIgnore
    private Set<ChessTournament> tournaments;

    @Version
    private int version;

}