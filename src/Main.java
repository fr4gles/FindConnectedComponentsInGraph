
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Michal
 */
public class Main 
{
    public static int GRAPH_SIZE;
    public static void main(String[] args) 
    {
        try
        {
            Main.GRAPH_SIZE = Integer.parseInt(args[1])+1;
        }
        catch(NumberFormatException e)
        {
            System.err.println("BŁĄD ZŁY ROZMIAR"+e.getMessage());
        }
        
        Graph g = new Graph();
        
        File file = new File(args[0]);
        try 
        {
            Scanner sc = new Scanner(file).useDelimiter("\\D+");
            while(sc.hasNext())
            {
                Integer a = sc.nextInt();
                Integer b = sc.nextInt();
                
                g.addEdge(a, b);
                g.addEdge(b, a);
            }
        } 
        catch (FileNotFoundException ex) 
        {
            System.err.println("BŁAD Z PARSOWANIEM PLIKU WEJSCIOWEGO"+ex.getMessage());
        }
        
        System.out.println("Ilość Vertex'ów: "+(Main.GRAPH_SIZE-1));
        //g.PrintList();
        
        
        int ilosc_skladowych_spojnych = 0;
        for(int i=1;i<Main.GRAPH_SIZE;++i)
        {
            if(!g.GetDiscoveredList().get(i))
            {
                g.DFS(i);
                //System.out.println(".");
                ilosc_skladowych_spojnych++;
            }
        }
        
        System.out.println("Składowych spójnych : "+ilosc_skladowych_spojnych);
        
    }    
}
