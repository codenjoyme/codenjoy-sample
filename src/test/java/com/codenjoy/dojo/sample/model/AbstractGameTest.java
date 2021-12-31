package com.codenjoy.dojo.sample.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.games.sample.Element;
import com.codenjoy.dojo.sample.TestGameSettings;
import com.codenjoy.dojo.sample.services.Event;
import com.codenjoy.dojo.sample.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.Game;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.TestUtils;
import com.codenjoy.dojo.utils.events.EventsListenersAssert;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import com.codenjoy.dojo.whatsnext.WhatsNextUtils;
import org.junit.After;
import org.junit.Before;
import org.mockito.stubbing.OngoingStubbing;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.utils.TestUtils.asArray;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class AbstractGameTest {

    private List<EventListener> listeners;
    private List<Game> games;
    private List<Player> players;

    private Dice dice;
    private PrinterFactory<Element, Player> printer;
    private Sample field;
    private GameSettings settings;
    private EventsListenersAssert events;
    private Level level;

    @Before
    public void setup() {
        listeners = new LinkedList<>();
        players = new LinkedList<>();
        games = new LinkedList<>();

        dice = mock(Dice.class);
        settings = new TestGameSettings();
        setupSettings();
        printer = new PrinterFactoryImpl<>();
        events = new EventsListenersAssert(() -> listeners, Event.class);
    }

    @After
    public void after() {
        verifyAllEvents("");
        SmartAssert.checkResult();
    }

    public void dice(int... ints) {
        if (ints.length == 0) return;
        OngoingStubbing<Integer> when = when(dice.next(anyInt()));
        for (int i : ints) {
            when = when.thenReturn(i);
        }
    }

    public void givenFl(String... maps) {
        int levelNumber = LevelProgress.levelsStartsFrom1;
        settings.setLevelMaps(levelNumber, maps);
        level = settings.level(levelNumber, dice, Level::new);

        beforeCreateField();

        field = new Sample(dice, null, settings);
        field.load(level.map(), this::givenPlayer);

        setupHeroesDice();

        games = WhatsNextUtils.newGameForAll(players, printer, field);

        afterCreateField();
    }

    private void setupHeroesDice() {
        dice(asArray(level.heroes()));
    }

    private void beforeCreateField() {
        // settings / level pre-processing
    }

    private void afterCreateField() {
        // settings / field post-processing
    }

    protected Player givenPlayer() {
        EventListener listener = mock(EventListener.class);
        listeners.add(listener);

        Player player = new Player(listener, settings);
        players.add(player);
        return player;
    }

    public Player givenPlayer(Point pt) {
        Player player = givenPlayer();

        dice(asArray(asList(pt)));
        Game game = WhatsNextUtils.newGame(player, printer, field);
        games.add(game);

        return players.get(players.size() - 1);
    }

    public void assertEquals(Object expected, Object actual) {
        SmartAssert.assertEquals(expected, actual);
    }

    protected void setupSettings() {
        // do something with settings
    }

    public void tick() {
        field.tick();
    }

    // getters & asserts

    public void verifyAllEvents(String expected) {
        assertEquals(expected, events().getEvents());
    }

    public void assertScores(String expected) {
        assertEquals(expected,
                TestUtils.collectHeroesData(players, "scores", true));
    }

    public GameSettings settings() {
        return settings;
    }

    public Sample field() {
        return field;
    }

    public EventsListenersAssert events() {
        return events;
    }

    public void assertF(String expected, int index) {
        assertEquals(expected, game(index).getBoardAsString());
    }

    public Game game(int index) {
        return games.get(index);
    }

    public Player player(int index) {
        return players.get(index);
    }

    public Hero hero(int index) {
        return (Hero) game(index).getPlayer().getHero();
    }

    // getters, if only one player

    public void assertF(String expected) {
        assertF(expected, 0);
    }

    public Game game() {
        return game(0);
    }

    public Player player() {
        return player(0);
    }

    public Hero hero() {
        return hero(0);
    }
}