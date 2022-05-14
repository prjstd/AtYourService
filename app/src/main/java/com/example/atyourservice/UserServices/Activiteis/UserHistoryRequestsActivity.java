package com.example.atyourservice.UserServices.Activiteis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.atyourservice.Home.Activities.HomeActivity;
import com.example.atyourservice.UserServices.Class.PendingServices;
import com.example.atyourservice.Home.Adapter.MyAdapter;
import com.example.atyourservice.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryRequestsActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    ArrayList<PendingServices> mHistoryList;
    ProgressDialog pb;
    TextView noData;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserHistoryRequestsActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_requests);

        Init();
        GetData();

    }

    private void Init() {
        pb = new ProgressDialog(this);
        pb.setTitle("يرجى الانتظار !");
        pb.setMessage("جاري التحميل...");
        mHistoryList  =new ArrayList<>();
        if(!mHistoryList.isEmpty())
            mHistoryList.clear();
        mRecyclerView = findViewById(R.id.recyclerview);
        noData = findViewById(R.id.noData);

        final GridLayoutManager mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private void GetData() {
        if(!mHistoryList.isEmpty())
            mHistoryList.clear();

        pb.show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PendingService")
               .whereEqualTo("User_id", getSharedPreferences("UserInfo", MODE_PRIVATE).getString("Uid", ""))
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                pb.dismiss();

                List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();

                for(int i=0; i<gr.size(); i++) {
                    if(gr.get(i).getString("Service_Status").equals("1") || gr.get(i).getString("Service_Status").equals("2")){
                        mHistoryList.add(gr.get(i).toObject(PendingServices.class));
                    }
                }

                if(!mHistoryList.isEmpty()) {
                    MyAdapter myAdapter = new MyAdapter(UserHistoryRequestsActivity.this, mHistoryList);
                    mRecyclerView.setAdapter(myAdapter);
                }else{
                    noData.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}