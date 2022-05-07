package com.example.atyourservice.FlagService.Activiteis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atyourservice.FlagService.Class.Services;
import com.example.atyourservice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserFlagServiceActivity extends AppCompatActivity {

    EditText fullNameField, emailField, nationalNumField,serviceTypeField, servicePriceField, balanceField;
    Button btnReguest;
    //ArrayList<Services> Services;
    Services srv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_flag_service);

        Init();
        GetData();
        Listners();
    }
    private void Init() {
        fullNameField  = findViewById(R.id.fullNameField);
        emailField  = findViewById(R.id.emailField);
        nationalNumField  = findViewById(R.id.nationalNumField);
        serviceTypeField  = findViewById(R.id.serviceTypeField);
        servicePriceField  = findViewById(R.id.servicePriceField);
        balanceField  = findViewById(R.id.balanceField);
        btnReguest  = findViewById(R.id.btnReguest);

        fullNameField.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Full-Name", ""));
        emailField.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Email", ""));
        balanceField.setText(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Ballance", ""));

        //Services = new ArrayList<>();

    }
    private void GetData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ServicePrice").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();
                for(int i=0; i<gr.size(); i++) {
                    if(gr.get(i).getString("الخدمة").equals("خدمة العلم")){
                      srv = new Services(gr.get(i).getString("الخدمة").toString(),gr.get(i).getString("السعر").toString());
                    }
                }
                serviceTypeField.setText(srv.type);
                servicePriceField.setText(srv.price);
            }
        });

    }
    private void Listners() {
        btnReguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}