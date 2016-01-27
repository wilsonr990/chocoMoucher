package Image;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Exceptions.CantCaptureScreen;
import Exceptions.CantReadFile;
import Exceptions.FileAlreadyExists;
import org.junit.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 *
 * @author wilsonr
 */
public class ImageHolderTest {

    @Test
    public void testGetWidth() {
        try {
            ImageHolder instance = new ImageHolder("test.png");
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
            ImageHolder instance = new ImageHolder("test.png");
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
            ImageHolder instance = new ImageHolder("test.png");
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
            ImageHolder instance = new ImageHolder("test.png");
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
            ImageHolder img = new ImageHolder("testSubImage.png");
            ImageHolder instance = new ImageHolder("testImage.png");
            Point expResult = new Point(5,5);
            Point result = instance.findSubImage(img);
            assertEquals(expResult, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }

    @Test
    public void testFindSubImageReturnValidIfTheyAreEqual() {
        try {
            ImageHolder img = new ImageHolder("testImage.png");
            ImageHolder instance = new ImageHolder("testImage.png");
            Point expResult = new Point(0,0);
            Point result = instance.findSubImage(img);
            assertEquals(expResult, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }
    
    @Test
    public void testFindSubImageReturnNullIfImgIsNotFoundInInstance() {
        try {
            ImageHolder img = new ImageHolder("testImage.png");
            ImageHolder instance = new ImageHolder("testSubImage.png");
            Point result = instance.findSubImage(img);
            assertEquals(null, result);
        } catch (CantReadFile ex) {
            fail("Image should be loaded");
        }
    }
    
    @Test
    public void throwExceptionIfFileDoesntExists() {
        try {
            ImageHolder img = new ImageHolder("nonExistantpng");
            fail("should not be loaded");
        } catch (CantReadFile ex) {
            assertTrue("Image doesntExists", true);
        }
    }

    @Test
    public void saveImageInDisk() {
        String name = "saved.png";
        try {
            ImageHolder img = new ImageHolder((Rectangle) (null));
            img.saveImage(name);
        } catch (CantCaptureScreen ignored) {
            Assert.assertTrue(false);
        } catch (IOException ignored) {
            Assert.assertTrue(false);
        } catch (FileAlreadyExists fileAlreadyExists) {
            Assert.assertTrue(false);
        }
        try {
            ImageHolder img = new ImageHolder((Rectangle) (null));
            img.saveImage(name);
        } catch (CantCaptureScreen ignored) {
            Assert.assertTrue(false);
        } catch (IOException ignored) {
            Assert.assertTrue(false);
        } catch (FileAlreadyExists fileAlreadyExists) {
            Assert.assertTrue( new File(name).delete() );
        }
    }
}
