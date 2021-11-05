package com.oopsmails.springboot.playaroud.jpa.model;

import java.util.List;

public interface TournamentIntf {

    String getName();

    List<PlayerNameIntf> getPlayers();
}
