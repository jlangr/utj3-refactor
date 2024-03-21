package iloveyouboss;

// START:class
import java.util.HashMap;
import java.util.Map;

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
        var matcher = new Matcher(criteria, answers);
        score = matcher.score();
        return matcher.matches();
    }
    // END:matches

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
// END:class
