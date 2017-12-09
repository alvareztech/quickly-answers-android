package tech.alvarez.quicklyanswers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import tech.alvarez.quicklyanswers.adapters.AnswersAdapter;
import tech.alvarez.quicklyanswers.model.Answer;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnswersAdapter answersAdapter;

    private String questionCode;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        answersAdapter = new AnswersAdapter();
        recyclerView.setAdapter(answersAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().getExtras() != null) {
            questionCode = getIntent().getStringExtra("questionCode");
        }

        db = FirebaseFirestore.getInstance();

        loadData();
    }

    private void loadData() {
        db.collection("answers")
                .whereEqualTo("questionCode", questionCode)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        answersAdapter.clear();
                        for (DocumentSnapshot document : documentSnapshots) {
                            Answer a = document.toObject(Answer.class);
                            answersAdapter.add(a);
                        }
                    }
                });
    }

    public void cancel(View view) {
        finish();
    }
}
