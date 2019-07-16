package com.example.watertracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private int counter;
    private static final String FILE_NAME = "waterCounter1.txt";
    private TextView mTextView;
    private ProgressBar waterTracker;
    View v;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);


        counter = 0;
        ImageButton bottleImageButton = (ImageButton) v.findViewById(R.id.bottlebtn);
        ImageButton glassImageButton = (ImageButton) v.findViewById(R.id.glassbtn);
        final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        glassImageButton.setAnimation(myAnim);
        ImageButton resetImageButton = (ImageButton) v.findViewById(R.id.resetbtn);
        mTextView = (TextView) v.findViewById(R.id.countertext);
        waterTracker = (ProgressBar) v.findViewById(R.id.waterCounter);
        waterTracker.setMax(3000);
        load(v);
        waterTracker.setProgress(counter);
        mTextView.setText("Total ml: " + counter);

        resetImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        bottleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (counter < 3000) {
                    counter = counter + 500;
                    random();
                } else {
                    showMaxWarning();
                }
                if (counter >= 3000) {
                    showMaxWarning();
                }
                mTextView.setText("Total ml: " + counter);
                waterTracker.setProgress(counter);
                save(v);
            }
        });

        glassImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton glassImageButton = v.findViewById(R.id.glassbtn);
                glassImageButton.startAnimation(myAnim);
                if (counter < 3000) {
                    counter = counter + 200;
                   // random();
                } else {
                    showMaxWarning();
                }
                if (counter >= 3000) {
                    showMaxWarning();
                }
                mTextView.setText("Total ml: " + counter);
                waterTracker.setProgress(counter);
                save(v);


            }
        });


        return v;
    }

    public void reset() {
        counter = 0;
        mTextView.setText("Total ml: " + counter);
        waterTracker.setProgress(counter);
        save(v);

    }

    public void showMaxWarning() {
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

    public void random() {
        String[] arr = {"is your ass jealous of the amount of shit that just came out of your mouth", "Your family tree must be a cactus because everyone in your family is a prick", "The only way you'll get laid is if you crawl up a chicken's ass and wait", "Well done", "Keep drinking", "You're doing great sweetie", "Yaaaassssssssss QUEEN", "I'm so proud of you.I just wanted to tell you in case no one has", "It doesn't matter how slow you go, as long as you don't stop..", "A little progress each day adds up to big results"};
        Random r = new Random();
        int randomMessage = r.nextInt(arr.length);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle(arr[randomMessage]);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Thanks",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void save(View v) {
        String text = Integer.toString(counter);
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            Toast.makeText(getActivity(), "Saved",
                    Toast.LENGTH_LONG).show();
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
        FileInputStream fis = null;

        try {
            fis = getActivity().openFileInput(FILE_NAME);
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
}
