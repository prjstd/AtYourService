package com.example.atyourservice.AdminServices.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.AdminServices.Adapter.AdminMyAdapter;
import com.example.atyourservice.Home.Activities.HomeActivity;
import com.example.atyourservice.R;
import com.example.atyourservice.UserServices.Class.PendingServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminNonPayPendingActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<PendingServices> mRequestsList;
    ProgressDialog pb;
    TextView noData;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminNonPayPendingActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nonpaypen_service);

        Init();
        GetData();
    }

    private void Init() {
        pb = new ProgressDialog(this);
        pb.setTitle("يرجى الانتظار !");
        pb.setMessage("جاري التحميل...");
        noData = findViewById(R.id.noData);
        mRequestsList  =new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerview);
        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void GetData() {
        try{

            if(!mRequestsList.isEmpty())
                mRequestsList.clear();

        pb.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PendingService")
                .whereEqualTo("Payment_Status", "0")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pb.dismiss();

                List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();

                for(int i=0; i<gr.size(); i++) {
                    if(gr.get(i).getString("Service_Status").equals("0")){
                        mRequestsList.add(gr.get(i).toObject(PendingServices.class));//الطلبات غير المدفوعة
                    }
                }

                if(!mRequestsList.isEmpty()){
                    AdminMyAdapter adminMyAdapter = new AdminMyAdapter(AdminNonPayPendingActivity.this, mRequestsList);
                    mRecyclerView.setAdapter(adminMyAdapter);
                }else{
                    noData.setVisibility(View.VISIBLE);
                }

            }
        });
            mRecyclerView.notifyAll();

        }catch (Exception ex){}
    }
}