package com.jntucep.c19_delhi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    private Button btn;
    private TextView newcases,deltadeaths,deltarecovered,date;
    private JSONObject response1;
    private PieChart pieChart;
    private TextView conformed,deaths,recovered,active;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ExploreFragment() {

    }


    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        btn = v.findViewById(R.id.button);
        conformed = v.findViewById(R.id.textView5);
        deaths = v.findViewById(R.id.textView7);
        recovered = v.findViewById(R.id.textView6);
        active = v.findViewById(R.id.textView8);
        newcases = v.findViewById(R.id.textView19);
        deltadeaths = v.findViewById(R.id.textView21);
        deltarecovered = v.findViewById(R.id.textView23);
        pieChart = v.findViewById(R.id.piechart);
        date = v.findViewById(R.id.date);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StateWiseReport.class);
                startActivity(intent);
            }
        });
        parseJson();
        return v;
    }

    private void parseJson() {

        String url="https://api.covid19india.org/data.json";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(JSONObject response) {
                response1=response;
                try {
                    JSONArray array=response.getJSONArray("statewise");
                    for(int i =0;i<array.length();i++) {

                        JSONObject object = array.getJSONObject(i);
                        if (object.getString("state").equals("Delhi")) {
                            Log.d("dataaa", object.toString());
                            active.setText(object.getString("active"));
                            conformed.setText(object.getString("confirmed"));
                            recovered.setText(object.getString("recovered"));
                            deaths.setText(object.getString("deaths"));
                            newcases.setText(object.getString("deltaconfirmed"));
                            deltarecovered.setText(object.getString("deltarecovered"));
                            deltadeaths.setText(object.getString("deltadeaths"));
                            date.setText(object.getString("lastupdatedtime"));
                            pieChart.addPieSlice(new PieModel("deltaconfirmed", Integer.parseInt(newcases.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("deltarecovered", Integer.parseInt(deltarecovered.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("deltadeaths", Integer.parseInt(deltadeaths.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        volleystuff.getInstance(getActivity()).addToRequestQueue(request);




    }
}