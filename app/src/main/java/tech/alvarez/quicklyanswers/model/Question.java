package tech.alvarez.quicklyanswers.model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String value;
    private String userCreated;
    private List<String> answers;

    public Question() {
        answers = new ArrayList<>();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
