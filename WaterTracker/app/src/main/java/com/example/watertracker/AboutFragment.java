package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmet_about, container, false);

        ImageButton factsbtn = (ImageButton) v.findViewById(R.id.waterFactsBtn);
        ImageButton credits = (ImageButton) v.findViewById(R.id.creditsbtn);
        factsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), waterFacts.class);

                startActivity(intent1);
            }
        });
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), creditsPage.class);

                startActivity(intent2);
            }
        });

        return v;

    }
}
