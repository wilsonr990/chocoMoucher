package models;

import Exceptions.ErrorInImageResources;
import Exceptions.FileAlreadyExists;
import Image.ImageHolder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Created by wilsonr on 1/26/2016.
 */
public class Analyzer {
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private String dataPath = "analyzed";

    public void startAnalysis() throws ErrorInImageResources {
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

    private void initializeThread() throws ErrorInImageResources {
        File file = new File("data");
        if (!file.exists()) {
            showMessageDialog(null, "No Data File Found");
            return;
        }

        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.getName().equals("LIVE")) continue;
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

                    if (let1.equals(let2)) {
                        return val1.compareTo(val2);
                    } else
                        return let1.compareTo(let2);
                }
            });
            final File finalFile = new File("Analyzed\\" + f.getName() + "\\");
            if (!finalFile.exists()) {
                finalFile.mkdir();
            }
//            Differences(imageFiles, finalFile);
//            Similarities(imageFiles, finalFile);
            ApplyMask(imageFiles, finalFile);
//            Unique(imageFiles, finalFile);
//            FitPattern(imageFiles, finalFile, "f.png");
        }

    }

    private void Differences(final File[] imageFiles, final File finalFile) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    ImageHolder previous = null;
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
                } catch (ErrorInImageResources cantReadFile) {
                    showMessageDialog(null, "Cant read file!");
                }
                showMessageDialog(null, "ENDED PROCESS!!");
            }
        };
        thread.start();
        threads.add(thread);
    }

    private void Similarities(final File[] imageFiles, final File finalFile) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    ImageHolder similarity = null;
                    for (File image : imageFiles) {
                        if (interrupted()) return;

                        ImageHolder current = new ImageHolder(image);
                        similarity = current.Similarity(similarity);
                        similarity.saveImage(finalFile.getAbsolutePath() + "\\" + i++ + ".png");
                    }
                    threads.remove(this);
                } catch (FileAlreadyExists fileAlreadyExists) {
                    showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                } catch (IOException e) {
                    showMessageDialog(null, "Cant manage files!");
                } catch (ErrorInImageResources cantReadFile) {
                    showMessageDialog(null, "Cant read file!");
                }
                showMessageDialog(null, "ENDED PROCESS!!");
            }
        };
        thread.start();
        threads.add(thread);
    }

    private void Unique(final File[] imageFiles, final File finalFile) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    ImageHolder current = null;
                    for (File image : imageFiles) {
                        if (interrupted()) return;

                        if (current == null) current = new ImageHolder(image);
                        else current = current.Similarity(new ImageHolder(image));
                    }
                    current.saveImage(finalFile.getAbsolutePath() + "\\" + i++ + ".png");
                    threads.remove(this);
                } catch (FileAlreadyExists fileAlreadyExists) {
                    showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                } catch (IOException e) {
                    showMessageDialog(null, "Cant manage files!");
                } catch (ErrorInImageResources cantReadFile) {
                    showMessageDialog(null, "Cant read file!");
                }
                showMessageDialog(null, "ENDED PROCESS!!");
            }
        };
        thread.start();
        threads.add(thread);
    }

    private void FitPattern(final File[] imageFiles, final File finalFile, final String patternFile) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int i = 0;
                    ImageHolder pattern = new ImageHolder(patternFile);
                    for (File image : imageFiles) {
                        if (interrupted()) return;

                        ImageHolder imageHolder = new ImageHolder(image);
                        if (imageHolder.findSubImage(pattern) != null)
                            imageHolder.saveImage(finalFile.getAbsolutePath() + "\\r" + i++ + ".png");
                    }
                    threads.remove(this);
                } catch (FileAlreadyExists fileAlreadyExists) {
                    showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                } catch (IOException e) {
                    showMessageDialog(null, "Cant manage files!");
                } catch (ErrorInImageResources cantReadFile) {
                    showMessageDialog(null, "Cant read file!");
                }
                showMessageDialog(null, "ENDED PROCESS!!");
            }
        };
        thread.start();
        threads.add(thread);
    }

    private void ApplyMask(final File[] imageFiles, final File finalFile) throws ErrorInImageResources {
        final ImageHolder mask = new ImageHolder("mask.png");
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    List<ImageHolder> saved = new ArrayList<ImageHolder>();
                    URL resource = getClass().getClassLoader().getResource("base.png");
                    File file = new File(resource.getFile()).getParentFile();
                    File[] savedImages = file.listFiles();

                    for (File img : savedImages) {
                        String name = img.getName();
                        if (name.contains(".png"))
                            saved.add(new ImageHolder(name));
                    }

                    int i = 0;
                    for (File image : imageFiles) {
                        if (interrupted()) return;

                        ImageHolder current = new ImageHolder(image);
                        ArrayList<ImageHolder> masked = current.getMaskedImages(mask);
                        for (ImageHolder img : masked) {
                            if (!alreadySaved(img, saved)) {
                                saved.add(img);
                                img.saveImage(finalFile.getAbsolutePath() + "\\r" + i++ + ".png");
                            }
                        }
                    }
                    threads.remove(this);
                } catch (FileAlreadyExists fileAlreadyExists) {
                    showMessageDialog(null, "The path already exists! ( " + dataPath + " )");
                } catch (IOException e) {
                    showMessageDialog(null, "Cant manage files!");
                } catch (ErrorInImageResources cantReadFile) {
                    showMessageDialog(null, "Cant read file!");
                }
                showMessageDialog(null, "ENDED PROCESS!!");
            }

            private boolean alreadySaved(ImageHolder img, List<ImageHolder> saved) {
                for (ImageHolder image : saved) {
                    if (img.findSubImage(image) != null)
                        return true;
                }
                return false;
            }
        };
        thread.start();
        threads.add(thread);
    }

    public String getDataPath() {
        return dataPath;
    }
}
