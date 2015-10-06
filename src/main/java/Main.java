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
    
    public static void main(String[] args){
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
