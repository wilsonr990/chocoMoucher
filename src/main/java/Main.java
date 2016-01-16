import java.awt.*;
import java.io.IOException;

/**
 *
 * @author wilsonr
 */
public class Main {
    
    static public void printMap( char[] map){
        int count = 0;
        for (char cell: map){
            System.out.print( cell + ", " );
            if(++count == 8){
                count=0;
                System.out.print( "\n" );
            }
        }
    }
    
    public static void main(String[] args) throws CantCaptureScreen, IOException {
//        int i=0;
//        while (true){
//            Rectangle rectangle = null;
//            Image image = new Image(rectangle);
//            image.saveImage("img" + i++ + ".png");
//        }
        playGame();
    }

    private static void playGame() {
        try {
            ChocoMouche gameInterface;
            gameInterface = new ChocoMouche();

            Chocomoucher gamer;
            gamer = new Chocomoucher( gameInterface );

            gamer.play();
        } catch (NoOpenGame ex) {
            System.err.println("No Game Is Open");
        }
    }

}
