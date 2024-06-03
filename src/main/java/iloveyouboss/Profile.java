package iloveyouboss;

import java.util.HashMap;
import java.util.Map;

import static iloveyouboss.Weight.REQUIRED;

public class Profile {
    private final Map<String,Answer> answers = new HashMap<>();
    private final String name;
    private int score;

    public Profile(String name) {
        this.name = name;
    }

    public void add(Answer... newAnswers) {
        for (var answer: newAnswers)
            answers.put(answer.questionText(), answer);
    }

    // START:matches
    public boolean matches(Criteria criteria) {
        score = new Matcher(criteria, answers).score();

        // START_HIGHLIGHT
        return isMatchFor(criteria);
        // END_HIGHLIGHT
    }

    // START_HIGHLIGHT
    private boolean isMatchFor(Criteria criteria) {
        // END_HIGHLIGHT
        if (anyRequiredCriteriaNotMet(criteria)) return false;
        return anyMatches(criteria);
    }
    // END:matches

    // START:anyRequiredCriteriaNotMet
    private boolean anyRequiredCriteriaNotMet(Criteria criteria) {
        return criteria.stream()
            .filter(criterion ->
                !criterion.isMatch(profileAnswerMatching(criterion)))
            .anyMatch(criterion -> criterion.weight() == REQUIRED);
    }
    // END:anyRequiredCriteriaNotMet

    // START:anyMatches
    private boolean anyMatches(Criteria criteria) {
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }
    // END:anyMatches

    // TODO duplicated in Matcher
    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
