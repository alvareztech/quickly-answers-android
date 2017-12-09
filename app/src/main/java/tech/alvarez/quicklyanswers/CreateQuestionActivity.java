package tech.alvarez.quicklyanswers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

import tech.alvarez.quicklyanswers.model.Question;
import tech.alvarez.quicklyanswers.util.RandomString;
import tech.alvarez.quicklyanswers.util.Util;

public class CreateQuestionActivity extends AppCompatActivity {

    private EditText questionEditText;
    private EditText answer1EditText;
    private EditText answer2EditText;
    private EditText answer3EditText;
    private EditText answer4EditText;

    private FloatingActionButton fab;
    private ProgressBar progressBar;

    private RandomString randomString;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        questionEditText = findViewById(R.id.questionEditText);

        answer1EditText = (EditText) findViewById(R.id.answer1EditText);
        answer2EditText = (EditText) findViewById(R.id.answer2EditText);
        answer3EditText = (EditText) findViewById(R.id.answer3EditText);
        answer4EditText = (EditText) findViewById(R.id.answer4EditText);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        randomString = new RandomString(6);

        db = FirebaseFirestore.getInstance();
    }


    public void saveAnswer(View view) {

        String question = questionEditText.getText().toString().trim();
        String answer1 = answer1EditText.getText().toString().trim();
        String answer2 = answer2EditText.getText().toString().trim();
        String answer3 = answer3EditText.getText().toString().trim();
        String answer4 = answer4EditText.getText().toString().trim();

        if (!Util.isQuestionValidate(question)) {
            showMessage("Su pregunta debe tener al menos 8 caracteres");
            return;
        }
        if (!Util.isAnswerValidate(answer1)) {
            showMessage("Su respuesta debe tener al menos 2 caracteres");
            return;
        }
        if (!Util.isAnswerValidate(answer2)) {
            showMessage("Su respuesta debe tener al menos 2 caracteres");
            return;
        }

        Question q = new Question();
        q.setValue(question);
        q.setUserCreated("123456");

        if (Util.isAnswerValidate(answer3) && Util.isAnswerValidate(answer4)) {
            q.setAnswers(Arrays.asList(answer1, answer2, answer3, answer4));
        } else if (Util.isAnswerValidate(answer3)) {
            q.setAnswers(Arrays.asList(answer1, answer2, answer3));
        } else {
            q.setAnswers(Arrays.asList(answer1, answer2));
        }

        String generatedCode = randomString.nextString();

        startProgress();
        db.collection("questions")
                .document(generatedCode)
                .set(q)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        endProgress();
                        if (task.isSuccessful()) {
                            goMainScreen();
                        } else {
                            showMessage(task.getException().getMessage());
                        }
                    }
                });
    }

    private void startProgress() {
        fab.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        questionEditText.setEnabled(false);
        answer1EditText.setEnabled(false);
        answer2EditText.setEnabled(false);
        answer3EditText.setEnabled(false);
        answer4EditText.setEnabled(false);
    }

    private void endProgress() {
        fab.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        questionEditText.setEnabled(true);
        answer1EditText.setEnabled(true);
        answer2EditText.setEnabled(true);
        answer3EditText.setEnabled(true);
        answer4EditText.setEnabled(true);
    }

    private void goMainScreen() {
        finish();
    }

    private void showMessage(String message) {
        View rootView = findViewById(R.id.rootView);
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void cancel(View view) {
        finish();
    }
}
