package tech.alvarez.quicklyanswers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;

import tech.alvarez.quicklyanswers.model.Answer;
import tech.alvarez.quicklyanswers.model.Question;

public class AnswersActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private TextView questionTextView;
    private Button answer1Button;
    private Button answer2Button;
    private Button answer3Button;
    private Button answer4Button;

    private FirebaseFirestore db;

    private String questionCode;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        questionTextView = (TextView) findViewById(R.id.questionTextView);
        answer1Button = (Button) findViewById(R.id.answer1Button);
        answer2Button = (Button) findViewById(R.id.answer2Button);
        answer3Button = (Button) findViewById(R.id.answer3Button);
        answer4Button = (Button) findViewById(R.id.answer4Button);
        answer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnswer(answer1Button.getText().toString());
            }
        });
        answer2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnswer(answer2Button.getText().toString());
            }
        });
        answer3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnswer(answer3Button.getText().toString());
            }
        });
        answer4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnswer(answer4Button.getText().toString());
            }
        });

        db = FirebaseFirestore.getInstance();

        if (getIntent().getExtras() != null) {
            String code = getIntent().getStringExtra("code");
            loadData(code);
        }

    }

    private void sendAnswer(String answer) {
        Answer a = new Answer();
        a.setValue(answer);
        a.setDate(new Date());
        a.setQuestionCode(questionCode);
        a.setUser("123");

        startProgress();
        db.collection("answers")
                .add(a)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            goMainScreen();
                        } else {
                            showMessage(task.getException().getMessage());
                        }
                    }
                });
    }

    private void goMainScreen() {
        finish();
    }

    private void loadData(String code) {
        this.questionCode = code;

        startProgress();
        db.collection("questions")
                .document(code)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                        endProgress();
                        question = documentSnapshot.toObject(Question.class);
                        showData();
                    }
                });
    }

    private void startProgress() {
        progressBar.setVisibility(View.VISIBLE);

        answer1Button.setVisibility(View.GONE);
        answer2Button.setVisibility(View.GONE);
        answer3Button.setVisibility(View.GONE);
        answer4Button.setVisibility(View.GONE);
    }

    private void endProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void showData() {
        questionTextView.setText(question.getValue());

        int nroAnswers = question.getAnswers().size();

        if (nroAnswers == 2) {
            answer1Button.setVisibility(View.VISIBLE);
            answer2Button.setVisibility(View.VISIBLE);
            answer3Button.setVisibility(View.GONE);
            answer4Button.setVisibility(View.GONE);
            answer1Button.setText(question.getAnswers().get(0));
            answer2Button.setText(question.getAnswers().get(1));
        } else if (nroAnswers == 3) {
            answer1Button.setVisibility(View.VISIBLE);
            answer2Button.setVisibility(View.VISIBLE);
            answer3Button.setVisibility(View.VISIBLE);
            answer4Button.setVisibility(View.GONE);

            answer1Button.setText(question.getAnswers().get(0));
            answer2Button.setText(question.getAnswers().get(1));
            answer3Button.setText(question.getAnswers().get(2));
        } else if (nroAnswers == 4) {
            answer1Button.setVisibility(View.VISIBLE);
            answer2Button.setVisibility(View.VISIBLE);
            answer3Button.setVisibility(View.VISIBLE);
            answer4Button.setVisibility(View.VISIBLE);

            answer1Button.setText(question.getAnswers().get(0));
            answer2Button.setText(question.getAnswers().get(1));
            answer3Button.setText(question.getAnswers().get(2));
            answer4Button.setText(question.getAnswers().get(3));
        }
    }

    private void showMessage(String message) {
        View rootView = findViewById(R.id.rootView);
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void cancel(View view) {
        finish();
    }
}
