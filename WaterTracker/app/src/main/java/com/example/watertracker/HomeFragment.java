package com.example.watertracker;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private int counter;
    private int progressMax;
    private static final String FILE_NAME = "waterCounter1.txt";
    private static final String FILE_NAMEPROGRESS="ProgressMaxValue.txt";

    private TextView GoalTextView;
    private TextView mTextView;
    private ProgressBar waterTracker;

    private boolean showCongrats = true;

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);



        counter = 0;
        progressMax=3000;
        final ImageButton bottleImageButton = (ImageButton) v.findViewById(R.id.bottlebtn);
        final Animation bottleAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        final Animation glassAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bottleAnimation.setInterpolator(interpolator);
        bottleImageButton.setAnimation(bottleAnimation);

        final ImageButton glassImageButton = (ImageButton) v.findViewById(R.id.glassbtn);
        glassAnimation.setInterpolator(interpolator);
        glassImageButton.setAnimation(glassAnimation);
        ImageButton resetImageButton = (ImageButton) v.findViewById(R.id.resetbtn);
        mTextView = (TextView) v.findViewById(R.id.countertext);
        waterTracker = (ProgressBar) v.findViewById(R.id.waterCounter);
        load(v);
        loadProgress(v);
        waterTracker.setMax(progressMax);
        waterTracker.setProgress(counter);
        mTextView.setText("Total ml consumed: " + counter);
//        GoalTextView = (TextView) v.findViewById(R.id.textView8);
//        GoalTextView.setText("My Goal : "+progressMax+"ml");
        resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        bottleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottleImageButton.startAnimation(bottleAnimation);

                counter = counter + 500;
                random();
                if(showCongrats && counter >= progressMax){
                    showMaxWarning();
                    showCongrats = false;
                }

                mTextView.setText("Total ml consumed: " + counter);
                waterTracker.setProgress(counter);
                log(v);

            }
        });

        glassImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glassImageButton.startAnimation(glassAnimation);

                counter = counter + 500;
                random();
                if(showCongrats && counter >= progressMax){
                    showMaxWarning();
                    showCongrats = false;
                }

                mTextView.setText("Total ml consumed: " + counter);
                waterTracker.setProgress(counter);
                log(v);


            }
        });


        return v;
    }

    public void reset(){
        counter = 0;
        mTextView.setText("Total ml consumed: " + counter);
        waterTracker.setProgress(counter);
        log(v);
        showCongrats = true;

    }

    public void showMaxWarning(){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Congrats you've hit your goal for the day");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void random(){
        String[] arr = {"Look at you", "Keep calm and drink water!", "Water is love,Water is life", "Get hydrated man!",  "Well done", "Keep drinking", "You're doing great sweetie", "Yaaaassssssssss QUEEN", "I'm so proud of you.I just wanted to tell you in case no one has", "It doesn't matter how slow you go, as long as you don't stop..", "A little progress each day adds up to big results", "H2-Okurrrr"};
        Random r=new Random();
        int randomMessage=r.nextInt(arr.length);
        Toast.makeText(getActivity(), arr[randomMessage],
                Toast.LENGTH_SHORT).show();


    }

    public void save(View v) {
        String text = Integer.toString(counter);
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View v) {
        Calendar calendar = Calendar.getInstance();

        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR);

        String FILE_NAME_DAY = "day" + day_of_week + "-" + week_of_year + ".txt";

        FileInputStream fis = null;

        try {
            fis = getActivity().openFileInput(FILE_NAME_DAY);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            counter = Integer.parseInt(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void log(View v) {
        Calendar calendar = Calendar.getInstance();

        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR);

        String FILE_NAME_DAY = "day" + day_of_week + "-" + week_of_year + ".txt";
        FileOutputStream fos = null;

        String text = Integer.toString(counter);

            try {
                fos = getActivity().openFileOutput(FILE_NAME_DAY, MODE_PRIVATE);
                fos.write(text.getBytes());
                Toast.makeText(getActivity(), "Logged: " + FILE_NAME_DAY + " with: " + text,
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();

            }

        }



    public void loadProgress(View v) {
        FileInputStream fis = null;

        try {
            fis = getActivity().openFileInput(FILE_NAMEPROGRESS);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            progressMax = Integer.parseInt(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


