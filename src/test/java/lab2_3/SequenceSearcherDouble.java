package lab2_3;

import java.util.Stack;
import java.util.Vector;

import edu.iis.mto.search.SearchResult;
import edu.iis.mto.search.SearchResult.Builder;
import edu.iis.mto.search.SequenceSearcher;

public class SequenceSearcherDouble implements SequenceSearcher {

    public static Vector<Integer> searchedKeys = new Vector<>();
    public static Stack<Boolean> valuesToReturn = new Stack<>();
    public static int callsCounter = 0;

    @Override
    public SearchResult search(int key, int[] seq) {
        searchedKeys.add(key);
        callsCounter++;
        Builder builder = SearchResult.builder();
        builder.withFound(valuesToReturn.pop());
        return builder.build();
    }

}
