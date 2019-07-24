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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
    Spinner sp ;
    String names[] = {"2 Litres","2.5 Litres","3 Litres","3.5 Litres","4 Litres"};
    ArrayAdapter<String> adapter;
    String record;
    double value;
    private static final String FILE_NAMEPROGRESS="ProgressMaxValue.txt";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Button setGoal = (Button) v.findViewById(R.id.SetBtn);

        //Spinner bits

        sp = (Spinner) v.findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,names);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //use postion value

                switch (position) {

                    case 0:
                        
                        value = Double.parseDouble("2");

                        break;

                    case 1:


                        value = Double.parseDouble("2.5");

                        break;

                    case 2:

                        value = Double.parseDouble("3");

                        break;
                    case 3:

                        value = Double.parseDouble("3.5");

                        break;
                    case 4:

                        value = Double.parseDouble("4");

                        break;
                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        goalValue = (EditText) v.findViewById(R.id.goalText);




        setGoal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                max = (int) (value * 1000);
                saveProgress(v);
                Toast.makeText(getActivity(), "Goal has been changed", Toast.LENGTH_LONG).show();

                Intent backHome = new Intent(getActivity(),MainActivity.class);

                startActivity(backHome);

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

