package com.example.atyourservice.AdminServices.Adapter;

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

import com.example.atyourservice.R;
import com.example.atyourservice.UserServices.Class.PendingServices;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class AdminMyAdapter extends RecyclerView.Adapter<AdminRequestViewHolder>{

    private Context mContext;
    private List<PendingServices> mRequestsList;

    public AdminMyAdapter(Context mContext, List<PendingServices> mRequestsList) {
        this.mContext = mContext;
        this.mRequestsList = mRequestsList;
    }

    @Override
    public AdminRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_usereq_history, parent, false);

        return new AdminRequestViewHolder(mView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(AdminRequestViewHolder holder, int position) {
        holder.tvReqTitle.setText(mRequestsList.get(position).Service_Name);
        holder.tvReqDate.setText("تاريخ التقديم : "+mRequestsList.get(position).Request_Date);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View vi;
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        vi = inflater.inflate(R.layout.user_history_info_dialog, null);
                        builder.setView(vi);

                        final TextView user = vi.findViewById(R.id.tv_userpresent);
                        TextView phone=vi.findViewById(R.id.tv_phone_present);
                        TextView title=vi.findViewById(R.id.tv_title_present);
                        TextView disc=vi.findViewById(R.id.tv_disc_present);
                        TextView from=vi.findViewById(R.id.star_date2);
                        TextView to=vi.findViewById(R.id.end_date2);
                        TextView workstate=vi.findViewById(R.id.tv_status_present);
                        Button ok=vi.findViewById(R.id.dialogButtonOk);

                        final TextView acceptedPeople=vi.findViewById(R.id.tv_personnum_present);

                        final FirebaseAuth auth=FirebaseAuth.getInstance();

                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        if(!mRequestsList.get(position).Nationality_Number.equals("")){
                            user.setText(mRequestsList.get(position).Nationality_Number);
                        }else{
                            user.setText("لم تقم بادخال الرقم الوطني عند التقديم");

                        }
                        if(!mRequestsList.get(position).Replay_Date.equals("")){
                            phone.setText(mRequestsList.get(position).Replay_Date);
                        }else{
                            phone.setText("لم يتم الرد بعد");
                        }
                        title.setText(mRequestsList.get(position).Service_Price);
                        if(mRequestsList.get(position).Service_Status.equals("0")){
                            disc.setText("قيد الاجراء");
                        }else if(mRequestsList.get(position).Service_Status.equals("1")){
                            disc.setText("تم القبول مع انتظار الدفع للاصدار");
                        }else if(mRequestsList.get(position).Service_Status.equals("2")){
                            disc.setText("تم القبول و الاصدار");
                        }
                        if(mRequestsList.get(position).Payment_Status.equals("0")){
                            from.setText("الرصيد غير كافي");
                        }else if(mRequestsList.get(position).Payment_Status.equals("1")){
                            from.setText("تم الدفع");
                        }

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
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
    TextView tvReqTitle,tvReqDate;
    CardView mCardView;

    AdminRequestViewHolder(View itemView) {
        super(itemView);//this solve the problem
        mImage = itemView.findViewById(R.id.postImage);

        tvReqTitle = itemView.findViewById(R.id.tvReqTitle);
        tvReqDate = itemView.findViewById(R.id.tvReqDate);
        mCardView = itemView.findViewById(R.id.cardview);


    }
}