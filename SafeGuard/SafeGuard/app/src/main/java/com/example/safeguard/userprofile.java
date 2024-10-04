package com.example.safeguard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class userprofile extends AppCompatActivity {

    private TextView helloUser;
    private EditText address, medical, name, contact;
    private Button saveChanges;
    private ImageView imagesign, profileIcon;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        // Initialize Views
        helloUser = findViewById(R.id.hellouser);
        address = findViewById(R.id.address);
        medical = findViewById(R.id.medical);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        saveChanges = findViewById(R.id.savechanges);
        imagesign = findViewById(R.id.imagesign);

        // Set Username TextView
        if (user != null) {
            String email = user.getEmail();
            String username = email != null ? email.split("@")[0] : "User";
            helloUser.setText(username);
        }

        // Save changes to Firebase Database
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        // Sign out when the sign-out icon is clicked
        imagesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void saveUserInfo() {
        String strAddress = address.getText().toString().trim();
        String strMedical = medical.getText().toString().trim();
        String strName = name.getText().toString().trim();
        String strContact = contact.getText().toString().trim();

        if (strAddress.isEmpty() || strMedical.isEmpty() || strName.isEmpty() || strContact.isEmpty()) {
            Toast.makeText(userprofile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("address", strAddress);
        userInfo.put("medical", strMedical);
        userInfo.put("name", strName);
        userInfo.put("contact", strContact);

        userRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(userprofile.this, "Information saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(userprofile.this, "Failed to save information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signOut() {
        auth.signOut();
        Intent intent = new Intent(userprofile.this, LoginFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
