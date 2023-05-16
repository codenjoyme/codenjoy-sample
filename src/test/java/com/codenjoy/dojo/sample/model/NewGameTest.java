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

import org.junit.Test;

public class NewGameTest extends AbstractGameTest {

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
        dice(2, 1); // new gold
        tick();

        // then
        assertF("☼☼☼☼☼\n" +
                "☼☺ $☼\n" +
                "☼   ☼\n" +
                "☼$$$☼\n" +
                "☼☼☼☼☼\n");

        verifyAllEvents("[GET_GOLD]");
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
        dice(1, 2,  // new gold
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

        verifyAllEvents(
                "listener(0) => [GET_GOLD]\n" +
                "listener(1) => [GET_GOLD]\n");
    }
}