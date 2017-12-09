package tech.alvarez.quicklyanswers.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.alvarez.quicklyanswers.R;
import tech.alvarez.quicklyanswers.adapters.QuestionsAdapter;
import tech.alvarez.quicklyanswers.model.Question;


public class MyQuestionsFragment extends Fragment implements QuestionsAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private QuestionsAdapter questionsAdapter;

    public MyQuestionsFragment() {
    }

    public static MyQuestionsFragment newInstance() {
        MyQuestionsFragment fragment = new MyQuestionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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
    public void onQuestionClick(Question q) {

    }
}
