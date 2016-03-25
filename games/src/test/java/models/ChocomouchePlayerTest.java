package models;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import models.impl.ChocoMouche;
import models.impl.ChocomouchePlayer;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author wilsonr
 */
public class ChocomouchePlayerTest {
    private ChocoMouche mockGame;
    private GameInterface mockInterface;

    @Before
    public void setting() {
        mockGame = mock(ChocoMouche.class);
        mockInterface = mock(GameInterface.class);
    }

    @Test
    public void initialMap() {
        int[][] map = {{-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1}};

        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(ChocoMouche.Property.Lives, 3);
        properties.put(ChocoMouche.Property.TurnPercentage, 50);
        properties.put(ChocoMouche.Property.Map, map);
        when(mockGame.getGameProperties()).thenReturn(properties);
        ChocomouchePlayer player = new ChocomouchePlayer(mockGame);

        player.update();
        player.getBestMoves();
        List<Point> moves = player.getMoves();

        assertEquals(0.5, player.getMinProbability(), 0.01);
        assertEquals(new Point(0, 0), moves.get(0));
        assertEquals(72, moves.size());
    }

    @Test
    public void uncoveringAMine() {
        int[][] map = {{ 9, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1}};

        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(ChocoMouche.Property.Lives, 3);
        properties.put(ChocoMouche.Property.TurnPercentage, 50);
        properties.put(ChocoMouche.Property.Map, map);
        when(mockGame.getGameProperties()).thenReturn(properties);
        ChocomouchePlayer player = new ChocomouchePlayer(mockGame);

        player.update();
        player.getBestMoves();
        List<Point> moves = player.getMoves();

        assertEquals(0.5, player.getMinProbability(), 0.01);
        assertEquals(new Point(0, 1), moves.get(0));
        assertEquals(71, moves.size());
    }

    @Test
    public void uncoveringANumber() {
        int[][] map = {{ 1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                       {-1, -1, -1, -1, -1, -1, -1, -1, -1}};

        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(ChocoMouche.Property.Lives, 3);
        properties.put(ChocoMouche.Property.TurnPercentage, 50);
        properties.put(ChocoMouche.Property.Map, map);
        when(mockGame.getGameProperties()).thenReturn(properties);
        ChocomouchePlayer player = new ChocomouchePlayer(mockGame);

        player.update();
        player.getBestMoves();
        List<Point> moves = player.getMoves();

        assertEquals(0.333, player.getMinProbability(), 0.01);
        assertEquals(new Point(0, 1), moves.get(0));
        assertEquals(3, moves.size());
    }

    @Test
    public void uncoveringAZero() {
        int[][] map =  {{ 0,  1, -1, -1, -1, -1, -1, -1, -1},
                        { 1,  2, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                        {-1, -1, -1, -1, -1, -1, -1, -1, -1}};

        Map<Object, Object> properties = new HashMap<Object, Object>();
        properties.put(ChocoMouche.Property.Lives, 3);
        properties.put(ChocoMouche.Property.TurnPercentage, 50);
        properties.put(ChocoMouche.Property.Map, map);
        when(mockGame.getGameProperties()).thenReturn(properties);
        ChocomouchePlayer player = new ChocomouchePlayer(mockGame);

        player.update();
        player.getBestMoves();
        List<Point> moves = player.getMoves();

        assertEquals(0, player.getMinProbability(), 0.01);
        assertEquals(new Point(2, 2), moves.get(0));
        assertEquals(1, moves.size());
    }
}
