package com.example.cricketapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    private String url="http://cricapi.com/api/matches?apikey=I4Ve1dy10pYD6t9ON7RQChXnCHr2";
    private RecyclerView.Adapter mAdapter;
    private List<Model> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        modelList=new ArrayList<>();
        loadUrlData();
    }

    private void loadUrlData() {
      final ProgressDialog pd=new ProgressDialog(this);
    pd.setMessage("Loading ...... ");
    pd.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();

                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("matches");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            String uniqueId=jsonArray.getJSONObject(i).getString("unique_id");
                            String team1Tv=jsonArray.getJSONObject(i).getString("team-1");
                            String team2Tv=jsonArray.getJSONObject(i).getString("team-2");
                            String matchtypeTv=jsonArray.getJSONObject(i).getString("type");
                            String matchstatusTv=jsonArray.getJSONObject(i).getString("matchStarted");
                            if(matchstatusTv.equals("true"))
                            {
                                matchstatusTv="Match Started";
                            }
                            else
                            {
                                matchstatusTv="Match not started";
                            }
                            String dateGMT=jsonArray.getJSONObject(i).getString("dateTimeGMT");
                            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            format.setTimeZone(TimeZone.getTimeZone(dateGMT));
                            Date date=format.parse(dateGMT);
                            SimpleDateFormat format2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String dateTime=format2.format(date);

                            Model model=new Model(uniqueId,team1Tv,team2Tv,matchtypeTv,matchstatusTv,dateTime);
                            modelList.add(model);

                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    mAdapter=new MyAdpter(modelList,getApplicationContext());
                    recyclerView.setAdapter(mAdapter);
                    }

                catch(Exception e){
                            Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }

    });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
