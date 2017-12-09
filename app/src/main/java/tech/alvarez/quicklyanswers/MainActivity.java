package tech.alvarez.quicklyanswers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText codeEditText;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeEditText = (EditText) findViewById(R.id.codeEditText);
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

        firebaseAuth = FirebaseAuth.getInstance();

        // AdMob
        MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");

//        verifyAuthentication();
    }

    private void openAnswersScreen(String code) {
        Intent intent = new Intent(this, AnswersActivity.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    private void verifyAuthentication() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {

        } else {
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    public void openCreateQuestion(View view) {
        Intent intent = new Intent(this, CreateQuestionActivity.class);
        startActivity(intent);
    }

    private void showMessage(String message) {
        View rootView = findViewById(R.id.rootView);
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}