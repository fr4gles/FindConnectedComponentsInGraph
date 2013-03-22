
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Michal
 */
public class Main 
{
    public static Integer GRAPH_SIZE;
    public static void main(String[] args) 
    {
        Graph g = new Graph();
        
        try
        {
            Main.GRAPH_SIZE = Integer.parseInt(args[1]);
        }
        catch(NumberFormatException e)
        {
            System.err.println("BŁĄD ZŁY ROZMIAR"+e.getMessage());
        }
        
        File file = new File(args[0]);
        try 
        {
            Scanner sc = new Scanner(file).useDelimiter("\\D+");
            while(sc.hasNext())
            {
                Integer a = sc.nextInt();
                Integer b = sc.nextInt();
                
                g.addEdge(a, b);
            }
        } 
        catch (FileNotFoundException ex) 
        {
            System.err.println("BŁAD Z PARSOWANIEM PLIKU WEJSCIOWEGO"+ex.getMessage());
        }
        
        g.PrintList(g.getNextList());
        System.out.println(" - - ");
        g.PrintList(g.getHeadList());
    }    
}
