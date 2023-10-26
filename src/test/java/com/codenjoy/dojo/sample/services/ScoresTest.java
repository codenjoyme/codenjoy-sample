package com.codenjoy.dojo.sample.services;

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


import com.codenjoy.dojo.sample.TestGameSettings;
import com.codenjoy.dojo.services.event.EventObject;
import com.codenjoy.dojo.services.event.ScoresMap;
import com.codenjoy.dojo.utils.scorestest.AbstractScoresTest;
import org.junit.Test;

import static com.codenjoy.dojo.sample.services.GameSettings.Keys.*;

public class ScoresTest extends AbstractScoresTest {

    @Override
    public GameSettings settings() {
        return new TestGameSettings()
                .integer(GET_GOLD_SCORE, 30)
                .integer(HERO_DIED_PENALTY, -10)
                .integer(WIN_ROUND_SCORE, 20);
    }

    @Override
    protected Class<? extends ScoresMap> scores() {
        return Scores.class;
    }

    @Override
    protected Class<? extends Enum> eventTypes() {
        return Event.class;
    }

    @Test
    public void shouldCollectScores() {
        assertEvents("100:\n" +
                "GET_GOLD > +30 = 130\n" +
                "GET_GOLD > +30 = 160\n" +
                "GET_GOLD > +30 = 190\n" +
                "GET_GOLD > +30 = 220\n" +
                "HERO_DIED > -10 = 210\n" +
                "WIN_ROUND > +20 = 230\n" +
                "WIN_ROUND > +20 = 250\n" +
                "KILL_ENEMY_HERO > +10 = 260\n" +
                "KILL_ENEMY_HERO > +10 = 270\n" +
                "KILL_ENEMY_HERO > +10 = 280\n" +
                "KILL_OTHER_HERO > +5 = 285\n" +
                "KILL_OTHER_HERO > +5 = 290\n" +
                "KILL_OTHER_HERO > +5 = 295\n" +
                "KILL_OTHER_HERO > +5 = 300\n" +
                "KILL_OTHER_HERO > +5 = 305");
    }

    @Test
    public void shouldNotBeLessThanZero() {
        assertEvents("1:\n" +
                "HERO_DIED > -1 = 0\n" +
                "HERO_DIED > +0 = 0\n" +
                "HERO_DIED > +0 = 0");
    }

    @Test
    public void shouldCleanScore() {
        assertEvents("0:\n" +
                "GET_GOLD > +30 = 30\n" +
                "(CLEAN) > -30 = 0\n" +
                "KILL_OTHER_HERO > +5 = 5");
    }

    @Test
    public void shouldCollectScores_whenGetGold() {
        assertEvents("100:\n" +
                "GET_GOLD > +30 = 130\n" +
                "GET_GOLD > +30 = 160");
    }

    @Test
    public void shouldCollectScores_whenHeroDied() {
        assertEvents("100:\n" +
                "HERO_DIED > -10 = 90\n" +
                "HERO_DIED > -10 = 80");
    }

    @Test
    public void shouldCollectScores_whenWinRound() {
        assertEvents("100:\n" +
                "WIN_ROUND > +20 = 120\n" +
                "WIN_ROUND > +20 = 140");
    }

    @Test
    public void shouldCollectScores_whenKillOtherHero() {
        assertEvents("140:\n" +
                "KILL_OTHER_HERO > +5 = 145\n" +
                "KILL_OTHER_HERO > +5 = 150");
    }

    @Test
    public void shouldCollectScores_whenKillEnemyHero() {
        // given
        settings.integer(KILL_ENEMY_HERO_SCORE, 1);

        assertEvents("100:\n" +
                "KILL_ENEMY_HERO > +1 = 101\n" +
                "KILL_ENEMY_HERO > +1 = 102");
    }
}