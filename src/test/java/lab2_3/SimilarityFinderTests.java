package lab2_3;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

}
