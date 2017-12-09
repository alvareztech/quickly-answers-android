package tech.alvarez.quicklyanswers.util;

public class Util {

    public static final int MIN_LENGTH_QUESTION = 8;
    public static final int MIN_LENGTH_ANSWER = 2;

    public static boolean isQuestionValidate(String question) {
        return question.length() >= MIN_LENGTH_QUESTION;
    }

    public static boolean isAnswerValidate(String answer) {
        return answer.length() >= MIN_LENGTH_ANSWER;
    }
}
