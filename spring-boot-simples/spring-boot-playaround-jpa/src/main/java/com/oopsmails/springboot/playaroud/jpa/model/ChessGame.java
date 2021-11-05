package com.oopsmails.springboot.playaroud.jpa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class ChessGame {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate date;

    private int round;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessTournament chessTournament;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessPlayer playerWhite;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChessPlayer playerBlack;

    @Version
    private int version;

}