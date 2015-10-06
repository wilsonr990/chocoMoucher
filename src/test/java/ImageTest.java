/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
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
public class ImageTest {
    
    public ImageTest() {
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
    public void testGetWidth() {
        try {
            Image instance = new Image("test.png");
            int expResult = 2;
            int result = instance.getWidth();
            assertEquals(expResult, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }

    @Test
    public void testGetHeight() {
        try {
            Image instance = new Image("test.png");
            int expResult = 2;
            int result = instance.getHeight();
            assertEquals(expResult, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }

    @Test
    public void testGetRGB() {
        try {
            Image instance = new Image("test.png");
            int result = instance.getRGB(0, 0);
            assertEquals( Color.WHITE.getRGB(), result);
            result = instance.getRGB(1, 1);
            assertEquals( Color.WHITE.getRGB(), result);
            result = instance.getRGB(1, 0);
            assertEquals( Color.BLACK.getRGB(), result);
            result = instance.getRGB(0, 1);
            assertEquals( Color.BLACK.getRGB(), result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }

    @Test
    public void testGetDimension() {
        try {
            System.out.println("getDimension");
            Image instance = new Image("test.png");
            Dimension expResult = new Dimension(2, 2);
            Dimension result = instance.getDimension();
            assertEquals(expResult, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }

    @Test
    public void testFindSubImage() {
        try {
            Image img = new Image("testSubImage.png");
            Image instance = new Image("testImage.png");
            Point expResult = new Point(5,5);
            Point result = instance.findSubImage(img);
            assertEquals(expResult, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }
    
    @Test
    public void testFindSubImageReturnNullIfImgIsNotFoundInInstance() {
        try {
            Image img = new Image("testImage.png");
            Image instance = new Image("testSubImage.png");
            Point result = instance.findSubImage(img);
            assertEquals(null, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }
    
    @Test
    public void throwExceptionIfFileDoesntExists() {
        try {
            Image img = new Image("nonExistantImage.png");
            fail("should not be loaded");
        } catch (CantReadFile ex) {
            assertTrue("Image doesntExists", true);
        }
    }
    
}
