package com.codenjoy.dojo.sample.model;

import com.codenjoy.dojo.services.*;

/**
 * А вот тут немного хак :) Дело в том, что фреймворк изначально не поддерживал игры типа "все на однмо поле", а потому
 * пришлось сделать этот декоратор. Борда (@see Sample) - одна на всех, а игры (@see SingleSample) у каждого своя. Кода тут не много.
 */
public class SingleSample implements Game {

    private Printer printer;
    private Player player;
    private Sample game;

    public SingleSample(Sample game, EventListener listener, PrinterFactory factory) {
        this.game = game;
        this.player = new Player(listener);
        this.printer = factory.getPrinter(game.reader(), player);
    }

    @Override
    public Joystick getJoystick() {
        return player.getHero();
    }

    @Override
    public int getMaxScore() {
        return player.getMaxScore();
    }

    @Override
    public int getCurrentScore() {
        return player.getScore();
    }

    @Override
    public boolean isGameOver() {
        return !player.hero.isAlive();
    }

    @Override
    public void newGame() {
        game.newGame(player);
    }

    @Override
    public String getBoardAsString() {
        return printer.print();
    }

    @Override
    public void destroy() {
        game.remove(player);
    }

    @Override
    public void clearScore() {
        player.clearScore();
    }

    @Override
    public Point getHero() {
        return player.getHero();
    }

    @Override
    public void tick() {
        game.tick();
    }

    public Player getPlayer() {
        return player;
    }

}
