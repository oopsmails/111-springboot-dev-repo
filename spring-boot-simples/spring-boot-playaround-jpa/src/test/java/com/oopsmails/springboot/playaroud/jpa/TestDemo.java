package com.oopsmails.springboot.playaroud.jpa;


import com.oopsmails.springboot.playaroud.jpa.controller.ChessPlayerController;
import com.oopsmails.springboot.playaroud.jpa.entity.InstantItem;
import com.oopsmails.springboot.playaroud.jpa.model.BetterPlayerFullNameIntf;
import com.oopsmails.springboot.playaroud.jpa.model.ChessPlayer;
import com.oopsmails.springboot.playaroud.jpa.model.ChessTournament;
import com.oopsmails.springboot.playaroud.jpa.model.PlayerFullNameIntf;
import com.oopsmails.springboot.playaroud.jpa.model.PlayerName;
import com.oopsmails.springboot.playaroud.jpa.model.PlayerNameIntf;
import com.oopsmails.springboot.playaroud.jpa.model.TournamentIntf;
import com.oopsmails.springboot.playaroud.jpa.repository.ChessPlayerRepository;
import com.oopsmails.springboot.playaroud.jpa.repository.ChessTournamentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class TestDemo {

    @Autowired
    private ChessPlayerRepository playerRepo;

    @Autowired
    private ChessTournamentRepository tournamentRepo;

    @Autowired
    private ChessPlayerController playerController;

    @Autowired
    private EntityManager entityManager;

    /**
     * ToMany, never use FetchType.EAGER, instead using FetchType.LAZY, which is default.
     * in ChessPlayer class, change around EAGER to compare, preparing 20 JDBC statements vs preparing 1 JDBC statement
     * code, @ManyToMany(mappedBy = "players") //, fetch = FetchType.EAGER)
     */
    @Test
    public void findAll() {
        log.info("... findAll ...");

        List<ChessPlayer> players = playerRepo.findAll();
        assertThat(players.size()).isEqualTo(19);

        ChessPlayer player = players.get(0);
        log.info(player.getFirstName() + " " + player.getLastName());
    }

    /**
     * findWithTournamentsBy(), if no "LEFT JOIN FETCH", then "executing 20 JDBC statements".
     * with "@Query("SELECT p FROM ChessPlayer p LEFT JOIN FETCH p.tournaments")", then "executing 1 JDBC statements".
     * SQL: equivalent "left outer join"
     * <p>
     * Another option is "@EntityGraph(attributePaths = "tournaments")", basically doing the same thing.
     * This needs the following annotation in ChessPlayer class,
     * code: @NamedEntityGraph(name = "graph.ChessPlayerTournaments", attributeNodes = @NamedAttributeNode("tournaments"))
     */
    @Test
    @Transactional
    public void findWithTournaments() {
        log.info("... findWithTournaments ...");

        List<ChessPlayer> players = playerRepo.findWithTournamentsBy();
        assertThat(players.size()).isEqualTo(19);

        for (ChessPlayer player : players) {
            log.info(player.getFirstName() + " " + player.getLastName()
                    + " played in " + player.getTournaments().size() + " tournaments.");
        }
    }

    /**
     * if using "List<ChessPlayer> players " in ChessTournament class then "executing 18 JDBC statements".
     * Changing it to "Set<ChessPlayer> ", then "executing 4 JDBC statements", which is as expect, i.e, select tournament,
     * select player, update tournament version, delete from chess_tournament_players.
     */
    @Test
    @Transactional
    @Commit
    public void removePlayerFromTournament() {
        log.info("... removePlayerFromTournament ...");

        ChessTournament tournament = tournamentRepo.getById(2L);
        ChessPlayer removedPlayer = playerRepo.getById(34L);

        tournament.getPlayers().remove(removedPlayer);
    }

    /**
     * Based on the video, if using Entity class PlayerName, then select will get ALL columns.
     * But in this case, we only need first_name and last_name.
     * <p>
     * Using interface, PlayerNameIntf, will solve the issue, i.e, only getting columns we need ... scalar
     */
    @Test
    public void getPlayerNamesDto() {
        log.info("... getPlayerNamesDto ...");

        PlayerName player = playerRepo.findDtoByFirstName("Magnus");
        log.info("PlayerName: " + player.toString());
        log.info(player.getFirstName() + " " + player.getLastName());
    }

    @Test
    public void getPlayerNames() {
        log.info("... getPlayerNames ...");

        PlayerNameIntf player = playerRepo.findByFirstName("Magnus");
        log.info("PlayerNameIntf: " + player.toString());
        log.info(player.getFirstName() + " " + player.getLastName());
    }

    /**
     * Exception here: org.springframework.core.convert.ConverterNotFoundException:
     * <p>
     * If using native queries, then USE INTERFACE!!!
     * <p>
     * see next test case, getPlayerNamesNative()
     */
    @Test
    public void getPlayerNamesDtoNative() {
        log.info("... getPlayerNamesDtoNative ...");

        try {
            PlayerName player = playerRepo.findDtoNativeByFirstName("Magnus");
            log.info(player.getFirstName() + " " + player.getLastName());
        } catch (ConverterNotFoundException e) {
            log.error(e.toString());
        }
    }

    @Test
    public void getPlayerNamesNative() {
        log.info("... getPlayerNamesNative ...");

        PlayerNameIntf player = playerRepo.findNativeByFirstName("Magnus");
        log.info(player.getFirstName() + " " + player.getLastName());
    }

    /**
     * Even using interface, TournamentIntf, we still get all columns, losing the advantage of DTO projection (using interface instead of Entity class) ...
     * in PlayerFullNameIntf, @Value("#{target.lastName +', ' + target.firstName}"), but still selecting all columns from chess_tournament_players ...
     * <p>
     * Because using expression language, Spring JPA will get all columns of Entity, and apply expression language.
     * <p>
     * See BetterPlayerFullNameIntf to resolve it.
     */
    @Test
    @Transactional
    public void getTournamentWithPlayers() {
        log.info("... getTournamentWithPlayers ...");

        TournamentIntf tournament = tournamentRepo.findByName("Tata Steel Chess Tournament 2021");

        log.info("======== Test Output ===========");
        log.info(tournament.getPlayers().size() + " players played in the " + tournament.getName() + " tournament.");
    }

    @Test
    public void getPlayerFullNames() {
        log.info("... getPlayerFullNames ...");

        PlayerFullNameIntf player = playerRepo.findFullNameByFirstName("Magnus");
        log.info(player.getFullName());
    }

    /**
     * Using BetterPlayerFullNameIntf, will ONLY select first_name and last_name columns as we need.
     */
    @Test
    public void getBetterPlayerFullNames() {
        log.info("... getBetterPlayerFullNames ...");

        BetterPlayerFullNameIntf player = playerRepo.findBetterFullNameByFirstName("Magnus");
        log.info(player.getFullName());
    }

    /**
     * Each user has their own Hibernate Session, i.e, 1st Level Cache.
     * <p>
     * Then users cannot get benefit of caching data of other user's session.
     * This is why 2nd Level Cache is introduced.
     * <p>
     * There is another "Query Cache" is kind of same as 2nd Level Cache.
     * <p>
     * 2nd Level Cache needs to be activated in application.properties, it is
     * - Transparent usage
     * - PersistenceProvider doesn't need to provide it
     * <p>
     * see, spring.jpa.properties.javax.persistence.sharedCache.mode=ENABLE_SELECTIVE
     * <p>
     * ENABLE_SELECTIVE, means we have to annotate Entity class, @Cacheable
     * <p>
     * Other options, ALL, NONE, DISABLE_SELECTIVE, UNSPECIFIED
     * <p>
     * Note: ChessPlayer class,
     * code, @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
     * <p>
     * See in log, "performing 1 L2C puts" for the first call and "performing 1 L2C hits" for the second call.
     */
    @Test
    public void getPlayerUsingCache() {
        log.info("... getPlayerUsingCache ...");

        playerController.getPlayerById(1L);
        playerController.getPlayerById(1L);
    }

    @Test
    public void testGetDbTimestamp() {
        Query query = this.entityManager.createNativeQuery("SELECT CURRENT_TIMESTAMP AS DATE_VALUE", InstantItem.class);
        InstantItem instantItem = (InstantItem) query.getSingleResult();
        LocalDateTime now = LocalDateTime.ofInstant(instantItem.getDate(), TimeZone.getDefault().toZoneId());
        log.info("DB current timestamp: {}", now);
    }
}
