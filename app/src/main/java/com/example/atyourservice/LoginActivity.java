package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atyourservice.Home.Activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;

    CircleImageView img;
    private Button btnSignup, btnLogin, btnReset;
    ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish(); }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        pb = new ProgressDialog(this);
        pb.setMessage("يرجى الانتظار...");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.show();
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "ادخل الايميل", Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                    return;
                }

                if ( password.equals("")) {
                    Toast.makeText(getApplicationContext(), "ادخل كلمة السر", Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("Users")
                                            .document(auth.getCurrentUser().getUid())
                                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                                            editor.putString("Uid", documentSnapshot.getString("Uid"));
                                            editor.putString("Full-Name", documentSnapshot.getString("Full-Name"));
                                            editor.putString("Email", documentSnapshot.getString("Email"));
                                            editor.putString("Password", documentSnapshot.getString("Password"));
                                            editor.putString("Ballance", documentSnapshot.getString("Ballance"));
                                            if(email.contains("@admin") || email.contains("@Admin")) {
                                                editor.putString("type", "Admin");
                                            }else{
                                                editor.putString("type", "");
                                            }
                                            editor.apply();

                                            pb.dismiss();
                                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                            finish();

                                        }
                                    });



                                } else {

                                    if (password.length() < 6) {
                                        pb.dismiss();
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        pb.dismiss();
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}