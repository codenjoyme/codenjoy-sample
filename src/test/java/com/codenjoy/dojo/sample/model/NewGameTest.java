package com.codenjoy.dojo.sample.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2022 Codenjoy
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

import com.codenjoy.dojo.sample.services.Event;
import com.codenjoy.dojo.sample.services.GameRunner;
import com.codenjoy.dojo.sample.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.GameType;
import com.codenjoy.dojo.services.multiplayer.TriFunction;
import com.codenjoy.dojo.utils.gametest.NewAbstractBaseGameTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static com.codenjoy.dojo.sample.services.GameSettings.Keys.*;
import static com.codenjoy.dojo.services.event.Mode.CUMULATIVELY;
import static com.codenjoy.dojo.services.round.RoundSettings.Keys.ROUNDS_ENABLED;

public class NewGameTest extends NewAbstractBaseGameTest<Player, Sample, GameSettings, Level, Hero> {

    @Before
    public void setup() {
        super.setup();
    }

    @After
    public void after() {
        super.after();
    }

    @Override
    protected void setupSettings(GameSettings settings) {
        settings.initScore(CUMULATIVELY)
                .bool(ROUNDS_ENABLED, false)
                .integer(GET_GOLD_SCORE, 30)
                .integer(HERO_DIED_PENALTY, -10)
                .integer(WIN_ROUND_SCORE, 20);;
    }

    @Override
    protected Function<String, Level> createLevel() {
        return Level::new;
    }

    @Override
    protected BiFunction<EventListener, GameSettings, Player> createPlayer() {
        return Player::new;
    }

    @Override
    protected TriFunction<Dice, Level, GameSettings, Sample> createField() {
        return Sample::new;
    }

    @Override
    protected Class<?> eventClass() {
        return Event.class;
    }

    @Override
    protected GameType gameType() {
        return new GameRunner();
    }

    @Test
    public void testOnePlayer() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼$ $☼\n" +
                "☼ ☺ ☼\n" +
                "☼$ $☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼$ $☼\n" +
                "☼ ☺ ☼\n" +
                "☼$ $☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼$☺$☼\n" +
                "☼   ☼\n" +
                "☼$ $☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().left();
        dice().will(2, 1); // new gold
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☺ $☼\n" +
                "☼   ☼\n" +
                "☼$$$☼\n" +
                "☼☼☼☼☼\n");
    }

    @Test
    public void testTwoPlayers() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼$ $☼\n" +
                "☼☺ ☺☼\n" +
                "☼$ $☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼$ $☼\n" +
                "☼☺ ☻☼\n" +
                "☼$ $☼\n" +
                "☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼\n" +
                "☼$ $☼\n" +
                "☼☻ ☺☼\n" +
                "☼$ $☼\n" +
                "☼☼☼☼☼\n", 1);

        // when
        hero(0).up();
        hero(1).down();
        dice().will(1, 2,  // new gold
                    3, 2); // new gold
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☺ $☼\n" +
                "☼$ $☼\n" +
                "☼$ ☻☼\n" +
                "☼☼☼☼☼\n", 0);

        assertF("☼☼☼☼☼\n" +
                "☼☻ $☼\n" +
                "☼$ $☼\n" +
                "☼$ ☺☼\n" +
                "☼☼☼☼☼\n", 1);
    }
}