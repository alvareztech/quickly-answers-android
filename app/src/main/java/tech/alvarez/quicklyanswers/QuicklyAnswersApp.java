package tech.alvarez.quicklyanswers;


import android.app.Application;
import android.content.Context;

public class QuicklyAnswersApp extends Application {

    private static QuicklyAnswersApp INSTANCE;

    public static QuicklyAnswersApp getInstance() {
        return INSTANCE;
    }

    public static Context getContext() {
        return INSTANCE.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }
}
