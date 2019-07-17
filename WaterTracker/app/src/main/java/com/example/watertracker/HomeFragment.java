package com.example.watertracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

                if(counter < 3000) {
                    counter = counter + 500;
                }
                else{
                    showMaxWarning();
                }if(counter >= 3000){
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

                if(counter < 3000) {
                    counter = counter + 200;
                }
                else{
                    showMaxWarning();
                }
                if(counter >= 3000){
                    showMaxWarning();
                }
                mTextView.setText("Total ml: " + counter);
                waterTracker.setProgress(counter);
                save(v);
            }
        });


        return v;
    }

    public void reset(){
        counter = 0;
        mTextView.setText("Total ml: " + counter);
        waterTracker.setProgress(counter);
        save(v);

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

    public void log() throws FileNotFoundException {
        Calendar calendar = Calendar.getInstance();

        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        int week_of_year = calendar.get(Calendar.WEEK_OF_YEAR);

        final String FILE_NAME = "day" + day_of_week + "-" + week_of_year + ".txt";

        String text = Integer.toString(counter);
        FileOutputStream fos = new FileOutputStream(FILE_NAME,true);

        try {
            fos = getActivity().openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            Toast.makeText(getActivity(), "Logged",
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
}
