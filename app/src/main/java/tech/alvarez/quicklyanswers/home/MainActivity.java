package tech.alvarez.quicklyanswers.home;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import tech.alvarez.quicklyanswers.CreateQuestionActivity;
import tech.alvarez.quicklyanswers.LogInActivity;
import tech.alvarez.quicklyanswers.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAnalytics firebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        ExtensiblePageIndicator extensiblePageIndicator = (ExtensiblePageIndicator) findViewById(R.id.flexibleIndicator);
        extensiblePageIndicator.initViewPager(viewPager);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        verifyAuthentication();
    }

    private void verifyAuthentication() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openCreateQuestion(View view) {
        Bundle params = new Bundle();
        params.putString("user", "123");
        firebaseAnalytics.logEvent("create_question", params);

        Intent intent = new Intent(this, CreateQuestionActivity.class);
        startActivity(intent);
    }

    private void showMessage(String message) {
        View rootView = findViewById(R.id.rootView);
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }

    public void logOut(View view) {
        firebaseAuth.signOut();

        goLogInScreen();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return InsertCodeFragment.newInstance();
            }
            return MyQuestionsFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
