package com.example.watertracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    EditText goalValue;
    int max;
    private static final String FILE_NAMEPROGRESS="ProgressMaxValue.txt";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Button setGoal = (Button) v.findViewById(R.id.SetBtn);
        goalValue = (EditText) v.findViewById(R.id.goalText);




       setGoal.setOnClickListener(new View.OnClickListener(){
         @Override
           public void onClick(View view){
             Editable newProgress = goalValue.getEditableText();

             try {
                 max = (int) Integer.parseInt(String.valueOf(newProgress));
                 if (max > 0) {
                     saveProgress(v);
                     Toast.makeText(getActivity(), "Goal has been changed", Toast.LENGTH_LONG).show();
                     Intent backToHome = new Intent(getActivity(), MainActivity.class);
                     startActivity(backToHome);
                 } else {
                     AlertDialog alertDialog= new AlertDialog.Builder(getActivity()).create();
                     alertDialog.setTitle("Can't enter a negative number");
                     alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                             new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                 }
                             });
                     alertDialog.show();

                 }
             } catch (NumberFormatException ex) {
                 ex.printStackTrace();
                 // Alert
                 AlertDialog alertDialog= new AlertDialog.Builder(getActivity()).create();
                 alertDialog.setTitle("Enter a valid number");
                 alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                             }
                         });
                 alertDialog.show();
             } finally {
                 try {
                     InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                     imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                 } catch (Exception e) {
                     // TODO: handle exception
                 }
             }


         }

        });
       

        return v;
    }
    public void saveProgress(View v) {
        String text = Integer.toString(max);
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(FILE_NAMEPROGRESS, MODE_PRIVATE);
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
}
