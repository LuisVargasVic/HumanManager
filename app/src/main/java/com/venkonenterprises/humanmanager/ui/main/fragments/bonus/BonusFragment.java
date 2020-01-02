package com.venkonenterprises.humanmanager.ui.main.fragments.bonus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.venkonenterprises.humanmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BonusFragment extends Fragment {


    public BonusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bonus, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_bonus);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new BonusAdapter(getContext()));


        return view;
    }

}
