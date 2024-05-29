package iloveyouboss;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static iloveyouboss.Weight.REQUIRED;
import static java.util.Arrays.asList;

// START:class
public record Matcher(Criteria criteria, Map<String, Answer> answers) {
    public Matcher(Criteria criteria, List<Answer> matcherAnswers) {
        this(criteria, asMap(matcherAnswers));
    }

    public Matcher(Criteria criteria, Answer... matcherAnswers) {
        this(criteria, asList(matcherAnswers));
    }

    private static Map<String, Answer> asMap(List<Answer> answers) {
        return answers.stream().collect(
           Collectors.toMap(Answer::questionText, answer -> answer));
    }

    public boolean matches() {
        return allRequiredCriteriaMet() && anyMatches();
    }

    private boolean allRequiredCriteriaMet() {
        return criteria.stream()
            .filter(criterion -> criterion.weight() == REQUIRED)
            .allMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    private boolean anyMatches() {
        return criteria.stream()
            .anyMatch(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)));
    }

    private Answer profileAnswerMatching(Criterion criterion) {
        return answers.get(criterion.questionText());
    }

    public int score() {
        return criteria.stream()
            .filter(criterion ->
                criterion.isMatch(profileAnswerMatching(criterion)))
            .mapToInt(criterion -> criterion.weight().value())
            .sum();
    }
}
// END:class
