package com.ikmr.banbara23.yaeyama_liner_checker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikmr.banbara23.yaeyama_liner_checker.databinding.ActivityMainBinding;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Read from the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("weather");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + map.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void onClick(View view) {
        Log.d(TAG, "view.getId():" + view.getId());
        startActivity(new Intent(this, StatusListTabActivity.class));
        switch (view.getId()) {
            case R.id.top_activity_annei:
                break;
            case R.id.top_activity_ykf:
                break;
            case R.id.top_activity_dream:
                break;
        }
    }
}
