package com.jntucep.c19_delhi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.myViewHolder>{

    String districts[],confirmed[], active[], recovered[], deceased[];

    public DistrictAdapter(String[] districts, String[] confirmed,String[] active,String[] recovered,String[] deceased) {
        this.districts = districts;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.deceased = deceased;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycler_state, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int i) {
        String districtName=districts[i];
        String confirmedCases=confirmed[i];
        String activeCases=active[i];
        String recoveredCases=recovered[i];
        String deathCases=deceased[i];
        holder.txt_confirmed_st.setText(confirmedCases);
        holder.txt_active_st.setText(activeCases);
        holder.txt_recovered_st.setText(recoveredCases);
        holder.txt_deaths_st.setText(deathCases);
        holder.txt_state.setText(districtName);
    }

    @Override
    public int getItemCount() {
        return districts.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView txt_confirmed_st, txt_active_st, txt_recovered_st, txt_deaths_st, txt_state, txt_updated_stt;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_confirmed_st=itemView.findViewById(R.id.txt_confirmed_st);
            txt_active_st=itemView.findViewById(R.id.txt_active_st);
            txt_recovered_st=itemView.findViewById(R.id.txt_recovered_st);
            txt_deaths_st=itemView.findViewById(R.id.txt_deaths_st);
            txt_state=itemView.findViewById(R.id.txt_state);

        }
    }
}
