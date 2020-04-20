package tech.alvarez.quicklyanswers.home;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import tech.alvarez.quicklyanswers.R;
import tech.alvarez.quicklyanswers.ResultsActivity;
import tech.alvarez.quicklyanswers.adapters.QuestionsAdapter;
import tech.alvarez.quicklyanswers.model.Question;


public class MyQuestionsFragment extends Fragment implements QuestionsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private QuestionsAdapter questionsAdapter;

    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;

    public MyQuestionsFragment() {
    }

    public static MyQuestionsFragment newInstance() {
        MyQuestionsFragment fragment = new MyQuestionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_questions, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        questionsAdapter = new QuestionsAdapter(this);
        recyclerView.setAdapter(questionsAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        db.collection("questions")
                .whereEqualTo("userCreated", firebaseAuth.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        questionsAdapter.clear();
                        for (DocumentSnapshot document : documentSnapshots) {
                            Question q = document.toObject(Question.class);
                            q.setId(document.getId());
                            questionsAdapter.add(q);
                        }
                    }
                });
    }

    @Override
    public void onQuestionClick(Question q) {
        Intent intent = new Intent(getActivity(), ResultsActivity.class);
        intent.putExtra("questionCode", q.getId());
        startActivity(intent);
    }
}
