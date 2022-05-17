package com.example.atyourservice.Home.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.UserServices.Class.PendingServices;
import com.example.atyourservice.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<UserHistoryViewHolder>{

    private Context mContext;
    private List<PendingServices> mHistoryList;

    public MyAdapter(Context mContext, List<PendingServices> mHistoryList) {
        this.mContext = mContext;
        this.mHistoryList = mHistoryList;
    }

    @Override
    public UserHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_usereq_history, parent, false);

        return new UserHistoryViewHolder(mView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder( UserHistoryViewHolder holder, int position) {
        holder.tvReqTitle.setText(mHistoryList.get(position).Service_Name);
        holder.tvReqDate.setText("تاريخ التقديم : "+mHistoryList.get(position).Request_Date);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            View vi;
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            vi = inflater.inflate(R.layout.user_history_info_dialog, null);
                            builder.setView(vi);

                            final TextView Nationality_Number = vi.findViewById(R.id.tv_Nationality_Number);
                            TextView Request_Date=vi.findViewById(R.id.tv_Request_Date);
                            TextView Replay_Date=vi.findViewById(R.id.tv_Replay_Date);
                            TextView Service_Status=vi.findViewById(R.id.tv_Service_Status);
                            TextView Service_Price=vi.findViewById(R.id.tv_Service_Price);
                            TextView Payment_Status=vi.findViewById(R.id.tv_Payment_Status);

                            Button ok=vi.findViewById(R.id.dialogButtonOk);


                            final FirebaseAuth auth=FirebaseAuth.getInstance();

                            final AlertDialog dialog = builder.create();
                            dialog.show();
                            if(!mHistoryList.get(position).Nationality_Number.equals("")){
                                Nationality_Number.setText(mHistoryList.get(position).Nationality_Number);
                            }else{
                                Nationality_Number.setText("لم تقم بادخال الرقم الوطني عند التقديم");

                            }

                            Request_Date.setText(mHistoryList.get(position).Request_Date);

                            if(!mHistoryList.get(position).Replay_Date.equals("")){
                                Replay_Date.setText(mHistoryList.get(position).Replay_Date);
                            }else{
                                Replay_Date.setText("لم يتم الرد بعد");
                            }
                            Service_Price.setText(mHistoryList.get(position).Service_Price);

                            if(mHistoryList.get(position).Service_Status.equals("0")){
                                Service_Status.setText("قيد الاجراء");
                            }else if(mHistoryList.get(position).Service_Status.equals("1")){
                                Service_Status.setText("تم القبول مع انتظار الدفع للاصدار");
                            }else if(mHistoryList.get(position).Service_Status.equals("2")){
                                Service_Status.setText("تم القبول و الاصدار");
                            }else if(mHistoryList.get(position).Service_Status.equals("3")){
                                Service_Status.setText("الطلب مرفوض");
                            }
                            if(mHistoryList.get(position).Payment_Status.equals("0")){
                                Payment_Status.setText("الرصيد غير كافي");
                            }else if(mHistoryList.get(position).Payment_Status.equals("1")){
                                Payment_Status.setText("تم الدفع");
                            }

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }catch (Exception ex){}

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistoryList.size();
    }
}


class UserHistoryViewHolder extends RecyclerView.ViewHolder{

    ImageView mImage;
    TextView tvReqTitle,tvReqDate;
    CardView mCardView;

    UserHistoryViewHolder(View itemView) {
        super(itemView);//this solve the problem
        mImage = itemView.findViewById(R.id.postImage);

        tvReqTitle = itemView.findViewById(R.id.tvReqTitle);
        tvReqDate = itemView.findViewById(R.id.tvReqDate);
        mCardView = itemView.findViewById(R.id.cardview);


    }
}