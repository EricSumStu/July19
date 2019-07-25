package com.example.watertracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmet_about, container, false);
        final ImageButton factsbtn = (ImageButton) v.findViewById(R.id.waterFactsBtn);
        final Animation dropAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        final Animation peopleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        dropAnimation.setInterpolator(interpolator);
        factsbtn.setAnimation(dropAnimation);

        final ImageButton credits = (ImageButton) v.findViewById(R.id.creditsbtn);
        peopleAnimation.setInterpolator(interpolator);
        credits.setAnimation(peopleAnimation);
        factsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                factsbtn.startAnimation(dropAnimation);



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(), waterFacts.class);
                        getActivity().startActivity(mainIntent);

                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        });
        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credits.startAnimation(peopleAnimation);



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(), creditsPage.class);
                        getActivity().startActivity(mainIntent);

                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        });

        return v;

    }
}

