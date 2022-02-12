package com.codenjoy.dojo.sample.services;

/*-
 * #%L
 * expansion - it's a dojo-like platform from developers to developers.
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


import com.codenjoy.dojo.services.event.Calculator;
import com.codenjoy.dojo.services.settings.AllSettings;
import com.codenjoy.dojo.services.settings.PropertiesKey;
import com.codenjoy.dojo.services.settings.SettingsImpl;

import java.util.Arrays;
import java.util.List;

import static com.codenjoy.dojo.sample.services.GameSettings.Keys.*;

public class GameSettings extends SettingsImpl implements AllSettings<GameSettings> {

    public enum Keys implements PropertiesKey {

        GET_GOLD_SCORE,
        WIN_ROUND_SCORE,
        HERO_DIED_PENALTY,
        KILL_OTHER_HERO_SCORE,
        KILL_ENEMY_HERO_SCORE,
        SCORE_COUNTING_TYPE;

        private String key;

        Keys() {
            key = key(GameRunner.GAME_NAME);
        }

        @Override
        public String key() {
            return key;
        }
    }

    @Override
    public List<Key> allKeys() {
        return Arrays.asList(Keys.values());
    }

    public GameSettings() {
        initAll();

        integer(GET_GOLD_SCORE, 30);
        integer(WIN_ROUND_SCORE, 100);
        integer(HERO_DIED_PENALTY, -20);
        integer(KILL_OTHER_HERO_SCORE, 5);
        integer(KILL_ENEMY_HERO_SCORE, 10);

        Levels.setup(this);
    }

    public Calculator<Void> calculator() {
        return new Calculator<>(new Scores(this));
    }
}