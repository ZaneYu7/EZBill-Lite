package com.example.my_application.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_application.R;
import com.example.my_application.beam.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private TextView editProfile;

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile = (TextView) findViewById(R.id.edit_profile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });

        //logout function
        logout = (Button) findViewById(R.id.logOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView userNameTextView = (TextView) findViewById(R.id.userName);
        final TextView userNameTextView_0 = (TextView) findViewById(R.id.userName_0); //avatar

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                    String userName = userProfile.userName;
                    String userName_0 = userProfile.userName;
                    String email = userProfile.email;

                    fullNameTextView.setText(fullName);
                    userNameTextView_0.setText(userName_0);
                    userNameTextView.setText(userName);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }
}