package com.cameron.finalyearprojectv2.ui.daily;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Calendar;

public class DailyFragment extends Fragment {

    private DailyViewModel dailyViewModel;
    private TableLayout tableForTimeTable;
    private Calendar calendar;
    private int previousWeek;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyViewModel =
                ViewModelProviders.of(this).get(DailyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timetable, container, false);

        dailyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        //get the user data to get the timetable data
        UserData data = MainActivity.getData();
        ArrayList<TimeTable> timeTable = data.getTimeTable();
        previousWeek = Calendar.getInstance().getWeekYear() - 1;
        for (int counter = 0; counter < timeTable.size(); counter++) {
            if (timeTable.get(counter).getDateTime().getTime().getYear() >= Calendar.getInstance().getTime().getYear()) {

                TableLayout tableForTimeTable = (TableLayout) root.findViewById(R.id.tableForTimeTable);

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

                TimeTable currentData = timeTable.get(counter);

                textTimeTable.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                textTimeTable.setText(currentData.getTitle() + "\n" + currentData.getDetails() + "\n" + currentData.getDateTime().getTime() + "\n");
                textTimeTable.setGravity(Gravity.CENTER);
                textTimeTable.setBottom(10);
                tableRow.addView(textTimeTable);
                tableForTimeTable.addView(tableRow);
            }
        }
        return root;
    }

    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

}