package iloveyouboss;

import java.util.Map;
import static iloveyouboss.Weight.REQUIRED;

// START:class
public class Matcher {
    // START_HIGHLIGHT
    private final Criteria criteria;
    // END_HIGHLIGHT
    private final Map<String, Answer> answers;
    private int score;

    public Matcher(Criteria criteria, Map<String, Answer> answers) {
        this.criteria = criteria;
        // START_HIGHLIGHT
        this.answers = answers;
        calculateScore();
        // END_HIGHLIGHT
    }

    // START_HIGHLIGHT
    private void calculateScore() {
        // END_HIGHLIGHT
        score = criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }

    // START:matches
    public boolean matches() {
        // START_HIGHLIGHT
        if (anyRequiredCriteriaNotMet()) return false;
            // END_HIGHLIGHT
        // START_HIGHLIGHT
        return anyMatches();
        // END_HIGHLIGHT
    }
    // END:matches

    // START_HIGHLIGHT
    private boolean anyMatches() {
        // END_HIGHLIGHT
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    // START_HIGHLIGHT
    private boolean anyRequiredCriteriaNotMet() {
        // END_HIGHLIGHT
        return criteria.stream()
            .filter(criterion ->
                !criterion.isMatch(profileAnswerMatching(criterion)))
            .anyMatch(criterion -> criterion.weight() == REQUIRED);
    }

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return score;
    }
}
// END:class
