package lab2_3;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import edu.iis.mto.similarity.SimilarityFinder;

public class SimilarityFinderTests {

    private final double SIMILARITY_EPSILON = 0.00001;

    @Before
    public void cleanEnvironment() {
        SequenceSearcherDouble.searchedKeys.clear();
        SequenceSearcherDouble.valuesToReturn.clear();
        SequenceSearcherDouble.callsCounter = 0;
    }

    public void testResultOfJackadSimilarity(int[] firstSequence, int[] secondSequence, double expectedSimilarity) {
        SimilarityFinder objectUnderTest = new SimilarityFinder(new SequenceSearcherDouble());
        double jackardSimilarity = objectUnderTest.calculateJackardSimilarity(firstSequence, secondSequence);
        double delta = Math.abs(jackardSimilarity - expectedSimilarity);
        boolean result = delta < SIMILARITY_EPSILON;
        assertThat(result, is(equalTo(true)));
    }

    public void testIfAllElementsHaveBeenCheckedWhileCalculatingJackadSimilarity(int[] firstSequence, int[] secondSequence) {
        Vector<Integer> tempVector = new Vector<>();
        for (int i = 0; i < firstSequence.length; i++) {
            SequenceSearcherDouble.valuesToReturn.push(true);
            tempVector.add(firstSequence[i]);
        }
        SimilarityFinder objectUnderTest = new SimilarityFinder(new SequenceSearcherDouble());
        objectUnderTest.calculateJackardSimilarity(firstSequence, secondSequence);
        assertThat(SequenceSearcherDouble.searchedKeys.containsAll(tempVector), is(equalTo(true)));
    }

    @Test
    public void shouldReturnOneAsSimilarityForEmptySequences() {
        int[] seq1 = {};
        int[] seq2 = {};
        testResultOfJackadSimilarity(seq1, seq2, 1);
    }

    @Test
    public void shouldReturnZeroForSequencesWithNoIntersection() {
        int[] seq1 = {1};
        int[] seq2 = {0};
        SequenceSearcherDouble.valuesToReturn.push(false);
        testResultOfJackadSimilarity(seq1, seq2, 0);
    }

    @Test
    public void shouldReturnOneForSequencesWithFullIntersection() {
        int[] seq1 = {1, 2, 3, 4};
        int[] seq2 = {1, 2, 3, 4};
        SequenceSearcherDouble.valuesToReturn.push(true);
        SequenceSearcherDouble.valuesToReturn.push(true);
        SequenceSearcherDouble.valuesToReturn.push(true);
        SequenceSearcherDouble.valuesToReturn.push(true);
        testResultOfJackadSimilarity(seq1, seq2, 1);
    }

    @Test
    public void shouldReturnOneFifth() {
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {3, 4, 5};
        SequenceSearcherDouble.valuesToReturn.push(true);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        testResultOfJackadSimilarity(seq1, seq2, 0.2);
    }

    @Test
    public void shouldReturnZeroWhenOneSequenceIsEmpty() {
        int[] seq1 = {1, 2, 3};
        int[] seq2 = {};
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        testResultOfJackadSimilarity(seq1, seq2, 0);
    }

    @Test
    public void shouldReturnZeroForLongSequencesWithNoIntersection() {
        int[] seq1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] seq2 = {11, 22, 33, 44, 55, 66, 77, 88, 99, 00, 111, 222, 333, 444, 555, 666};
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        SequenceSearcherDouble.valuesToReturn.push(false);
        testResultOfJackadSimilarity(seq1, seq2, 0);
    }

    @Test
    public void shouldHaveCheckedEveryElementInSequenceOfLengthOne() {
        int[] seq1 = {1};
        int[] seq2 = {1, 2, 3};
        testIfAllElementsHaveBeenCheckedWhileCalculatingJackadSimilarity(seq1, seq2);
    }

}
