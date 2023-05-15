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
import com.codenjoy.dojo.services.*;
import com.codenjoy.dojo.services.multiplayer.FieldService;
import com.codenjoy.dojo.services.multiplayer.LevelProgress;
import com.codenjoy.dojo.services.multiplayer.Spreader;
import com.codenjoy.dojo.services.room.RoomService;
import com.codenjoy.dojo.utils.smart.SmartAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NewGameTest {

    private Deals all;
    private Dice dice;
    private RoomService rooms;

    private List<Deal> deals = new LinkedList<>();

    @Before
    public void before() {
        rooms = new RoomService();
        FieldService fields = new FieldService(0);
        Spreader spreader = new Spreader(fields);

        all = new Deals(spreader, rooms);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
        all.init(lock);
        all.onAdd(deal -> assertNotNull(deal));
        all.onRemove(deal -> assertNotNull(deal));

        dice = new DiceGenerator(message -> System.out.println(message)).getDice();
    }

    @Test
    public void test() {
        // given
        givenFl("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼ ☺ ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        // then
        assertF("☼☼☼☼☼\n" +
                "☼   ☼\n" +
                "☼☺  ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");

        // when
        hero().up();
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☺  ☼\n" +
                "☼   ☼\n" +
                "☼   ☼\n" +
                "☼☼☼☼☼\n");
    }

    private void givenFl(String... map) {
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
                        .setLevelMaps(level, map);
            }
        };

        long now = Calendar.getInstance().getTimeInMillis();

        rooms.create("room", gameType);
        Deal deal = all.deal(PlayerSave.NULL, "room", "player", "callbackUrl", gameType, now);

        deals.add(deal);
    }

    private void tick() {
        all.tick();
    }

    @After
    public void after() {
        SmartAssert.checkResult();
    }

    // public getters

    public Deal deal() {
        return deal(0);
    }

    public Deal deal(int index) {
        return deals.get(index);
    }

    public Game game() {
        return game(0);
    }

    public Game game(int index) {
        return deal(index).getGame();
    }

    public Joystick hero() {
        return deal().getJoystick();
    }

    public Joystick hero(int index) {
        return deal(index).getJoystick();
    }

    // common asserts

    public void assertF(String expected) {
        assertF(expected, 0);
    }

    /**
     * Проверяет одну борду с заданным индексом.
     * @param expected Ожидаемое значение.
     * @param index Индекс игрока.
     */
    public void assertF(String expected, int index) {
        assertEquals(expected, game(index).getBoardAsString(true));
    }


}
