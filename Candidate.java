import java.util.*;

public class Candidate {
    private String word;
    private int confidence;

    Candidate(String wor) {
	word = wor;
	confidence = 1;
    }

    public String getWord() {
	return word;
    }
    public int getConfidence() {
	return confidence;
    }
    public void incrementConfidence() {
	confidence++;
    }
}
