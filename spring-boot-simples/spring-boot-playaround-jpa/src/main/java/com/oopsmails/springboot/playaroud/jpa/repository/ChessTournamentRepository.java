package com.oopsmails.springboot.playaroud.jpa.repository;

import com.oopsmails.springboot.playaroud.jpa.model.ChessTournament;
import com.oopsmails.springboot.playaroud.jpa.model.TournamentIntf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChessTournamentRepository extends JpaRepository<ChessTournament, Long> {

    TournamentIntf findByName(String name);
}
