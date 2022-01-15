package com.codenjoy.dojo.sample;

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
import com.codenjoy.dojo.sample.services.GameRunner;
import com.codenjoy.dojo.sample.services.GameSettings;
import com.codenjoy.dojo.services.Dice;
import org.junit.Test;

import static com.codenjoy.dojo.utils.TestUtils.assertPerformance;
import static org.junit.Assert.assertEquals;

public class PerformanceTest {

    @Test
    public void test() {

        // about 6.4 sec
        int players = 30;
        int ticks = 1000;

        int expectedCreation = 1000;
        int expectedPrint = 3000;
        int expectedTick = 2800;

        Dice dice = new DiceGenerator().getDice(2000);
        GameRunner runner = new GameRunner(){

            @Override
            public Dice getDice() {
                return dice;
            }

            @Override
            public GameSettings getSettings() {
                return new GameSettings();
            }
        };

        boolean printBoard = false;
        String board = assertPerformance(runner,
                players, ticks,
                expectedCreation, expectedPrint, expectedTick,
                printBoard);

        assertEquals(
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n" +
                "☼ x☻  ☼xxxxx☻☻x☻x☼$ $ ☼\n" +
                "☼xx xx☼xxxxxxxxxx☼    ☼\n" +
                "☼xxxxx☼xx ☼  xx☼☼☼ xxx☼\n" +
                "☼xxxxx☼ ☻ ☼    x ☼ x x☼\n" +
                "☼x$xxx☼☼☼☼☼☼xxxx ☼☼x ☻☼\n" +
                "☼xxxxx ☻☼  xx xxxxxx  ☼\n" +
                "☼☼☼xxxxx☼  x☼xx☻xxx☻x☻☼\n" +
                "☼  xx☻ xxx☻x☼☻xxx☼xxxx☼\n" +
                "☼☻xxxxxxxx☻ ☼    ☼xxxx☼\n" +
                "☼☼☼☼☼☼☼☼xxx ☼☼☼☼☼☼xxx ☼\n" +
                "☼xxx ☻☼ xxxx☼ xx  xxx ☼\n" +
                "☼xxx☻x☼ xx☻x☼xxxxxxxx ☼\n" +
                "☼xxx☻x☼ xxxx☼x    ☼xxx☼\n" +
                "☼  xxxxxxx xxx    ☼ $$☼\n" +
                "☼  xxxxx $☼xxxx☼☼☼☼☼☼☼☼\n" +
                "☼  x☼☻☻x  ☼xxxxx  ☼xxx☼\n" +
                "☼  ☻☼ ☻xx ☼☻xx☻x  ☼xxx☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☻☻☻xxxxx☼\n" +
                "☼     ☼$ xxxxxx☺xxx ☻x☼\n" +
                "☼    $☼       ☼ xxxxx ☼\n" +
                "☼     $      $☼ x  $ $☼\n" +
                "☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼☼\n",
                board);
    }
}