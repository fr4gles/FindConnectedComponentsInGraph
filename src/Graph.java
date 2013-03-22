import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Klasa reprezentująca graf
 * zawiera reprezentacje tablicy next i head oraz tablicy odwiedzonych wierzchołków
 * zawiera podstawowe, wymagane do poprawnego działania algorytmu, metody oraz logikę
 * @author Michal
 * @date 22.03.2013
 */
public class Graph 
{
    // UWAGA!! ... zgodnie z logiką algorytmu
    // tablicę head należałoby indeksować od n+1 ...
    // ale na potrzeby programu (uwzględniając sposób zapisu i indeksowania tablic w javie) wartość n+1 odpowiada 0 elementowi w tablicy head
    // zmieniona logika działania nie wpływa na poprawność algorytmu
    private List<Integer> next;         // tablica, lista indeksów odwołujących się do sąsiadów wierzołka o zadanym indeksie (jeśli indeks < ILOŚC WIERZCHOLKÓw) -> zgodnie z idea zadania
    private List<Vertex> head;          // tablica, lista wierzołków, której indeks odpowiada sąsiadowi wierzołka.
    private List<Boolean> discovered;   // tablica odwiedzonych wierzchołków, FALSE - nie odwiedzony, TRUE - odwiedzony ...
                                        // ... wielkość tej tablicy odpowiada ilośći wierzchołków, a indeks odpowiada indeksowi wierzchołka,
        
    public Graph()
    {
        // inicjalizacja tablic
        next = new ArrayList<>();
        head = new ArrayList<>();
        discovered = new ArrayList<>();
        
        //inicjalizacja tablicy next oraz discovered
        for(int i=0;i<Main.GRAPH_SIZE;++i)
        {
            next.add(-1);                       // wypełnienie tablicy next wartościami "pustymi", jesli next ma -1 to nie ma polaczenia ...
            discovered.add(Boolean.FALSE);      // ustawienie wszystkich wierzcholkow na NIE odwiedzone
        } 
    }
    
    public void addEdge(Edge newEdge)
    {
        Integer a = newEdge.GetStartVertex().Get();     // pobranie indesku początkowego wierzchołka krawędzi
        Integer b = newEdge.GetEndVertex().Get();       // ... ^ końowego wierzchołka krawędzi
        
        if(next.get(a) < 0)                             // "krawędź nie jest wpisana" do tablicy next, to aktualizuj ją ...
        {
            next.set( a, head.size()+Main.GRAPH_SIZE);  // ... wstaw do tablicy next w indeksie a, indeks pierwszego wolnego elementu w tablicy head
            head.add( new Vertex(b) );                  // dodaj do head nowy wierzchołek
            next.add( -1 );                             // dodaj no next pusty element
        }
        else                                            // w przeciwnym przypadku ...
        {
            head.add( new Vertex(b) );                  // dodaj nowy wierzchołek do tablicy head ...
            next.add( -1 );                             // ... a next, tym samym ustaw na wartość "pustą" : -1
            
            int begin = next.get(a);                    // pobierz indeks "połączenia" --> zgodnie z idea, patrz Efficient Algorithm for Graph Manipulation - John Hopcroft, Robert Tarjan
            while(true)                                 // kontynuj aż do przerwania ...
            {
                if( (next.get(begin)) > -1)             // jeśli wartosc w tablicy next pod indeksem "połączenia" nie jest "pusty" to ...
                    begin = next.get(begin);            // ... ustaw indeks na nową wartość
                else                                    // jesli jednak jest pusto to ...
                    break;                              // przerwij
            }
            
            next.set(begin, head.size()+Main.GRAPH_SIZE-1); // ustaw wartosc tablicy next na wartość indeksu "połączenia", otrzymanego powyżej ^
        }
    }
    
    /**
     * Pobranie tablicy next
     * @return tablica next
     */
    public List<Integer> getNextList()
    {
        return next;
    }
    
    /**
     * Pobranie tablicy head
     * @return tablica head
     */
    public List<Vertex> getHeadList()
    {
        return head;
    }
    
    /**
     * pobranie listy odwiedzonych
     * @return tablica discovered
     */
    public List<Boolean> GetDiscoveredList()
    {
        return discovered;
    }
    
    /**
     * Testowy wypis tablic ...
     */
    public void PrintList()
    {
        for(int i=0;i<Main.GRAPH_SIZE;++i)
        {
            System.out.print("["+i+"]:");
            int begin = next.get(i);
            while( begin > -1)
            {
                System.out.print(" -> ["+begin + "]: "+head.get(begin-Main.GRAPH_SIZE).Get());
                begin = next.get(begin);
            }
            System.out.println("");
        }
    }
    
    
    /**
     * Przeszukiwanie w głąb (ang. Depth-first search, w skrócie DFS) 
     * – jeden z algorytmów przeszukiwania grafu. 
     * Przeszukiwanie w głąb polega na badaniu wszystkich krawędzi wychodzących z podanego wierzchołka. Po zbadaniu wszystkich krawędzi wychodzących z danego wierzchołka algorytm powraca do wierzchołka, z którego dany wierzchołek został odwiedzony.
     * @param v_index indeks wierzchołka do odwiedzenia, od którego zaczynamy DFS
     */
    public void DFS(int v_index)
    {
        Stack<Integer> stack = new Stack<>();   // tworzony jest stos przechowyjący indeksy wierzchołka ...
        stack.push(v_index);                    // na początku jest dodawany wierzchołek dla którego wykonujemy DFS
        
        while(stack.empty() != true)            // jeśli stos nie jest pusty, to ...
        {
            int t = (int) stack.pop();          // weź indeks ze stosu

            if(Main.TEST)
                System.out.print(t+" ");
    
            discovered.set(t, Boolean.TRUE);    // oznacz wierzchołek o indeksie pobranym ze stosu jako odwiedzony

            int begin = next.get(t);            // pobierz indeks "połączenia" --> zgodnie z idea, patrz Efficient Algorithm for Graph Manipulation - John Hopcroft, Robert Tarjan
            while( begin > -1)                  // -1 oznacza ze wierzchołek nie ma połączenia, zatem jeśli jest różny, kontynuj ...
            {
                if(!discovered.get(                                     // jeśli nie odwiedziłem wierzchołka o ...
                        head.get(begin-Main.GRAPH_SIZE).Get()           // ... indeksie zawartym w tablicy head ...
                        ))  
                    stack.push(head.get(begin-Main.GRAPH_SIZE).Get());  // ... to dodaj go do stosu ...
                begin = next.get(begin);                                // ustaw nowy indeks połączenia z tablicy next
            }
        }
    }
}

/**
 * Klasa reprezentująca wierzcholek
 * @author Michal
 * @date 22.03.2013
 */
final class Vertex
{
    private Integer index;          // indeks wierzcholka

    public Vertex(Integer tmp)      // przy stworzeniu obiektu ...
    {
        Set(tmp);
    }

    public void Set(Integer tmp)    // ... ustaw wartosc indeksu
    {
        index = tmp;
    }

    public Integer Get()            // pobierz indeks
    {
        return index;
    }
}

/**
 * Klasa reprezentująca krawędź
 * @author Michal
 */
final class Edge
{
    private Vertex  start,          // wierzchołek startowy
                    end;            // wierzchołek końcowy

    public Edge(Vertex s, Vertex e) // konstruktor ... 
    {
        start = s;
        end = e;
    }

    public void Set(Vertex s, Vertex e) // ... ustaw zadane wartosci
    {
        start = s;
        end = e;
    }

    public Vertex GetStartVertex()      // pobierz poczatkowy wierzcholek krawedzi
    {
        return start;
    }

    public Vertex GetEndVertex()        // pobierz koncowy wierzcholek krawedzi
    {
        return end;
    }
}