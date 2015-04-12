package com.codenjoy.dojo.sample.client.ai;

import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.LocalGameRunner;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.sample.client.Board;
import com.codenjoy.dojo.sample.model.Elements;
import com.codenjoy.dojo.sample.services.GameRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;

import java.util.List;

/**
 * Это алгоритм твоего бота. Он будет запускаться в игру с первым
 * зарегистрировавшимся игроком, чтобы ему не было скучно играть самому.
 * Реализуй его как хочешь, хоть на Random.
 * Для его запуска воспользуйся методом {@see ApofigSolver#main}
 */
public class ApofigSolver implements Solver<Board> {

    private DeikstraFindWay way;

    public ApofigSolver(Dice dice) {
        this.way = new DeikstraFindWay();
    }

    public DeikstraFindWay.Possible possible(final Board board) {
        return new DeikstraFindWay.Possible() {
            @Override
            public boolean possible(Point from, Direction where) {
                int x = from.getX();
                int y = from.getY();
                if (board.isBarrierAt(x, y)) return false;

                Point newPt = where.change(from);
                int nx = newPt.getX();
                int ny = newPt.getY();

                if (board.isOutOfField(nx, ny)) return false;

                if (board.isBarrierAt(nx, ny)) return false;
                if (board.isBombAt(nx, ny)) return false;

                return true;
            }

            @Override
            public boolean possible(Point atWay) {
                return true;
            }
        };
    }

    @Override
    public String get(final Board board) {
        if (board.isGameOver()) return "";
        List<Direction> result = getDirections(board);
        if (result.isEmpty()) return "";
        return result.get(0).toString();
    }

    public List<Direction> getDirections(Board board) {
        int size = board.size();
        Point from = board.getMe();
        List<Point> to = board.get(Elements.GOLD);
        DeikstraFindWay.Possible map = possible(board);
        return way.getShortestWay(size, from, to, map);
    }

    /**
     * Метод для запуска игры с текущим ботом. Служит для отладки.
     */
    public static void main(String[] args) {
        LocalGameRunner.run(new GameRunner(),
                new ApofigSolver(new RandomDice()),
                new Board());
//        start(WebSocketRunner.DEFAULT_USER, WebSocketRunner.Host.LOCAL);
    }

    public static void start(String name, WebSocketRunner.Host server) {
        try {
            WebSocketRunner.run(server, name,
                    new ApofigSolver(new RandomDice()),
                    new Board());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
