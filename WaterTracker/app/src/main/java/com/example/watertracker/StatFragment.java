package com.example.watertracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class StatFragment extends Fragment {
    private static final String FILE_NAME = "day";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stat, container, false);

        setData();
        WeeklyView(v);



        return v;
    }

    public ArrayList getData(boolean isWeekly) {
        // if we pass true to getting data as is weekly. We do this statement.
        ArrayList  response = new ArrayList();
        int i = 1;
        int j = 0;
        FileInputStream fis = null;

        if(isWeekly) {

            while (i < 8) {
                try {
                    fis = getActivity().openFileInput(FILE_NAME + i + ".txt");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String text;

                    while ((text = br.readLine()) != null) {
                        sb.append(text);
                    }
                    System.out.println("DATA " + i + "is : " + Integer.parseInt(sb.toString()));
                    // add to our ArrayList to be returned.
                    response.add(new BarEntry(Integer.parseInt(sb.toString()), j));

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
                j++;
            }

        }
            return response;
    }

    public void setData() {
        int i = 1;
        String text  = "400";
        FileOutputStream fos = null;

        while(i < 8 ) {


            try {
                fos = getActivity().openFileOutput(FILE_NAME + i+".txt", MODE_PRIVATE);
                fos.write(text.getBytes());
                System.out.println("File printed " + i + " <- the number");
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

    public void WeeklyView(View v) {
        // this will be our x-axis.
        ArrayList days = new ArrayList();

        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        // our y-axis will consist of data drawn from the user.
        ArrayList intakeOfWater = new ArrayList();
        intakeOfWater = getData(true);
        System.out.println("intake of water is " + intakeOfWater.get(0).toString());


        // set to the chart
        BarChart chart = v.findViewById(R.id.barchart);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(true);

        BarDataSet bardataset = new BarDataSet(intakeOfWater, "Amount of Water Intake");
        chart.animateY(5000);
        BarData data = new BarData(days, bardataset);
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        chart.setData(data);


    }


}
