import java.util.ArrayList;
import java.util.List;

public class Trie {
    TrieNode root;

    public Trie(){
    root = new TrieNode();
    }

    public void add(String word, Stop stop){
        TrieNode node = root;
        char[] charWord = word.toCharArray();

        for(char c:charWord) {
            if (!node.children.containsKey(c)){
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);

        }
        node.nodeStops.add(stop);
    }

    public List<Stop> get(String word){
        TrieNode node = root;
        char[] charWord = word.toCharArray();

        for(char c:charWord){
            if(!node.children.containsKey(c)){
                return null;
            }
            node = node.children.get(c);
        }
        return node.nodeStops;
    }

    public List<Stop> getAll(String prefix){
        List<Stop> results = new ArrayList<Stop>();
        TrieNode node = root;
        char[] charPrefix = prefix.toCharArray();

        for(char c: charPrefix){
            if(!node.children.containsKey(c)){
                return null;
            }
            node = node.children.get(c);
        }
        getAllFrom(node, results);
        return results;
    }

    public void getAllFrom(TrieNode node, List<Stop> results){
        results.addAll(node.nodeStops);

        for(TrieNode t: node.children.values()){
            getAllFrom(t,results);
        }
    }
}
