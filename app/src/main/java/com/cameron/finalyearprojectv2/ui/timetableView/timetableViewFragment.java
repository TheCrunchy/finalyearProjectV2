package com.cameron.finalyearprojectv2.ui.timetableView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cameron.finalyearprojectv2.MainActivity;
import com.cameron.finalyearprojectv2.R;
import com.cameron.finalyearprojectv2.TimeTable;
import com.cameron.finalyearprojectv2.UserData;
import com.cameron.finalyearprojectv2.ui.DateForSpinner;

import java.util.ArrayList;
import java.util.Calendar;

public class timetableViewFragment extends Fragment {

    private timetableViewViewModel timetableViewViewModel;
    private Spinner spinner;
    private UserData data;
    ArrayList<TimeTable> timeTable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timetableViewViewModel =
                ViewModelProviders.of(this).get(timetableViewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);

        timetableViewViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        //get the user data
        data = MainActivity.getData();
        timeTable = data.getTimeTable();
        ArrayList<DateForSpinner> temporaryWeeks = new ArrayList<>();
        ArrayList<DateForSpinner> keepWeeks = new ArrayList<>();
        //Rewrite this to remove the mondays that dont have a user added event
        temporaryWeeks.add(new DateForSpinner(Calendar.getInstance()));
        for (int counter1 = 0; counter1 < timeTable.size(); counter1++) {
            temporaryWeeks.add(new DateForSpinner(timeTable.get(counter1).getDateTime()));

                if (!isDateInCurrentWeek(temporaryWeeks.get(counter1).getDateTime(), timeTable.get(counter1).getDateTime())) {
                    if (!keepWeeks.contains(new DateForSpinner(timeTable.get(counter1).getDateTime()))) {
                        keepWeeks.add(new DateForSpinner(timeTable.get(counter1).getDateTime()));
                    }
                }
            }
        //sort these by date
        keepWeeks.add(new DateForSpinner(timeTable.get(0).getDateTime()));

        ArrayAdapter<DateForSpinner> adapter = new ArrayAdapter<DateForSpinner>(this.getContext(), android.R.layout.simple_spinner_item, keepWeeks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = root.findViewById(R.id.spinnerForSelectingWeek);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DateForSpinner datad = (DateForSpinner) parent.getSelectedItem();
                TableLayout tableForTimeTable = (TableLayout) root.findViewById(R.id.tableForTimeTable);
                tableForTimeTable.removeAllViews();
                for (int counter1 = 0; counter1 < timeTable.size(); counter1++) {
                    //System.out.println("IN LOOP " + counter1 + " " + timeTable.get(counter1).getDateTime().getTime());
                    if (isWeekSame(timeTable.get(counter1).getDateTime(), datad.getDateTime())) {
                        //loop through them all and check if same week
                        TimeTable currentData = timeTable.get(counter1);

                        TableRow tableRow = new TableRow(root.getContext());
                        tableRow.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.MATCH_PARENT,
                                1.0f));
                        TextView textTimeTable = new TextView(root.getContext());
                        textTimeTable.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT,
                                1.0f));
                        textTimeTable.setGravity(Gravity.CENTER);
                        textTimeTable.setBottom(10);

                        textTimeTable.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.MATCH_PARENT,
                                TableRow.LayoutParams.MATCH_PARENT,
                                1.0f));
                        textTimeTable.setText(currentData.getTitle() + "\n" + currentData.getDetails() + "\n" + currentData.getDateTime().getTime() + "\n");
                        textTimeTable.setGravity(Gravity.CENTER);
                        textTimeTable.setBottom(10);
                        tableRow.addView(textTimeTable);
                        tableForTimeTable.addView(tableRow);
                        //populate the table
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(adapter);

        return root;
    }
    public boolean isDateInCurrentWeek(Calendar cal1, Calendar cal2) {

        Calendar firstTarget = cal2;
        int week = firstTarget.get(Calendar.WEEK_OF_YEAR);
        int year = firstTarget.get(Calendar.YEAR);
        Calendar secondTarget = cal1;
        int targetWeek = secondTarget.get(Calendar.WEEK_OF_YEAR);
        int targetYear = secondTarget.get(Calendar.YEAR);

        boolean belongs = (week == targetWeek && year == targetYear);
        System.out.println(belongs);
        return belongs;
    }
    //i would make this a public static but each use is slightly different
    private boolean isWeekSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR));
    }

}