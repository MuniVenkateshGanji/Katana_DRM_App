package com.jntucep.c19_delhi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DonationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DonationFragment extends Fragment {
    Spinner sp_Funds;

    ArrayList<String> arrayList_Funds;
    ArrayAdapter<String> arrayAdapter_Funds;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DonationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DonationFragment newInstance(String param1, String param2) {
        DonationFragment fragment = new DonationFragment();
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
        View v = inflater.inflate(R.layout.fragment_donation, container, false);
        sp_Funds = v.findViewById(R.id.sp_Funds);
        arrayList_Funds = new ArrayList<>();
        arrayList_Funds.add("SELECT");
        arrayList_Funds.add("PM CARES");
        arrayList_Funds.add("PM NATIONAL RELIEF FUND");
        arrayList_Funds.add("CM RELIEF FUND");

        arrayAdapter_Funds=new ArrayAdapter<>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_dropdown_item,arrayList_Funds);

        sp_Funds.setAdapter(arrayAdapter_Funds);
        sp_Funds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==1) {



                    Intent intent = new Intent(getActivity(),
                            PMCARES.class);
                    startActivity(intent);

                }
                if(position==2) {



                    Intent intent = new Intent(getActivity(),
                            PMNRF.class);
                    startActivity(intent);

                }
                if(position==3) {



                    Intent intent = new Intent(getActivity(),
                            CMRF.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }
}