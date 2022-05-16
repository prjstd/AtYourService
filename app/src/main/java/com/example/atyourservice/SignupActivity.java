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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,Ballance,fullName;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    FirebaseAuth auth;

    ProgressDialog pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        Ballance=(EditText) findViewById(R.id.BallanceField);
        fullName=(EditText) findViewById(R.id.fullNameField);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        auth  = FirebaseAuth.getInstance();
        pb = new ProgressDialog(this);
        pb.setTitle("يرجى الانتظار !");
        pb.setMessage("جاري التحميل...");


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.show();

                 String email = inputEmail.getText().toString().trim();
                 String password = inputPassword.getText().toString().trim();
                 String ballance = Ballance.getText().toString().trim();
                 String fullname= fullName.getText().toString().trim();

                if(email.equals("") || password.equals("") || fullname.equals("") || ballance.equals("")){
                    Toast.makeText(SignupActivity.this, "رجاءا املئ كامل البيانات", Toast.LENGTH_SHORT).show();
                    pb.dismiss();

                }else if(!email.contains(".com") && !email.contains("@") && email.length()<7) {
                    Toast.makeText(SignupActivity.this, "الرجاء ادخال ايميل صحيح", Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                RegUserAcc(email, password, ballance, fullname);
                            }
                            else {
                                task.getException().getMessage();
                                Toast.makeText(SignupActivity.this, " عملية التسجيل لم تكتمل..حاول مرة اخرى : " +task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                pb.dismiss();
                            }
                        }
                    });
                }

            }

            private void RegUserAcc(String email, String password, String ballance, String fullname) {
                Map<String, String> map = new HashMap<>();
                map.put("Uid", auth.getCurrentUser().getUid());
                map.put("Full-Name", fullname);
                map.put("Email", email);
                map.put("Password", password);
                map.put("Ballance",ballance);
                FirebaseFirestore db= FirebaseFirestore.getInstance();
                db.collection("Users")
                        .document(auth.getCurrentUser().getUid())
                        .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            pb.dismiss();
                            Toast.makeText(SignupActivity.this, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                            editor.putString("Uid", auth.getCurrentUser().getUid());
                            editor.putString("Full-Name", fullname);
                            editor.putString("Email", email);
                            editor.putString("Password", password);
                            editor.putString("Ballance", ballance);
                            if(email.contains("@admin") || email.contains("@Admin") || email.contains("@ADMIN") ) {
                                editor.putString("type", "Admin");
                            }else{
                                editor.putString("type", "");
                            }
                            editor.apply();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            SignupActivity.this.finish();
                        }
                    }
                });
            }
        });
    }
}