package com.oopsmails.springboot.playaroud.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cacheable
@Getter
@Setter
public class ChessTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_seq")
    @SequenceGenerator(name = "tournament_seq", sequenceName = "tournament_sequence", initialValue = 100)
    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Version
    private int version;

    @ManyToMany
    //    private List<ChessPlayer> players = new ArrayList<>();
    private Set<ChessPlayer> players = new HashSet<>();

    @OneToMany
    private Set<ChessGame> games = new HashSet<>();

}