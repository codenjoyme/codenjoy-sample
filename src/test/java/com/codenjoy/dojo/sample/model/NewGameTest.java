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

import com.codenjoy.dojo.client.local.DiceGenerator;
import com.codenjoy.dojo.sample.TestGameSettings;
import com.codenjoy.dojo.sample.services.GameRunner;
import com.codenjoy.dojo.sample.services.GameSettings;
import com.codenjoy.dojo.services.Deal;
import com.codenjoy.dojo.services.Deals;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.PlayerSave;
import com.codenjoy.dojo.services.multiplayer.FieldService;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import com.codenjoy.dojo.services.multiplayer.Spreader;
import com.codenjoy.dojo.services.room.RoomService;
import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NewGameTest {

    @Test
    public void test() {
        // given
        RoomService rooms = new RoomService();
        FieldService fields = new FieldService(0);
        Spreader spreader = new Spreader(fields);
        Dice dice = new DiceGenerator(message -> System.out.println(message)).getDice();
        GameRunner gameType = new GameRunner(){
            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                int level = LevelProgress.levelsStartsFrom1;
                return new TestGameSettings()
                        .clearLevelMaps(level)
                        .setLevelMap(level,
                                "☼☼☼☼☼\n" +
                                "☼   ☼\n" +
                                "☼ ☺ ☼\n" +
                                "☼   ☼\n" +
                                "☼☼☼☼☼\n");
            }
        };

        Deals deals = new Deals(spreader, rooms);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        deals.init(lock);
        deals.onAdd(deal -> assertNotNull(deal));
        deals.onRemove(deal -> assertNotNull(deal));
        long now = Calendar.getInstance().getTimeInMillis();

        // when
        Deal deal = deals.deal(PlayerSave.NULL, "room", "player", "callbackUrl", gameType, now);

        deal.tick();

        // then
        assertEquals("☼☼☼☼☼\n" +
                    "☼   ☼\n" +
                    "☼☺  ☼\n" +
                    "☼   ☼\n" +
                    "☼☼☼☼☼\n",
                deal.getGame().getBoardAsString(true));
    }

}
