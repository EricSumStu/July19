package com.example.watertracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private int counter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        counter = 0;
        ImageButton bottleImageButton = (ImageButton) getView().findViewById(R.id.bottlebtn);
        ImageButton glassImageButton = (ImageButton) getView().findViewById(R.id.glassbtn);
        final TextView mTextView = (TextView) getView().findViewById(R.id.countertext);
        final ProgressBar waterTracker = (ProgressBar) getView().findViewById(R.id.waterCounter);
        waterTracker.setMax(3000);
        mTextView.setText("Total ml: " + counter);

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
            }
        });


        return v;
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

}
