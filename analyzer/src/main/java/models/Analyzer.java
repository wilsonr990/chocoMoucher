package models;

import Exceptions.CantReadFile;
import Exceptions.FileAlreadyExists;
import Image.ImageHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 1/26/2016.
 */
public class Analyzer {
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private String dataPath = "analyzed";

    public void startAnalysis() {
        System.out.println("Start");
        if (threads.isEmpty()) {
            initializeThread();
        }
    }

    public void stopAnalysis() {
        System.out.println("Stop");
        for (Thread thread : threads) {
            thread.interrupt();
        }
        threads.clear();
    }

    private void initializeThread() {
        File file = new File("data");
        if (!file.exists()) {
            showMessageDialog(null, "No Data File Found");
            return;
        }

        File[] files = file.listFiles();
        for (File f : files) {
            System.out.println("will analyze " + f.getName());

            final File[] imageFiles = f.listFiles();
            Arrays.sort(imageFiles, new Comparator() {
                public int compare(Object f1, Object f2) {
                    String name1 = ((File) f1).getName();
                    Integer let1 = (int) name1.charAt(0);
                    Integer val1 = Integer.valueOf(((String) name1).substring(1, name1.lastIndexOf('.')));
                    String name2 = ((File) f2).getName();
                    Integer let2 = (int) name2.charAt(0);
                    Integer val2 = Integer.valueOf(((String) name2).substring(1, name2.lastIndexOf('.')));

                    if(let1.equals(let2)){
                        return val1.compareTo(val2);
                    }
                    else
                        return  let1.compareTo(let2);
                }
            });
            final File finalFile = new File("Analyzed\\" + f.getName() + "\\");
            if (!finalFile.exists()) {
                finalFile.mkdir();
            }
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        int i = 0;
                        ImageHolder previous = new ImageHolder();
                        for (File image : imageFiles) {
                            System.out.println("img " + image.getName());
                            if (interrupted()) return;

                            ImageHolder current = new ImageHolder(image);

                            ImageHolder difference = current.difference(previous);
                            difference.saveImage(finalFile.getAbsolutePath() + "\\" + i++ + ".png");

                            previous = current;
                        }
                        threads.remove(this);
                    } catch (FileAlreadyExists fileAlreadyExists) {
                        showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                    } catch (IOException e) {
                        showMessageDialog(null, "Cant manage files!");
                    } catch (CantReadFile cantReadFile) {
                        showMessageDialog(null, "Cant read file!");
                    }
                }
            };
            thread.start();
            threads.add(thread);
        }

    }

    public String getDataPath() {
        return dataPath;
    }
}
