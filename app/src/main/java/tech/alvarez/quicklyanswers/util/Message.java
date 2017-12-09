package tech.alvarez.quicklyanswers.util;

import android.support.design.widget.Snackbar;
import android.view.View;


public class Message {

    public static void show(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

}
