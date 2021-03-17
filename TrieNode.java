import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrieNode {
    List<Stop> nodeStops = new ArrayList<Stop>();
    HashMap<Character,TrieNode> children = new HashMap<>();

    public TrieNode() { }


}
