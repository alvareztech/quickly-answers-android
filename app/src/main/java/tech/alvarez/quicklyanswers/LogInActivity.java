package tech.alvarez.quicklyanswers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import tech.alvarez.quicklyanswers.home.MainActivity;
import tech.alvarez.quicklyanswers.util.Message;

public class LogInActivity extends AppCompatActivity {

    private View rootView;
    private TextView phoneTextView;
    private EditText phoneEditText;
    private TextView codeTextView;
    private EditText codeEditText;

    private FirebaseAuth firebaseAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        rootView = findViewById(R.id.rootView);
        phoneTextView = (TextView) findViewById(R.id.phoneTextView);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        phoneEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMe();
                    handled = true;
                }
                return handled;
            }
        });
        codeTextView = (TextView) findViewById(R.id.codeTextView);
        codeEditText = (EditText) findViewById(R.id.codeEditText);
        codeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String code = codeEditText.getText().toString();
                    verify(code);
                    handled = true;
                }
                return handled;
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

    }

    private void verify(String code) {
        hideKeyboard();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void sendMe() {
        hideKeyboard();
        sendVerificationCode();
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void sendVerificationCode() {
        String phone = phoneEditText.getText().toString().trim();

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+591" + phone, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Message.show("Invalid request", rootView);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Message.show("The SMS quota for the project has been exceeded", rootView);
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId = verificationId;
                Message.show("Código enviado", rootView);
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goMainScreen();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Message.show("Datos incorrectos para autenticarse.", rootView);
                            } else {
                                Message.show("Error al autenticarse.", rootView);
                            }
                        }
                    }
                });

    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showStep1() {
        phoneTextView.setVisibility(View.VISIBLE);
        phoneEditText.setVisibility(View.VISIBLE);
        codeTextView.setVisibility(View.GONE);
        codeEditText.setVisibility(View.GONE);
    }

    private void showStep2() {
        phoneTextView.setVisibility(View.GONE);
        phoneEditText.setVisibility(View.GONE);
        codeTextView.setVisibility(View.VISIBLE);
        codeEditText.setVisibility(View.VISIBLE);
    }


}
