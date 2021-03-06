package com.example.atyourservice.Home.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atyourservice.AdminServices.Activities.AdminBornCertificateActivity;
import com.example.atyourservice.AdminServices.Activities.AdminDriverLicenceActivity;
import com.example.atyourservice.AdminServices.Activities.AdminFlagServiceActivity;
import com.example.atyourservice.AdminServices.Activities.AdminIdentityServiceActivity;
import com.example.atyourservice.AdminServices.Activities.AdminNCRCServiceActivity;
import com.example.atyourservice.AdminServices.Activities.AdminNonPayPendingActivity;
import com.example.atyourservice.AdminServices.Activities.AdminPassportServiceActivity;
import com.example.atyourservice.UserServices.Activiteis.UserBornCertificateActivity;
import com.example.atyourservice.UserServices.Activiteis.UserDriveLicenseActivity;
import com.example.atyourservice.UserServices.Activiteis.UserFlagServiceActivity;
import com.example.atyourservice.Home.Adapter.HomeAdapter;
import com.example.atyourservice.R;
import com.example.atyourservice.UserServices.Activiteis.UserHistoryRequestsActivity;
import com.example.atyourservice.UserServices.Activiteis.UserIdentityerviceActivity;
import com.example.atyourservice.UserServices.Activiteis.UserNCRCServiceActivity;
import com.example.atyourservice.UserServices.Activiteis.UserPassporterviceActivity;
//import com.luseen.spacenavigation.SpaceItem;
//import com.luseen.spacenavigation.SpaceNavigationView;
//import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> menu;
    ArrayList<Integer> pics;
    GridView listView;
    HomeAdapter adapter;
    boolean flag = false;
    int BackCounter = 0;
    LinearLayout profileTag, historyTag, homeTage;
    TextView hispenTxt;


    @Override
    public void onBackPressed() {

        if(++BackCounter != 2)
            Toast.makeText(this, "???????? ?????? ?????????? ????????????", Toast.LENGTH_SHORT).show();
        else
            super.onBackPressed(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);
//       Space(savedInstanceState);

        Init();
        Listners();
        if (getSharedPreferences("UserInfo", MODE_PRIVATE).getString("type", "").equals("Admin"))
        {
            flag = true;
            hispenTxt.setText("?????? ????????????");
        }

    }

    private void Init() {

        profileTag = findViewById(R.id.profileTag);
        homeTage = findViewById(R.id.homeTag);
        historyTag = findViewById(R.id.historyTag);
        hispenTxt = findViewById(R.id.hispenTxt);

        menu = new ArrayList<>();
        pics = new ArrayList<>();

        menu.add(getResources().getString(R.string.main_menu_flag));
        menu.add(getResources().getString(R.string.main_menu_ncrc));
        menu.add(getResources().getString(R.string.main_menu_identity));
        menu.add(getResources().getString(R.string.main_menu_passport));
        menu.add(getResources().getString(R.string.main_menu_born));
        menu.add(getResources().getString(R.string.main_menu_drive));


        pics.add(R.drawable.flag);
        pics.add(R.drawable.ncrc);
        pics.add(R.drawable.identity);
        pics.add(R.drawable.passport);
        pics.add(R.drawable.born);
        pics.add(R.drawable.carcertificate);


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
                            HomeActivity.this.finish();

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserFlagServiceActivity.class));
                            HomeActivity.this.finish();

                        }
                        break;
                    case 1:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminNCRCServiceActivity.class));
                            HomeActivity.this.finish();

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserNCRCServiceActivity.class));
                            HomeActivity.this.finish();

                        }
                        break;
                    case 2:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminIdentityServiceActivity.class));
                            HomeActivity.this.finish();

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserIdentityerviceActivity.class));
                            HomeActivity.this.finish();

                        }
                        break;
                    case 3:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminPassportServiceActivity.class));
                            HomeActivity.this.finish();

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserPassporterviceActivity.class));
                            HomeActivity.this.finish();

                        }
                        break;
                    case 4:
                        if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminBornCertificateActivity.class));
                            HomeActivity.this.finish();

                        }else{
                            startActivity(new Intent(HomeActivity.this, UserBornCertificateActivity.class));
                            HomeActivity.this.finish();

                        }
                        break;
                    case 5:
                         if(flag){
                            startActivity(new Intent(HomeActivity.this, AdminDriverLicenceActivity.class));
                            HomeActivity.this.finish();
                        }else{
                            startActivity(new Intent(HomeActivity.this, UserDriveLicenseActivity.class));
                            HomeActivity.this.finish();

                        }
                        break;
//                    case 6:
//                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                        HomeActivity.this.finish();
//
//                        break;
//                    case 7:
//                        if(flag){
//                            startActivity(new Intent(HomeActivity.this, AdminNonPayPendingActivity.class));
//                            HomeActivity.this.finish();
//
//                        }else{
//                            startActivity(new Intent(HomeActivity.this, UserHistoryRequestsActivity.class));
//                            HomeActivity.this.finish();
//
//                        }
//                        break;
                }
            }
        });

        profileTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                HomeActivity.this.finish();
            }
        });

        historyTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    startActivity(new Intent(HomeActivity.this, AdminNonPayPendingActivity.class));
                    HomeActivity.this.finish();

                }else{
                    startActivity(new Intent(HomeActivity.this, UserHistoryRequestsActivity.class));
                    HomeActivity.this.finish();

                }
            }
        });

        homeTage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                HomeActivity.this.finish();
            }
        });
    }



//    private void Space(Bundle savedInstanceState) {
//
//        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
//        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
//        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.main_space_profile), R.drawable.ic_baseline_person_24));
//        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.main_space_history), R.drawable.ic_baseline_history_24));
//        spaceNavigationView.setSpaceItemIconSize(100);
//
//        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
//            @Override
//            public void onCentreButtonClick() {
//                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
//                HomeActivity.this.finish();
//            }
//
//            @Override
//            public void onItemClick(int itemIndex, String itemName) {
//
//                switch (itemIndex) {
//                    case 0:
//                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                        break;
//                    case 1:
//                        if (flag) {
//                            startActivity(new Intent(HomeActivity.this, AdminNonPayPendingActivity.class));
//
//                        } else {
//                            startActivity(new Intent(HomeActivity.this, UserHistoryRequestsActivity.class));
//                        }
//                        break;
//                }
//            }
//
//            @Override
//            public void onItemReselected(int itemIndex, String itemName) {
//
//                switch (itemIndex) {
//                    case 0:
//                        startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
//                        break;
//                    case 1:
//                        if (flag) {
//                            startActivity(new Intent(HomeActivity.this, AdminNonPayPendingActivity.class));
//
//                        } else {
//                            startActivity(new Intent(HomeActivity.this, UserHistoryRequestsActivity.class));
//                        }
//                        break;
//                }
//            }
//        });
//
//
//    }
}