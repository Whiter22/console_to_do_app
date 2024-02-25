import java.util.ArrayList;
import java.util.Comparator;

public class Binary_Heap<T> {
    //Max oriented
    private ArrayList<T> arr = null;

    //Constructors
    public Binary_Heap(ArrayList<T> array, Comparator<T> cmp){
        arr = new ArrayList<>();
        for(T element : array)
           add_to_heap(element, cmp);
    }

    public Binary_Heap(T element, Comparator<T> cmp){
        arr = new ArrayList<>();
        add_to_heap(element, cmp);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    //O(n)
    private void bottom_up(int node, int node_parent, Comparator<T> cmp){
        if(node == 0 || cmp.compare(arr.get(node), arr.get(node_parent)) <= 0)
            return;

        T temp = arr.get(node_parent);
        arr.set(node_parent, arr.get(node));
        arr.set(node, temp);

        node = node_parent;
        bottom_up(node, (node-1)/2, cmp);
    }

    //O(n log n)
    private void top_down(int node, int size, Comparator<T> cmp){
        int l_child = 2*node + 1;
        int r_child = 2*node + 2;
//        System.out.println("node: " + arr.get(node) + " l_child: " + arr.get(l_child) + " r_child: " + arr.get(r_child));
        int max_node = node;

        if(l_child < size && cmp.compare(arr.get(l_child), arr.get(max_node)) > 0){
            max_node = l_child;
        }

        if(r_child < size && cmp.compare(arr.get(r_child), arr.get(max_node)) > 0){
            max_node = r_child;
        }

        if(node != max_node){
            T temp = arr.get(node);
            arr.set(node, arr.get(max_node));
            arr.set(max_node, temp);
            top_down(max_node, size, cmp);
        }
    }

    public void add_to_heap(T element, Comparator<T> cmp){
        if(arr.size() == 0){
            arr.add(element);
            return;
        }

        arr.add(element);
        if(cmp.compare(arr.get(arr.size()-1), arr.get((arr.size()-1)/2)) > 0){
            bottom_up(arr.size()-1, (arr.size()-1)/2, cmp);
        }
    }

    public void remove_greatest(Comparator<T> cmp){
        if(arr.size() == 0)
            return;

        arr.set(0, arr.get(arr.size()-1));
        arr.remove(arr.size()-1);
        top_down(0, arr.size(), cmp);
    }

    public void heap_sort(Comparator<T> cmp){
        for(int i = arr.size() - 1; i > 0; i--) {
            T temp = arr.get(0);
            arr.set(0, arr.get(i));
            arr.set(i, temp);
            top_down(0, i, cmp);
        }
    }

    public ArrayList<T> get_heap(){ return arr; }
}
