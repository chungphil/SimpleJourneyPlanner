import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TrieNode {
    List<Stop> nodeStops = new ArrayList<Stop>();
    HashMap<Character,TrieNode> children = new HashMap<>();

    public TrieNode() {

    }

//    public void add(String word, Stop stop){
//        TrieNode node = this;
//        char[] charWord = word.toCharArray();
//
//        for(char c:charWord) {
//            if (!children.containsKey(c)){
//                children.put(c, new TrieNode());
//            }
//            node = children.get(c);
//
//        }
//        node.nodeStops.add(stop);
//    }
//
//    public ArrayList<Stop> get(String word){
//        TrieNode node = this;
//        char[] charWord = word.toCharArray();
//
//        for(char c:charWord){
//            if(!children.containsKey(c)){
//                return null;
//            }
//            node = children.get(c);
//        }
//        return node.nodeStops;
//    }
//
//    public ArrayList<Stop> getAll(String prefix){
//        ArrayList<Stop> results = new ArrayList<Stop>();
//        TrieNode node = this;
//        char[] charPrefix = prefix.toCharArray();
//
//        for(char c: charPrefix){
//            if(!children.containsKey(c)){
//                return null;
//            }
//            node = children.get(c);
//        }
//        getAllFrom(node, results);
//        return results;
//    }
//
//    public void getAllFrom(TrieNode node, ArrayList<Stop> results){
//        results.addAll(node.nodeStops);
//
//        for(TrieNode t: children.values()){
//            getAllFrom(t,results);
//        }
//    }

}
