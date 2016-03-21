/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import models.GameInterface;
import models.impl.ChocoMouche;
import models.impl.ChocomouchePlayer;
import org.junit.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * @author wilsonr
 */
public class AnalizerTest {

    public AnalizerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testProbabilityWhenMapIsInitial() {
        double[][] result = {{0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        double[][] p = a.findProbabilities();

        assertEquals(new Point(0, 0), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.005);
    }

    @Test
    public void testProbabilityWhenOnlyABombInMap() {
        int[][] map = {{9, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1}};
        double[][] result = {{1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        a.update(new Point(0, 0), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(0, 1), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.01);
    }

    @Test
    public void testProbabilityWhenManyBombsInMap() {
        int[][] map = {{9, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 9},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, 9, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, 9, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1}};
        double[][] result = {{1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 1},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 1.0, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 1, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (map[i][j] != -1)
                    a.update(new Point(i, j), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(0, 1), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.01);
    }

    @Test
    public void testProbabilityWhenNumbersAlredyHaveTheirBombsUncovered() {
        int[][] map = {{1, 9, -1, -1, -1, -1, 9, 2, 9},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, 9, 1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {9, 9, -1, -1, -1, -1, -1, -1, -1},
                {3, 9, -1, -1, -1, -1, -1, 1, 1},
                {-1, 2, 9, -1, -1, -1, -1, -1, 9}};
        double[][] result = {{0, 1, 0.5, 0.5, 0.5, 0.5, 1, 0, 1},
                {0, 0, 0.5, 0, 0, 0, 0, 0, 0},
                {0.5, 0.5, 0.5, 1, 0, 0, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0, 0, 0, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {1, 1, 0.5, 0.5, 0.5, 0.5, 0, 0, 0},
                {0, 1, 0, 0.5, 0.5, 0.5, 0, 0, 0},
                {0, 0, 1, 0.5, 0.5, 0.5, 0, 0, 1}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (map[i][j] != -1)
                    a.update(new Point(i, j), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(1, 0), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.01);
    }

    @Test
    public void testProbabilitiesForIndividualNumbers() {
        int[][] map = {{1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 1},
                {2, -1, -1, 4, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, 9, 2, -1, -1, -1}};
        double[][] result = {{0.0, 0.3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.3, 0.3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.2, 0.2},
                {0.4, 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.2, 0},
                {0, 0.4, 0.5, 0, 0.5, 0.5, 0.5, 0.2, 0.2},
                {0.4, 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.2, 0.2, 0.2, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 1, 0, 0.2, 0.5, 0.5}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (map[i][j] != -1)
                    a.update(new Point(i, j), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(2, 7), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.05);
    }

    @Test
    public void testProbabilitiesForCloseNumbers() {
        int[][] map = {{1, -1, 1, -1, -1, -1, -1, -1, 1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 1},
                {-1, -1, -1, -1, -1, -1, -1, -1, 2},
                {1, -1, -1, -1, -1, -1, -1, -1, -1},
                {1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {1, -1, -1, -1, -1, 3, 1, -1, 1}};
        double[][] result = {{0.0, 0.2, 0, 0.2, 0.5, 0.5, 0.5, 0.7, 0},
                {0.6, 0.2, 0.2, 0.2, 0.5, 0.5, 0.5, 0.2, 0.2},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.3, 0},
                {0.3, 0.3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.3, 0},
                {0, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5, 0.7, 0.7},
                {0, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.3, 0.3, 0.5, 0.5, 1, 0.5, 0.5, 0, 1},
                {0, 0.3, 0.5, 0.5, 1, 0, 0, 0, 0}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                if (map[i][j] != -1)
                    a.update(new Point(i, j), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(6, 7), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.04);
    }

    @Test
    public void testExpantion() {
        int[][] map = {{0, 0, 0, 1, -1, -1, -1, -1, -1},
                {1, 1, 0, 1, -1, -1, -1, -1, -1},
                {-1, 1, 0, 1, -1, -1, -1, -1, -1},
                {-1, 1, 1, 1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, 2, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, 3, 2},
                {-1, -1, -1, -1, -1, -1, -1, 1, 0}};
        double[][] result = {{0, 0, 0, 0, 0, 0.5, 0.5, 0.5, 0.5},
                {0, 0, 0, 0, 1, 0.5, 0.5, 0.5, 0.5},
                {1, 0, 0, 0, 0, 0.5, 0.5, 0.5, 0.5},
                {0, 0, 0, 0, 0, 0.5, 0.5, 0.5, 0.5},
                {0, 0, 0, 1, 0, 0.2, 0.2, 0.2, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.2, 0, 1, 1},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.2, 0.2, 0, 0},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.8, 0, 0}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        a.update(new Point(0, 0), map);
        a.update(new Point(7, 8), map);
        a.update(new Point(5, 6), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(0, 4), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++) {
                System.out.println(i + " " + j);
                Assert.assertEquals(result[i][j], p[i][j], 0.04);
            }
    }

    @Test
    public void testExpantion1() {
        int[][] map = {{0, 1, -1, -1, -1, -1, -1, -1, -1},
                {2, 3, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, 2, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1}};
        double[][] result = {{0, 0, 0.8, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0, 0, 0.2, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5},
                {1, 1, 0, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.2, 0.2, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        a.update(new Point(0, 0), map);
        a.update(new Point(2, 2), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(1, 2), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.04);
    }

    @Test
    public void testExpantion2() {
        int[][] map = {{0, 2, -1, -1, -1, -1, 1, 0, 0},
                {1, 3, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1}};
        double[][] result = {{0, 0, 0.8, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0, 0, 0.2, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5},
                {1, 1, 0, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.2, 0.2, 0.2, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5}};

        ChocomouchePlayer a = new ChocomouchePlayer(new ChocoMouche(), new GameInterface());
        a.update(new Point(0, 0), map);
        //a.update( new Point(2,2), map);
        double[][] p = a.findProbabilities();

        assertEquals(new Point(1, 2), a.bestMove().get(0));
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 9; j++)
                Assert.assertEquals(result[i][j], p[i][j], 0.04);
    }
}
