package com.codenjoy.dojo.sample.client;

import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.sample.model.Elements;
import com.codenjoy.dojo.services.Point;

import java.util.LinkedList;
import java.util.List;

import static com.codenjoy.dojo.services.PointImpl.pt;

/**
 * User: oleksandr.baglai
 */
public class Board extends AbstractBoard<Elements> {

    @Override
    public Elements valueOf(char ch) {
        return Elements.valueOf(ch);
    }

    public List<Point> getBarriers() {
        List<Point> all = getWalls();
        return removeDuplicates(all);
    }

    public List<Point> getWalls() {
        return get(Elements.WALL);
    }

    public boolean isBarrierAt(int x, int y) {
        return getBarriers().contains(pt(x, y));
    }

    public Point getMe() {
        return get(Elements.DEAD_HERO,
                Elements.HERO).get(0);
    }

    public boolean isGameOver() {
        return !get(Elements.DEAD_HERO).isEmpty();
    }

    public boolean isBombAt(int x, int y) {
        return get(Elements.BOMB).contains(pt(x, y));
    }
}