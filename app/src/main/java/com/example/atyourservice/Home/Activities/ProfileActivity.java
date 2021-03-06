package com.example.atyourservice.Home.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atyourservice.LoginActivity;
import com.example.atyourservice.R;
import com.example.atyourservice.Reset.Activities.ResetPasswordActivity;
import com.example.atyourservice.UserServices.Activiteis.UserFlagServiceActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    TextView name_txt, email_txt, balance_txt;
    EditText name_ed, email_ed, balance_ed;
    CircleImageView edit, save;
    RelativeLayout saveRel, editRel;
    Button btn_resPass, btn_logout;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Init();
        Listners();

    }

    private void Init() {
        name_ed = findViewById(R.id.name_ed);
        name_txt = findViewById(R.id.name_txt);

        email_txt = findViewById(R.id.email_txt);
        email_ed = findViewById(R.id.email_ed);

        balance_txt = findViewById(R.id.balance_txt);
        balance_ed = findViewById(R.id.balance_ed);

        name_txt.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Full-Name", ""));
        email_txt.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Email", ""));
        balance_txt.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Ballance", ""));

        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);

        saveRel = findViewById(R.id.saveRel);
        editRel = findViewById(R.id.editRel);

        btn_resPass = findViewById(R.id.btn_resPass);
        btn_logout = findViewById(R.id.logout);
    }

    private void Listners() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    editRel.setVisibility(View.GONE);
                    saveRel.setVisibility(View.VISIBLE);

                    name_txt.setVisibility(View.GONE);
                    name_ed.setVisibility(View.VISIBLE);
                    name_ed.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Full-Name", ""));

//                    email_txt.setVisibility(View.GONE);
//                    email_ed.setVisibility(View.VISIBLE);
//                    email_ed.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Email", ""));

                    balance_txt.setVisibility(View.GONE);
                    balance_ed.setVisibility(View.VISIBLE);
                    balance_ed.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Ballance", ""));


                } catch (Exception ex) {
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Map<String, Object> user = new HashMap<>();
                    user.put("Full-Name", name_ed.getText().toString());
                   // user.put("Email", email_ed.getText().toString());
                    user.put("Ballance", balance_ed.getText().toString());

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users").document(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Uid", ""))
                            .update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "???? ?????????? ?????????????? ??????????", Toast.LENGTH_SHORT).show();
                                editRel.setVisibility(View.VISIBLE);
                                saveRel.setVisibility(View.GONE);
                                name_txt.setVisibility(View.VISIBLE);
                                name_ed.setVisibility(View.GONE);
                                name_txt.setText(name_ed.getText().toString());
//                                email_txt.setVisibility(View.VISIBLE);
//                                email_ed.setVisibility(View.GONE);
//                                email_txt.setText(email_ed.getText().toString());
                                balance_txt.setVisibility(View.VISIBLE);
                                balance_ed.setVisibility(View.GONE);
                                balance_txt.setText(balance_ed.getText().toString());
                                SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                                editor.putString("Full-Name", name_ed.getText().toString());
//                                editor.putString("Email", email_ed.getText().toString());
                                editor.putString("Ballance", balance_ed.getText().toString());
                                editor.apply();
                            }
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileActivity.this, "?????? ?????? ??????...???????? ?????? ????????", Toast.LENGTH_SHORT).show();
                                    editRel.setVisibility(View.VISIBLE);
                                    saveRel.setVisibility(View.GONE);
                                }
                            });

                }catch(Exception ex){}
            }
        });
        btn_resPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, ResetPasswordActivity.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                Toast.makeText(ProfileActivity.this, "???? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}