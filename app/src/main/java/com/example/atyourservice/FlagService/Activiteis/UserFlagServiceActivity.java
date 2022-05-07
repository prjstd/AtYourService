package com.example.atyourservice.FlagService.Activiteis;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.atyourservice.FlagService.Class.Services;
import com.example.atyourservice.Home.Activities.HomeActivity;
import com.example.atyourservice.R;
import com.example.atyourservice.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserFlagServiceActivity extends AppCompatActivity {

    EditText fullNameField, emailField, nationalNumField,serviceTypeField, servicePriceField, balanceField;
    Button btnReguest;
    //ArrayList<Services> Services;
    Services srv;
    ProgressDialog pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_flag_service);

        Init();
        GetData();
        Listners();
    }
    private void Init() {
        pb = new ProgressDialog(this);
        pb.setTitle("يرجى الانتظار !");
        pb.setMessage("جاري التحميل...");
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

        pb.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ServicePrice")
//                .whereEqualTo("user_id", "asd")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pb.dismiss();

                List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();
//                ArrayList<Users>test=new ArrayList<>();

                for(int i=0; i<gr.size(); i++) {
//                    test.add(gr.get(0).toObject(Users.class));
                    if(gr.get(i).getString("id").equals("NCRC")){
                      srv = new Services(gr.get(i).getString("name").toString(),gr.get(i).getString("price").toString());
                    }
                }
                serviceTypeField.setText(srv.type);
                servicePriceField.setText(srv.price);
            }
        });
    }
    private void Listners() {
        btnReguest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                pb.show();
                if(nationalNumField.getText().equals("")){
                    Toast.makeText(UserFlagServiceActivity.this, " ادخل الرقم الوطني من فضلك", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(balanceField.getText().toString()) < Double.parseDouble(servicePriceField.getText().toString())){
                    Toast.makeText(UserFlagServiceActivity.this, "رصيدك الحالي لا يكفي لهذه الخدمة...سيتم تقديم الطلب حاليا ولكن ارجو تعبئة الرصيد باسرع وقت ممكن حتى لا يبقى طلبك معلقا", Toast.LENGTH_SHORT).show();
                }

                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                Map<String, String> map = new HashMap<>();
                map.put("Service_Name", serviceTypeField.getText().toString());
                map.put("User_id", getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Uid", ""));
                map.put("Nationality_Number", nationalNumField.getText().toString());
                map.put("Request_Date", date);
                map.put("Replay_Date", "");
                map.put("Service_Status", "0");
                map.put("Payment_Status", "0");
                map.put("Service_Price", servicePriceField.getText().toString());

                FirebaseFirestore.getInstance().collection("PendingService").document().set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            pb.dismiss();

                            final AlertDialog.Builder builder = new AlertDialog.Builder((UserFlagServiceActivity.this));
                            LayoutInflater inflater = (UserFlagServiceActivity.this).getLayoutInflater();
                            builder.setView(inflater.inflate(R.layout.seccess_req_dialog, null));
                            final AlertDialog dialog2 = builder.create();
                            ((FrameLayout) dialog2.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(Color.TRANSPARENT));
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog2.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                            dialog2.show();
                            dialog2.getWindow().setAttributes(lp);
                            dialog2.setCanceledOnTouchOutside(true);

                            Button btnOk = dialog2.findViewById(R.id.btnOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(UserFlagServiceActivity.this,HomeActivity.class));
                                }
                            });
                        }

                    }
                });
            }
        });
    }
}