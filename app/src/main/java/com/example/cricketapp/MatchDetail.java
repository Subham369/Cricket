package com.example.cricketapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MatchDetail extends AppCompatActivity {

    TextView mTeam1,mTeam2,mMatchStatus,mScore,mDes,mDate;
    private String url="http://cricapi.com/api/cricketScore/?apikey=I4Ve1dy10pYD6t9ON7RQChXnCHr2&unique_id=1199525";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Match Details ");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        String id=intent.getStringExtra("match_id");
        String date=intent.getStringExtra("date");

        url=url+id;
        mTeam1=findViewById(R.id.team1);
        mTeam2=findViewById(R.id.team2);
        mMatchStatus=findViewById(R.id.matchstatus);
        mScore=findViewById(R.id.score);
        mDes=findViewById(R.id.des);
        mDate=findViewById(R.id.date);

        mDate.setText(date);

        loadData();

    }

    private void loadData() {

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading .....");
        pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String t1=jsonObject.getString("team-1");
                    String t2=jsonObject.getString("team-2");
                    String mStatus=jsonObject.getString("matchStarted");
                    if (mStatus.equals("true")){
                        mStatus="Match Started";
                    }
                    else
                        mStatus="Match not started";

                    mTeam1.setText(t1);
                    mTeam2.setText(t2);
                    mMatchStatus.setText(mStatus);

                    try {
                        String sco=jsonObject.getString("score");
                        String description=jsonObject.getString("description");
                        mScore.setText(sco);
                        mDes.setText(description);

                    }catch (Exception e)
                    {
                        Toast.makeText(MatchDetail.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(MatchDetail.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchDetail.this, "Error :"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
