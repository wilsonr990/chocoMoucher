/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chocomoucher;

import java.awt.Point;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wilsonr
 */
public class analizerTest {
    
    public analizerTest() {
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
        double[][] result ={{0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}};
        
        Analizer a = new Analizer( );
        double[][] p = a.findProbabilities();
        
        assertEquals( new Point(0,0), a.bestMove());
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.005);
    }
    
    @Test
    public void testProbabilityWhenOnlyABombInMap() {
        int[][] map ={{ 9,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1}};
        double[][] result ={{1.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}};
        
        Analizer a = new Analizer( );
        a.update(new Point(0,0), map);
        double[][] p = a.findProbabilities();
        
        assertEquals( new Point(0,1), a.bestMove());
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
                assertEquals( result[i][j], p[i][j], 0.01);
    }
    
    @Test
    public void testProbabilityWhenManyBombsInMap() {
        int[][] map ={{ 9,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1, 9},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1, 9,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1, 9,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1}};
        double[][] result ={{1.0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,  1},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,1.0,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,  1,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}};
        
        Analizer a = new Analizer( );
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            if( map[i][j]!=-1 )
                a.update( new Point(i,j), map );
        double[][] p = a.findProbabilities();
        
        assertEquals( new Point(0,1), a.bestMove());
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.01);
    }
    
    @Test
    public void testProbabilityWhenNumbersAlredyHaveTheirBombsUncovered() {
        int[][] map ={{ 1, 9,-1,-1,-1,-1, 9, 2, 9},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1, 9, 1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      { 9, 9,-1,-1,-1,-1,-1,-1,-1},
                      { 3, 9,-1,-1,-1,-1,-1, 1, 1},
                      {-1, 2, 9,-1,-1,-1,-1,-1, 9}};
        double[][] result ={{  0,  1,0.5,0.5,0.5,0.5,  1,  0,   1},
                            {  0,  0,0.5,  0,  0,  0,  0,  0,  0},
                            {0.5,0.5,0.5,  1,  0,  0,0.5,0.5,0.5},
                            {0.5,0.5,0.5,  0,  0,  0,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {  1,  1,0.5,0.5,0.5,0.5,  0,  0,  0},
                            {  0,  1,  0,0.5,0.5,0.5,  0,  0,  0},
                            {  0,  0,  1,0.5,0.5,0.5,  0,  0,  1}};
        
        Analizer a = new Analizer( );
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            if( map[i][j]!=-1 )
                a.update( new Point(i,j), map );
        double[][] p = a.findProbabilities();
        
        assertEquals( new Point(1,0), a.bestMove());
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.01);
    }
    
    @Test
    public void testProbabilitiesForIndividualNumbers() {
        int[][] map ={{ 1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1, 1},
                      { 2,-1,-1, 4,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1, 9, 2,-1,-1,-1}};
        double[][] result ={{0.0,0.3,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.3,0.3,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.2,0.2},
                            {0.4,0.4,0.5,0.5,0.5,0.5,0.5,0.2,  0},
                            {  0,0.4,0.5,  0,0.5,0.5,0.5,0.2,0.2},
                            {0.4,0.4,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.2,0.2,0.2,0.5,0.5},
                            {0.5,0.5,0.5,0.5,  1,  0,0.2,0.5,0.5}};
        
        Analizer a = new Analizer( );
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            if( map[i][j]!=-1 )
                a.update( new Point(i,j), map );
        double[][] p = a.findProbabilities();
        
        assertEquals( new Point(2,7), a.bestMove());
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.05);
    }
    
    @Test
    public void testProbabilitiesForCloseNumbers() {
        int[][] map ={{ 1,-1, 1,-1,-1,-1,-1,-1, 1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1, 1},
                      {-1,-1,-1,-1,-1,-1,-1,-1, 2},
                      { 1,-1,-1,-1,-1,-1,-1,-1,-1},
                      { 1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      { 1,-1,-1,-1,-1, 3, 1,-1, 1}};
        double[][] result ={{0.0,0.2,  0,0.2,0.5,0.5,0.5,0.7,  0},
                            {0.6,0.2,0.2,0.2,0.5,0.5,0.5,0.2,0.2},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.3,  0},
                            {0.3,0.3,0.5,0.5,0.5,0.5,0.5,0.3,  0},
                            {  0,0.2,0.5,0.5,0.5,0.5,0.5,0.7,0.7},
                            {  0,0.2,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.3,0.3,0.5,0.5,  1,0.5,0.5,  0,  1},
                            {  0,0.3,0.5,0.5,  1,  0,  0,  0,  0}};
        
        Analizer a = new Analizer( );
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            if( map[i][j]!=-1 )
                a.update( new Point(i,j), map );
        double[][] p = a.findProbabilities();
    
        assertEquals( new Point(6,7), a.bestMove());    
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.04);
    }
    
    @Test
    public void testExpantion() {
        int[][] map ={{ 0, 0, 0, 1,-1,-1,-1,-1,-1},
                      { 1, 1, 0, 1,-1,-1,-1,-1,-1},
                      {-1, 1, 0, 1,-1,-1,-1,-1,-1},
                      {-1, 1, 1, 1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1, 2,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1, 3, 2},
                      {-1,-1,-1,-1,-1,-1,-1, 1, 0}};
        double[][] result ={{  0,  0,  0,  0,  0,0.5,0.5,0.5,0.5},
                            {  0,  0,  0,  0,  1,0.5,0.5,0.5,0.5},
                            {  1,  0,  0,  0,  0,0.5,0.5,0.5,0.5},
                            {  0,  0,  0,  0,  0,0.5,0.5,0.5,0.5},
                            {  0,  0,  0,  1,  0,0.2,0.2,0.2,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.2,  0,  1,  1},
                            {0.5,0.5,0.5,0.5,0.5,0.2,0.2,  0,  0},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.8,  0,  0}};
        
        Analizer a = new Analizer( );
        a.update( new Point(0,0), map );
        a.update( new Point(7,8), map );
        a.update( new Point(5,6), map );
        double[][] p = a.findProbabilities();
    
        assertEquals( new Point(0,4), a.bestMove());    
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.04);
    }
    
    @Test
    public void testExpantion1() {
        int[][] map ={{ 0, 1,-1,-1,-1,-1,-1,-1,-1},
                      { 2, 3,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1, 2,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1},
                      {-1,-1,-1,-1,-1,-1,-1,-1,-1}};
        double[][] result ={{  0,  0,0.8,0.5,0.5,0.5,0.5,0.5,0.5},
                            {  0,  0,0.2,0.2,0.5,0.5,0.5,0.5,0.5},
                            {  1,  1,  0,0.2,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.2,0.2,0.2,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5},
                            {0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5}};
        
        Analizer a = new Analizer( );
        a.update( new Point(0,0), map );
        a.update( new Point(2,2), map );
        double[][] p = a.findProbabilities();
    
        assertEquals( new Point(1,2), a.bestMove());    
        for( int i=0; i<8; i++ ) for( int j=0; j<9; j++ )
            assertEquals( result[i][j], p[i][j], 0.04);
    }
}
