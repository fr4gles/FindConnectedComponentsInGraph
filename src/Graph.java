
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Michal
 */
public class Graph 
{
    private List<Integer> next;
    private List<Integer> head;
    private List<Boolean> discovered;
        
    public Graph()
    {
        next = new ArrayList<>();
        head = new ArrayList<>();
        discovered = new ArrayList<>();
        
        for(int i=0;i<Main.GRAPH_SIZE;++i)
        {
            next.add(-1);
            discovered.add(Boolean.FALSE);
        }
        
    }
    
    public void addEdge(int a, int b)
    {
        if(next.get(a) < 0)
        {
            next.set(a, head.size()+Main.GRAPH_SIZE);
            
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
    
    public List<Boolean> GetDiscoveredList()
    {
        return discovered;
    }
    public void PrintList()
    {
        for(int i=0;i<Main.GRAPH_SIZE;++i)
        {
            System.out.print("["+i+"]:");
            int begin = next.get(i);
            while( begin > -1)
            {
                System.out.print(" -> ["+begin + "]: "+head.get(begin-Main.GRAPH_SIZE));
                begin = next.get(begin);
            }
            System.out.println("");
        }
    }
    
    public void DFS(int v_index)
    {
        Stack stack = new Stack();
        stack.push(v_index);
        while(stack.empty() != true)
        {
            int t = (int) stack.pop();

            //System.out.print(t+" ");
            discovered.set(t, Boolean.TRUE);
            
            int begin = next.get(t);
            while( begin > -1)
            {
                if(!discovered.get(head.get(begin-Main.GRAPH_SIZE)))
                    stack.push(head.get(begin-Main.GRAPH_SIZE));
                begin = next.get(begin);
            }
        }
    }
}

//class Vertex
//{
//    private int id;
//}
//
//class Edge
//{
//    
//}