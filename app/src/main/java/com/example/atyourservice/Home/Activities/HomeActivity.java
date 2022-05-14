package com.example.atyourservice.Home.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.atyourservice.AdminServices.Activities.AdminFlagServiceActivity;
import com.example.atyourservice.UserServices.Activiteis.UserFlagServiceActivity;
import com.example.atyourservice.Home.Adapter.HomeAdapter;
import com.example.atyourservice.R;
import com.example.atyourservice.UserServices.Activiteis.UserHistoryRequestsActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> menu;
    ArrayList<Integer> pics;
    GridView listView;
    HomeAdapter adapter;
    boolean flag = false;
    int BackCounter = 0;


    @Override
    public void onBackPressed() {

        if(++BackCounter != 2)
            Toast.makeText(this, "اضغط مرة ثانية للخروج", Toast.LENGTH_SHORT).show();
        else
            super.onBackPressed(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Space(savedInstanceState);

        if (getSharedPreferences("UserInfo", MODE_PRIVATE).getString("type", "").equals("Admin"))
        {
            flag = true;
        }

        Init();
        Listners();

    }

    private void Init() {


        menu = new ArrayList<>();
        pics = new ArrayList<>();

        menu.add(getResources().getString(R.string.main_menu_flag));
        menu.add(getResources().getString(R.string.main_menu_ncrc));
        menu.add(getResources().getString(R.string.main_menu_identity));
        menu.add(getResources().getString(R.string.main_menu_born));
        menu.add(getResources().getString(R.string.main_menu_profile));
        menu.add(getResources().getString(R.string.main_menu_history));

        pics.add(R.drawable.shopping);
        pics.add(R.drawable.note);
        pics.add(R.drawable.finance);
        pics.add(R.drawable.plan);
        pics.add(R.drawable.track);
        pics.add(R.drawable.social);

        listView = findViewById(R.id.listView);
        adapter = new HomeAdapter(HomeActivity.this, menu, pics);
        listView.setAdapter(adapter);

    }
    private void Listners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position){
                    case 0:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminFlagServiceActivity.class));

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserFlagServiceActivity.class));
                        }
                        break;
                    case 1:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminFlagServiceActivity.class));

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserFlagServiceActivity.class));
                        }
                        break;
                    case 2:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminFlagServiceActivity.class));

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserFlagServiceActivity.class));
                        }
                        break;
                    case 3:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminFlagServiceActivity.class));

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserFlagServiceActivity.class));
                        }
                        break;
                    case 4:
                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                        break;
                    case 5:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminFlagServiceActivity.class));

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserHistoryRequestsActivity.class));
                        }
                        break;
                }
            }
        });
    }
//    private void Space(Bundle savedInstanceState){
//
//        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
//        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
//        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.main_space_group), R.drawable.ic_baseline_groups_24));
//        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.main_space_chat),R.drawable.ic_baseline_bubble_chart_24));
//        spaceNavigationView.setSpaceItemIconSize(100);
//
//        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
//            @Override
//            public void onCentreButtonClick() {
//
//                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
//                HomeActivity.this.finish();
//
//            }
//
//            @Override
//            public void onItemClick(int itemIndex, String itemName) {
//
//                switch (itemName){
//                    case "Profile" :
//                            //startActivity(new Intent(HomeActivity.this, ProfileActivity.class);
//                            HomeActivity.this.finish();
//                        break;
//                    case "Process" :
//                        if(flag){
//                            startActivity(new Intent(HomeActivity.this, PendingActivity.class);
//                            HomeActivity.this.finish();
//                        }else{
//                            startActivity(new Intent(HomeActivity.this, ProcessActivity.class);
//                        HomeActivity.this.finish();
//                        }
//                        break;
//                }
//            }
//
//            @Override
//            public void onItemReselected(int itemIndex, String itemName) {
//
//                switch (itemName){
//                    case "Profile" :
//                            startActivity(new Intent(HomeActivity.this, ProfileActivity.class);
//                            HomeActivity.this.finish();
//
//                        break;
//                    case "Process" :
//                        if(flag){
//                            startActivity(new Intent(HomeActivity.this, PendingActivity.class);
//                            HomeActivity.this.finish();
//                        }else{
//                            startActivity(new Intent(HomeActivity.this, ProcessActivity.class);
//                            HomeActivity.this.finish();
//                        }
//                        break;
//            }
//            }
//        });
//    }


}