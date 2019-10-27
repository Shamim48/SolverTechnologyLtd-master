package com.Solver.Solver.ModelClass;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Solver extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
