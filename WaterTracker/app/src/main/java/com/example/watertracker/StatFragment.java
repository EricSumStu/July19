package com.example.watertracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class StatFragment extends Fragment {
    Calendar calendar = Calendar.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stat, container, false);
//        save(v);
        weeklyView(v);
        //
        return v;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public void save(View v) {      // first we will save to file
        int this_weeknumber = calendar.get(Calendar.WEEK_OF_YEAR);


        FileOutputStream fos = null;
        int i = 0;
        while (i < 4) {

            String text = Integer.toString(getRandomNumberInRange(1000,2000));

            try {
                fos = getActivity().openFileOutput("day" + i+"-"+this_weeknumber + ".txt", MODE_PRIVATE);

                fos.write(text.getBytes());
                System.out.println("PRINTING :" + i+ " dataaaa");
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
            i++;
        }

    }


    public ArrayList load(boolean isWeek) {
        FileInputStream fis = null;
        ArrayList xAxisData = new ArrayList();
        int i = 0;
        int this_weeknumber = calendar.get(Calendar.WEEK_OF_YEAR);

        if(isWeek) {
            while (i < 7) {

                try {

                    fis = getActivity().openFileInput("day" + i + "-" + this_weeknumber + ".txt");
                    System.out.println("day" + i + "-" + this_weeknumber + ".txt");
                    System.out.println(fis);
                    InputStreamReader isr = new InputStreamReader(fis);

                    BufferedReader br = new BufferedReader(isr);

                    StringBuilder sb = new StringBuilder();

                    String text;

                    // While loop must have condition.
                    // while( condition here) { code here }
                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                    }

                    System.out.println("DATA IS PUT IN: " + Integer.parseInt(sb.toString()));
                    xAxisData.add(new BarEntry(Integer.parseInt(sb.toString()), i));

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
                i++;
            }
        }
        return xAxisData;
}
public void weeklyView(View v) {
        ArrayList week = new ArrayList();

        week.add("Sunday");
        week.add("Monday");
        week.add("Tuesday");
        week.add("Wednesday");
        week.add("Thursday");
        week.add("Friday");
        week.add("Saturday");


        ArrayList intakeOfWater = new ArrayList();
        intakeOfWater = load(true);

    BarChart chart = v.findViewById(R.id.barchart);
    BarDataSet bardataset = new BarDataSet(intakeOfWater, "Amount of Water");
    chart.animateY(5000);
    BarData data = new BarData(week,bardataset);
    bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
    chart.setData(data);

}
}