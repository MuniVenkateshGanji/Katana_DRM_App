package com.jntucep.c19_delhi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements OnMapReadyCallback, GeoQueryEventListener {

    private Button btn;
    private TextView newcases,deltadeaths,deltarecovered,date,date1,totalcov,totaldea,totalrec;
    private JSONObject response1;
    private PieChart pieChart;
    private BarChart barChart;
    private TextView conformed,deaths,recovered,active;
    private GoogleMap mMap;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentUser;
    private DatabaseReference myLocationRef;
    private GeoFire geoFire;
    private WebView webView;
    private List<LatLng> dangerousAreas;
    private CardView cardView1;
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
        barChart = v.findViewById(R.id.barChart);
        date1 = v.findViewById(R.id.date1);
        totalcov = v.findViewById(R.id.concases);
        totaldea = v.findViewById(R.id.totaldea);
        totalrec = v.findViewById(R.id.totalrec);
        cardView1 = v.findViewById(R.id.vaccinecard);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StateWiseReport.class);
                startActivity(intent);
            }
        });


        parseJson();
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),vaccine.class);
                startActivity(intent);
            }
        });


        return v;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buildLocationRequest();
        buildLocationCallback();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initArea();
        settingGeoFire();
    }


    private void initArea() {
        dangerousAreas = new ArrayList<>();
        dangerousAreas.add(new LatLng(18.2425, 83.3482));
        dangerousAreas.add(new LatLng(15.8167, 80.3587));
        dangerousAreas.add(new LatLng(18.0930, 83.5510));
        dangerousAreas.add(new LatLng(18.8992, 83.4604));
        dangerousAreas.add(new LatLng(18.6149, 83.5297));
        dangerousAreas.add(new LatLng(17.6666, 83.2063));
        dangerousAreas.add(new LatLng(17.6664, 82.6105));
        dangerousAreas.add(new LatLng(17.6686, 82.9641));
        dangerousAreas.add(new LatLng(17.8055, 83.2089));
        dangerousAreas.add(new LatLng(17.6868, 83.2185));
        dangerousAreas.add(new LatLng(17.2922, 82.3506));
        dangerousAreas.add(new LatLng(17.5859, 83.1959));
        dangerousAreas.add(new LatLng(17.0500, 82.1679));
        dangerousAreas.add(new LatLng(17.0757, 82.1360));
        dangerousAreas.add(new LatLng(17.3730, 78.5476));
        dangerousAreas.add(new LatLng(17.0005, 81.8040));
        dangerousAreas.add(new LatLng(17.1146, 82.2522));
        dangerousAreas.add(new LatLng(17.3730, 78.5476));
        dangerousAreas.add(new LatLng(17.2479, 81.6432));
        dangerousAreas.add(new LatLng(13.0489, 80.2586));
        dangerousAreas.add(new LatLng(16.4330, 81.6966));
        dangerousAreas.add(new LatLng(17.0126, 81.7274));
        dangerousAreas.add(new LatLng(16.9930, 81.6668));
        dangerousAreas.add(new LatLng(16.8971, 81.6634));
        dangerousAreas.add(new LatLng(16.8073, 81.5316));
        dangerousAreas.add(new LatLng(16.6547, 81.7445));
        dangerousAreas.add(new LatLng(16.8108, 81.2637));
        dangerousAreas.add(new LatLng(16.7107, 81.0952));
        dangerousAreas.add(new LatLng(16.5823, 81.3784));
        dangerousAreas.add(new LatLng(16.5864, 81.4636));
        dangerousAreas.add(new LatLng(16.5449, 81.5212));
        dangerousAreas.add(new LatLng(16.8985, 80.1033));
        dangerousAreas.add(new LatLng(16.5062, 80.6480));
        dangerousAreas.add(new LatLng(16.4655, 80.7190));
        dangerousAreas.add(new LatLng(16.1809, 81.1303));
        dangerousAreas.add(new LatLng(16.7850, 80.8488));
        dangerousAreas.add(new LatLng(16.8260, 80.9339));
        dangerousAreas.add(new LatLng(16.4760, 79.4394));
        dangerousAreas.add(new LatLng(16.5953, 79.7205));
        dangerousAreas.add(new LatLng(16.3990, 78.6370));
        dangerousAreas.add(new LatLng(16.2359, 80.0496));
        dangerousAreas.add(new LatLng(16.8073, 81.5316));
        dangerousAreas.add(new LatLng(16.4346, 80.5662));
        dangerousAreas.add(new LatLng(15.8843, 80.3130));
        dangerousAreas.add(new LatLng(15.8167, 80.3587));
        dangerousAreas.add(new LatLng(15.5057, 80.0499));
        dangerousAreas.add(new LatLng(15.0725, 79.9053));
        dangerousAreas.add(new LatLng(14.4426, 79.9865));
        dangerousAreas.add(new LatLng(13.9059, 79.8910));
        dangerousAreas.add(new LatLng(14.0025, 80.0710));
        dangerousAreas.add(new LatLng(13.7009, 80.0209));
        dangerousAreas.add(new LatLng(13.5899, 80.0328));
        dangerousAreas.add(new LatLng(13.7527, 79.7067));
        dangerousAreas.add(new LatLng(13.6288, 79.4192));
        dangerousAreas.add(new LatLng(13.6373, 79.5037));
        dangerousAreas.add(new LatLng(13.4804, 74.7717));
        dangerousAreas.add(new LatLng(13.4323, 79.9612));
        dangerousAreas.add(new LatLng(12.9609, 75.0932));
        dangerousAreas.add(new LatLng(13.3201, 79.5856));
        dangerousAreas.add(new LatLng(12.7687, 75.2071));
        dangerousAreas.add(new LatLng(13.0012, 78.4795));
        dangerousAreas.add(new LatLng(13.8223, 77.5009));
        dangerousAreas.add(new LatLng(14.5506, 77.1087));
        dangerousAreas.add(new LatLng(14.6819, 77.6006));
        dangerousAreas.add(new LatLng(14.7265, 78.7321));
        dangerousAreas.add(new LatLng(14.7526, 78.5541));
        dangerousAreas.add(new LatLng(14.6394, 78.5349));
        dangerousAreas.add(new LatLng(14.4673, 78.8242));
        dangerousAreas.add(new LatLng(14.7309, 79.0589));
        dangerousAreas.add(new LatLng(14.4673, 78.8242));
        dangerousAreas.add(new LatLng(14.6084, 78.6626));
        dangerousAreas.add(new LatLng(15.6319, 77.2759));
        dangerousAreas.add(new LatLng(15.2289, 77.3181));
        dangerousAreas.add(new LatLng(15.4757, 77.3884));
        dangerousAreas.add(new LatLng(15.3142, 77.5440));
        dangerousAreas.add(new LatLng(15.8790, 78.5853));
        dangerousAreas.add(new LatLng(15.6860, 77.7710));
        dangerousAreas.add(new LatLng(15.8281, 78.0373));
        dangerousAreas.add(new LatLng(15.8556, 78.2646));
        dangerousAreas.add(new LatLng(15.3181, 78.2255));
        dangerousAreas.add(new LatLng(15.4777, 78.4873));
        dangerousAreas.add(new LatLng(15.6799, 78.4226));
        dangerousAreas.add(new LatLng(14.9640, 78.5916));
        dangerousAreas.add(new LatLng(15.8396, 78.4908));


    }

    private void settingGeoFire() {
        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocationRef);

    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                if (mMap != null) {

                    geoFire.setLocation("you", new GeoLocation(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (currentUser != null) currentUser.remove();
                            currentUser = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()))
                                    .title("You"));
                            //after add marker and move camera
                            mMap.animateCamera(CameraUpdateFactory
                                    .newLatLngZoom(currentUser.getPosition(), 6.5f));
                        }
                    });
                }
            }
        };

    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        mMap.getUiSettings().setZoomControlsEnabled(true);


        if (fusedLocationProviderClient != null)
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        //add circles to dangerous areas
        for (LatLng latLng : dangerousAreas){
            mMap.addCircle(new CircleOptions().center(latLng).radius(10000)//20km
                    .strokeColor(Color.RED)
                    .fillColor(0x220000FF)  //22 is transparent color
                    .strokeWidth(5.0f)
            );

        }
    }

    @Override
    public void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {

    }

    @Override
    public void onKeyExited(String key) {

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(getActivity(),""+ error.getMessage(),Toast.LENGTH_SHORT).show();
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
                        if (object.getString("state").equals("Andhra Pradesh")) {
                            Log.d("dataaa", object.toString());
                            active.setText(object.getString("active"));
                            conformed.setText(object.getString("confirmed"));
                            recovered.setText(object.getString("recovered"));
                            deaths.setText(object.getString("deaths"));
                            totalcov.setText(object.getString("confirmed"));
                            totalrec.setText(object.getString("recovered"));
                            totaldea.setText(object.getString("deaths"));
                            newcases.setText(object.getString("deltaconfirmed"));
                            deltarecovered.setText(object.getString("deltarecovered"));
                            deltadeaths.setText(object.getString("deltadeaths"));
                            date.setText(object.getString("lastupdatedtime"));
                            date1.setText(object.getString("lastupdatedtime"));
                            barChart.addBar(new BarModel("confirmed", Integer.parseInt(conformed.getText().toString()), Color.parseColor("#66BB6A")));
                            barChart.addBar(new BarModel("recovered", Integer.parseInt(recovered.getText().toString()), Color.parseColor("#EF5350")));
                            barChart.addBar(new BarModel("deaths", Integer.parseInt(deaths.getText().toString()), Color.parseColor("#29B6F6")));
                            barChart.addBar(new BarModel("active", Integer.parseInt(active.getText().toString()), Color.parseColor("#F8A78B")));
                            barChart.startAnimation();
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