import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TrieNode {
    ArrayList<Stop> nodeStops = new ArrayList<Stop>();
    HashMap<Character,TrieNode> children = new HashMap<>();

    public void add(char[] word, Stop stop){
        TrieNode node = this;
        for(char c:word) {
            if (!children.containsKey(c)){
                children.put(c, new TrieNode());
            }
            node = children.get(c);

        }
        node.nodeStops.add(stop);
    }

    public ArrayList<Stop> get(char[] word){
        TrieNode node = this;
        for(char c:word){
            if(!children.containsKey(c)){
                return null;
            }
            node = children.get(c);
        }
        return node.nodeStops;
    }

    public ArrayList<Stop> getAll(char[] prefix){
        ArrayList<Stop> results = new ArrayList<Stop>();
        TrieNode node = this;

        for(char c: prefix){
            if(!children.containsKey(c)){
                return null;
            }
            node = children.get(c);
        }
        getAllFrom(node, results);
        return results;
    }

    public void getAllFrom(TrieNode node, ArrayList<Stop> results){
        results.addAll(node.nodeStops);

        for(TrieNode t: children.values()){
            getAllFrom(t,results);
        }
    }

}
