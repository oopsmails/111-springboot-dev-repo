package com.oopsmails.springboot.playaroud.jpa.controller;

import com.oopsmails.springboot.playaroud.jpa.model.ChessPlayer;
import com.oopsmails.springboot.playaroud.jpa.repository.ChessPlayerRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import java.util.Optional;

@RestController
@RequestMapping(path = "player")
@Transactional
public class ChessPlayerController {

    private final ChessPlayerRepository playerRepo;

    public ChessPlayerController(ChessPlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    @GetMapping(path = "/{id}")
    public ChessPlayer getPlayerById(@PathVariable("id") Long id) {
        Optional<ChessPlayer> a = playerRepo.findById(id);
        if (!a.isPresent()) {
            throw new NoResultException();
        }
        return a.get();
    }
}
