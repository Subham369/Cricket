package com.example.cricketapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdpter extends RecyclerView.Adapter<MyAdpter.ViewHolder>{
    private List<Model> modelList;
    private Context context;

    public MyAdpter(List<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model model=modelList.get(position);
        holder.team1.setText(model.getTeam1());
        holder.team2.setText(model.getTeam2());
        holder.matchType.setText(model.getMatchtype());
        holder.matchStatus.setText(model.getMatchstatus());
        holder.date.setText(model.getDate());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matchId=model.getId();
                String date=model.getDate();
                Intent intent=new Intent(context,MatchDetail.class);
                intent.putExtra("match_id",matchId);
                intent.putExtra("date",date);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });
    }


    @Override
    public int getItemCount()

    {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView team1,team2,matchType,matchStatus,date;
        CardView cardView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
           team1= itemView.findViewById(R.id.team1);
           team2= itemView.findViewById(R.id.team2);
           matchType=itemView.findViewById(R.id.matchtype);
           matchStatus=itemView.findViewById(R.id.matchstatus);
           date=itemView.findViewById(R.id.date);
           cardView=itemView.findViewById(R.id.cardview);

        }
    }
}
