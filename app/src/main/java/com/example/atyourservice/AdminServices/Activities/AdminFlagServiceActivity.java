package com.example.atyourservice.AdminServices.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.atyourservice.AdminServices.Adapter.AdminMyAdapter;
import com.example.atyourservice.UserServices.Class.PendingServices;
import com.example.atyourservice.Home.Adapter.MyAdapter;
import com.example.atyourservice.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminFlagServiceActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<PendingServices> mRequestsList;
    ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_flag_service);

        Init();
        GetData();
    }

    private void Init() {
        pb = new ProgressDialog(this);
        pb.setTitle("يرجى الانتظار !");
        pb.setMessage("جاري التحميل...");
        mRequestsList  =new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerview);
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void GetData() {
        try{

        mRequestsList.clear();
        pb.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PendingService")
                .whereEqualTo("Service_Name", "خدمة علم")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pb.dismiss();

                List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();

                for(int i=0; i<gr.size(); i++) {
                    if(gr.get(i).getString("Service_Status").equals("0")){
                        mRequestsList.add(gr.get(i).toObject(PendingServices.class));//الطلبات يلي قيد الاجراء
                    }
                }
                AdminMyAdapter adminMyAdapter = new AdminMyAdapter(getApplicationContext(), mRequestsList);
                mRecyclerView.setAdapter(adminMyAdapter);
                //mRecyclerView.notifyAll();
            }
        });
        }catch (Exception ex){}
    }
}