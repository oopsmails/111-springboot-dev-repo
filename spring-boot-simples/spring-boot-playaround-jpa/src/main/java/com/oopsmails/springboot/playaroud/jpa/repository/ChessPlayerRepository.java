package com.oopsmails.springboot.playaroud.jpa.repository;


import com.oopsmails.springboot.playaroud.jpa.model.BetterPlayerFullNameIntf;
import com.oopsmails.springboot.playaroud.jpa.model.ChessPlayer;
import com.oopsmails.springboot.playaroud.jpa.model.PlayerFullNameIntf;
import com.oopsmails.springboot.playaroud.jpa.model.PlayerName;
import com.oopsmails.springboot.playaroud.jpa.model.PlayerNameIntf;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChessPlayerRepository extends JpaRepository<ChessPlayer, Long> {

    //     @Query("SELECT p FROM ChessPlayer p LEFT JOIN FETCH p.tournaments")
    @EntityGraph(attributePaths = "tournaments")
    List<ChessPlayer> findWithTournamentsBy();

    PlayerName findDtoByFirstName(String firstName);

    PlayerNameIntf findByFirstName(String firstName);

    @Query(value = "SELECT first_name as firstName, last_name as lastName FROM Chess_Player p WHERE p.first_name=:firstName",
            nativeQuery = true)
    PlayerNameIntf findNativeByFirstName(String firstName);

    @Query(value = "SELECT first_name as firstName, last_name as lastName FROM Chess_Player p WHERE p.first_name=:firstName",
            nativeQuery = true)
    PlayerName findDtoNativeByFirstName(String firstName);

    PlayerFullNameIntf findFullNameByFirstName(String firstName);

    BetterPlayerFullNameIntf findBetterFullNameByFirstName(String firstName);
}