package com.example.atyourservice.UserServices.Activiteis;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.atyourservice.Home.Activities.HomeActivity;
import com.example.atyourservice.R;
import com.example.atyourservice.UserServices.Class.Services;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class UserIdentityerviceActivity extends AppCompatActivity {
    EditText fullNameField, emailField, nationalNumField,serviceTypeField, servicePriceField, balanceField;
    Button btnReguest;
    Services srv;
    ProgressDialog pb;
    String msg="";
    Double blnc=0.0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserIdentityerviceActivity.this, HomeActivity.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_identityervice);

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
        blnc=Double.parseDouble(balanceField.getText().toString());

//        if(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("service","").contains("F")){
//            btnReguest.setEnabled(false);
//        }
    }
    private void GetData(){

        try{
            pb.show();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("ServicePrice")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    pb.dismiss();

                    List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();

                    for(int i=0; i<gr.size(); i++) {
                        if(gr.get(i).getString("id").equals("identityService")){
                            srv = new Services(gr.get(i).getString("name").toString(),gr.get(i).getString("price").toString());
                        }
                    }
                    serviceTypeField.setText(srv.type);
                    servicePriceField.setText(srv.price);
                }
            });
        }catch(Exception ex){}

    }

    private void Listners() {
        btnReguest.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                try{
                    pb.show();

                    String Payment_Status = "";
                    if(Double.parseDouble(balanceField.getText().toString()) < Double.parseDouble(servicePriceField.getText().toString())){
                        Payment_Status = "0";
                        msg="رصيدك الحالي لا يكفي لهذه الخدمة...سيتم تقديم الطلب حاليا ولكن ارجو تعبئة الرصيد باسرع وقت ممكن حتى لا يبقى طلبك معلقا";
                    }else {
                        Payment_Status = "1";
                        blnc = Double.parseDouble(balanceField.getText().toString()) - Double.parseDouble(servicePriceField.getText().toString());
                        Map<String, Object> user = new HashMap<>();
                        user.put("Ballance", String.valueOf(blnc));

                        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
                        db1.collection("Users").document(getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Uid", ""))
                                .update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
                                    editor.putString("Ballance", String.valueOf(blnc));
                                    editor.apply();
                                }
                            }
                        });
                    }

                    if(!nationalNumField.getText().equals("") && nationalNumField.length()<10){
                        pb.dismiss();
                        Toast.makeText(UserIdentityerviceActivity.this, "الرقم الوطني يتكون من 10 خانات ", Toast.LENGTH_SHORT).show();
                    }else{
                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        DocumentReference df = db.collection("PendingService").document();
                        String id =df.getId();

                        Map<String, String> map = new HashMap<>();
                        map.put("id", id);
                        map.put("Service_Name", serviceTypeField.getText().toString());
                        map.put("User_id", getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Uid", ""));
                        map.put("Full_Name", getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Full-Name", ""));
                        map.put("Nationality_Number", nationalNumField.getText().toString());
                        map.put("Request_Date", date);
                        map.put("Replay_Date", "");
                        map.put("Service_Status", "0");
                        map.put("Payment_Status", Payment_Status);
                        map.put("Service_Price", servicePriceField.getText().toString());

                        df.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    pb.dismiss();
                                    btnReguest.setEnabled(false);

//                            SharedPreferences.Editor editor = getSharedPreferences("UserInfo", MODE_PRIVATE).edit();
//                            editor.putString("service", "F");
//                            editor.apply();

                                    final AlertDialog.Builder builder = new AlertDialog.Builder((UserIdentityerviceActivity.this));
                                    LayoutInflater inflater = (UserIdentityerviceActivity.this).getLayoutInflater();
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
                                    TextView txtBody = dialog2.findViewById(R.id.txtBody);
                                    txtBody.setText(txtBody.getText()+"\n"+ msg);
                                    Button btnOk = dialog2.findViewById(R.id.btnOk);
                                    btnOk.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(UserIdentityerviceActivity.this,HomeActivity.class));

                                        }
                                    });
                                }

                            }
                        });
                    }


                }catch (Exception ex){}


            }
        });
    }
}