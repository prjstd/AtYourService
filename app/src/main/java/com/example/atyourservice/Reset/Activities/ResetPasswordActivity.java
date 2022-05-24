package com.example.atyourservice.Reset.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atyourservice.Home.Activities.ProfileActivity;
import com.example.atyourservice.LoginActivity;
import com.example.atyourservice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText NewPass, ConfirmPass, OldPass;
    Button changeBtn;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        startActivity(new Intent(ResetPasswordActivity.this, ProfileActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Init();
        Listners();
    }
    private void Init(){

        OldPass = findViewById(R.id.OldPass);
        NewPass = findViewById(R.id.NewPass);
        ConfirmPass = findViewById(R.id.ConfirmPass);
        changeBtn = findViewById(R.id.changeBtn);
    }
    private void Listners(){

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(OldPass.getText().toString().equals(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Password",""))){
                        if(NewPass.getText().toString().equals(ConfirmPass.getText().toString())){
                            Map<String, Object> user = new HashMap<>();
                            user.put("Password",NewPass.getText().toString());
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Users").document(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Uid",""))
                                    .update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    try{
                                        if(task.isSuccessful()){
                                            String NEW_PASS = NewPass.getText().toString();
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            AuthCredential credential = EmailAuthProvider
                                                    .getCredential(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Email", ""),
                                                            getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Password", ""));

                                            user.reauthenticate(credential)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                            user.updatePassword(NEW_PASS)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {

                                                                                if(task.isSuccessful()){
                                                                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                                                                    auth.signOut();

                                                                                    SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                                                                                    editor.putString("Uid", "");
                                                                                    editor.putString("Full-Name", "");
                                                                                    editor.putString("Email", "");
                                                                                    editor.putString("Password", "");
                                                                                    editor.putString("Ballance", "");
                                                                                    editor.putString("type", "");

                                                                                    editor.apply();

                                                                                    Toast.makeText(ResetPasswordActivity.this, "تم تغيير كلمة السر, الرجاء اعادة الدخول لتفعيل كلمة السر الجديدة", Toast.LENGTH_LONG).show();
                                                                                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                                                                    finish();
                                                                                }
                                                                                else
                                                                                    Toast.makeText(ResetPasswordActivity.this, "لقد حدث خطأ, حاول لاحقا من فضلك.", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    });

                                        }
                                    }catch(Exception ex){}

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ResetPasswordActivity.this, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }else{
                            Toast.makeText(ResetPasswordActivity.this, "كلمتي السر غير متطابقات", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ResetPasswordActivity.this, "كلمة السر الاصلية غير صحيحة", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){}
            }
        });
    }
}