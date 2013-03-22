
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Michal
 */
public class Graph 
{
    private List<Integer> next;
    private List<Integer> head;
    
    public Graph()
    {
        next = new ArrayList<>();
        head = new ArrayList<>();
        
        for(int i=0;i<Main.GRAPH_SIZE;++i)
            next.add(-1);
        
    }
    
    public void addEdge(int a, int b)
    {
        if(next.get(a) < 0)
        {
            next.set(a, head.size()+Main.GRAPH_SIZE+1);
            
            head.add(b);
            
            next.add(-1);
        }
        else
        {
            head.add(b);
            
            next.add(-1);
            
            int begin = next.get(a);
            while(true)
            {
                if( (next.get(begin)) > -1)
                    begin = next.get(begin);
                else
                    break;
            }
            
            next.set(begin, head.size()+Main.GRAPH_SIZE-1);
        }
    }
    
    public List<Integer> getNextList()
    {
        return next;
    }
    
    public List<Integer> getHeadList()
    {
        return head;
    }
    
    public void PrintList(List<Integer> listToPrint)
    {
        if(listToPrint!=null)
        {
            for(Integer v : listToPrint)
            {
                System.out.println(v);
            }
        }
    }
}

class Vertex
{
    private int id;
}

class Edge
{
    
}