package com.jntucep.c19_delhi;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StateWiseReport extends AppCompatActivity {

    private RecyclerView districtRecycler;
    private RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_wise_report);

        districtRecycler=findViewById(R.id.recycler);
        districtRecycler.setLayoutManager(new LinearLayoutManager(this));
        requestQueue= Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson() {
        String url="https://api.covid19india.org/state_district_wise.json";

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject state =response.getJSONObject("Andhra Pradesh");
                    JSONObject districtData=state.getJSONObject("districtData");

                    JSONArray keys=districtData.names();
                    int length=keys.length();
                    String district[]=new String[length];
                    String confirmed[]=new String[length];
                    String active[]=new String[length];
                    String recovered[]=new String[length];
                    String deceased[]=new String[length];

                    for (int i = 0; i < length; ++i) {
                        district[i]=keys.getString(i);
                        confirmed[i]=districtData.getJSONObject(district[i]).getString("confirmed");
                        active[i]=districtData.getJSONObject(district[i]).getString("active");
                        recovered[i]=districtData.getJSONObject(district[i]).getString("recovered");
                        deceased[i]=districtData.getJSONObject(district[i]).getString("deceased");

                    }
                    districtRecycler.setAdapter(new DistrictAdapter(district, confirmed, active, recovered, deceased));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );

        requestQueue.add(request);

    }
}
