package tech.alvarez.quicklyanswers.home;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import tech.alvarez.quicklyanswers.AnswersActivity;
import tech.alvarez.quicklyanswers.R;

public class InsertCodeFragment extends Fragment {

    private EditText codeEditText;

    public InsertCodeFragment() {
    }

    public static InsertCodeFragment newInstance() {
        InsertCodeFragment fragment = new InsertCodeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_code, container, false);

        codeEditText = (EditText) view.findViewById(R.id.codeEditText);
        codeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    openAnswersScreen(textView.getText().toString());
                    handled = true;
                }
                return handled;
            }
        });

        return view;
    }

    private void openAnswersScreen(String code) {
        Intent intent = new Intent(getActivity(), AnswersActivity.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

}
