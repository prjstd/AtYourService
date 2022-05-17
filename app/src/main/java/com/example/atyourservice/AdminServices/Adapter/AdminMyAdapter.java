package com.example.atyourservice.AdminServices.Adapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.AdminServices.Activities.AdminFlagServiceActivity;
import com.example.atyourservice.R;
import com.example.atyourservice.UserServices.Class.PendingServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminMyAdapter extends RecyclerView.Adapter<AdminRequestViewHolder>{

    private Context mContext;
    private List<PendingServices> mRequestsList;
    Activity activity;

    public AdminMyAdapter(Context mContext, List<PendingServices> mRequestsList,  Activity activity) {
        this.mContext = mContext;
        this.mRequestsList = mRequestsList;
        this.activity = activity;
    }

    @Override
    public AdminRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_adminreq_services, parent, false);

        return new AdminRequestViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(AdminRequestViewHolder holder, final int position) {
        holder.tvReqTitle.setText("الخدمة: "+mRequestsList.get(holder.getBindingAdapterPosition()).Service_Name);
        holder.tvUserName.setText("المقدم: "+mRequestsList.get(holder.getBindingAdapterPosition()).Full_Name);
        holder.tvReqDate.setText("تاريخ التقديم: "+mRequestsList.get(holder.getBindingAdapterPosition()).Request_Date);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View vi;
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        vi = inflater.inflate(R.layout.admin_request_info_dialog, null);
                        builder.setView(vi);

                        final TextView Nationality_Number = vi.findViewById(R.id.tv_Nationality_Number);
                        TextView Request_Date=vi.findViewById(R.id.tv_Request_Date);
                        TextView Service_Price=vi.findViewById(R.id.tv_Service_Price);
                        TextView Payment_Status=vi.findViewById(R.id.tv_Payment_Status);

                        Button Accept=vi.findViewById(R.id.dialogButtonAccept);
                        Button Reject=vi.findViewById(R.id.dialogButtonReject);


                        final FirebaseAuth auth=FirebaseAuth.getInstance();

                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        if(!mRequestsList.get(holder.getBindingAdapterPosition()).Nationality_Number.equals("")){
                            Nationality_Number.setText(mRequestsList.get(holder.getBindingAdapterPosition()).Nationality_Number);
                        }else{
                            Nationality_Number.setText("غير مدخل");

                        }
                        Request_Date.setText(mRequestsList.get(holder.getBindingAdapterPosition()).Request_Date);

                        Service_Price.setText(mRequestsList.get(holder.getBindingAdapterPosition()).Service_Price);
//                        if(mRequestsList.get(holder.getBindingAdapterPosition()).Service_Status.equals("0")){
//                            Service_Status.setText("قيد الاجراء");
//                        }else if(mRequestsList.get(holder.getBindingAdapterPosition()).Service_Status.equals("1")){
//                            Service_Status.setText("تم القبول مع انتظار الدفع للاصدار");
//                        }else if(mRequestsList.get(holder.getBindingAdapterPosition()).Service_Status.equals("2")){
//                            Service_Status.setText("تم القبول و الاصدار");
//                        }
                        if(mRequestsList.get(holder.getBindingAdapterPosition()).Payment_Status.equals("0")){
                            Payment_Status.setText("الرصيد غير كافي");
                        }else if(mRequestsList.get(holder.getBindingAdapterPosition()).Payment_Status.equals("1")){
                            Payment_Status.setText("تم الدفع");
                        }

                        Accept.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View v) {
                                try {
                                    dialog.show();

                                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                                    String Service_Status = "";
                                    if (mRequestsList.get(holder.getBindingAdapterPosition()).Payment_Status.equals("0")) {
                                        Service_Status = "1";
                                    } else if (mRequestsList.get(holder.getBindingAdapterPosition()).Payment_Status.equals("1")) {
                                        Service_Status = "2";
                                    }

                                    Map<String, Object> map = new HashMap<>();
                                    map.put("Replay_Date", date);
                                    map.put("Service_Status", Service_Status);

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("PendingService").document(mRequestsList.get(holder.getBindingAdapterPosition()).id)
                                            .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                dialog.dismiss();
                                                Toast.makeText(mContext, "تم قبول الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                                activity.recreate();
                                            }
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(mContext, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                                }
                                            });


                            }catch(Exception ex)
                            {
                            }
                        }
                        });

                        Reject.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View v) {
                                try{
                                    dialog.show();

                                    String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                                    String rejValue="3";

                                    Map<String, Object> map = new HashMap<>();
                                    map.put("Replay_Date", date);
                                    map.put("Service_Status", rejValue);

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("PendingService").document(mRequestsList.get(holder.getBindingAdapterPosition()).id)
                                            .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                dialog.dismiss();
                                                Toast.makeText(mContext, "تم رفض الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                                activity.recreate();

                                            }
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(mContext, "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }catch(Exception ex){}

                            }
                        });
                    }

                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRequestsList.size();
    }
}


class AdminRequestViewHolder extends RecyclerView.ViewHolder{

    ImageView mImage;
    TextView tvReqTitle, tvReqDate, tvUserName;
    CardView mCardView;

    AdminRequestViewHolder(View itemView) {
        super(itemView);//this solve the problem
        mImage = itemView.findViewById(R.id.postImage);

        tvReqTitle = itemView.findViewById(R.id.tvReqTitle);
        tvReqDate = itemView.findViewById(R.id.tvReqDate);
        tvUserName = itemView.findViewById(R.id.tvUserName);
        mCardView = itemView.findViewById(R.id.cardview);


    }
}