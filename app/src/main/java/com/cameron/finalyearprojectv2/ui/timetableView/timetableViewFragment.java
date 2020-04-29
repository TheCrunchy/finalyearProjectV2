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

import com.cameron.finalyearprojectv2.DateForSpinner;
import com.cameron.finalyearprojectv2.MainActivity;
import com.cameron.finalyearprojectv2.R;
import com.cameron.finalyearprojectv2.TimeTable;
import com.cameron.finalyearprojectv2.UserData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
        ArrayList<DateForSpinner> keepWeeks = new ArrayList<>();
        if (timeTable.size() < 1){
            timeTable.add(new TimeTable("No Data to display", "Add events using the 'Edit Timetable' option in the menu", Calendar.getInstance()));
        }
        keepWeeks.add(new DateForSpinner(timeTable.get(0).getDateTime()));


        //get the first date in the data and the last date in the data
        Calendar start = timeTable.get(0).getDateTime();
        Calendar end = timeTable.get(timeTable.size() - 1).getDateTime();

        //convert them to useful objects
        LocalDate startD = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate stopD = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<LocalDate> mondays = new ArrayList<> ();
        //Useful localdate stuff to get dates inbetween the 2 used above
        LocalDate nextOrSameMonday = startD.with ( TemporalAdjusters.nextOrSame ( DayOfWeek.MONDAY ) );


        //in the future rewrite the timetable data to always save the first day of that week so i dont need to do this stuff
        while ( ( null != nextOrSameMonday ) & (  ! nextOrSameMonday.isAfter ( stopD ) ) ) {
            mondays.add ( nextOrSameMonday );  //
            nextOrSameMonday = nextOrSameMonday.plusWeeks ( 1 );
        }

        //remove dates that arent in the same weeks as events in the data
        for (int counter1 = 0; counter1 < timeTable.size(); counter1++) {
            for (int counter2 = 0; counter2 < mondays.size(); counter2++) {
                if (isWeekSameLocalDate(mondays.get(counter2), ( timeTable.get(counter1)).getDateTime()) && !keepWeeks.contains(new DateForSpinner((timeTable.get(counter1)).getDateTime()))) {
                    keepWeeks.add(new DateForSpinner((timeTable.get(counter1)).getDateTime()));
                }
            }
        }
        //populate the spinner dropdown with the users weeks with data
        ArrayAdapter<DateForSpinner> adapter = new ArrayAdapter<DateForSpinner>(this.getContext(), android.R.layout.simple_spinner_item, keepWeeks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = root.findViewById(R.id.spinnerForSelectingWeek);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //populate the scrollview when the user picks from the dropdown spinner, default will always be the first week though i could change that to be the current week.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DateForSpinner datad = (DateForSpinner) parent.getSelectedItem();
                TableLayout tableForTimeTable = (TableLayout) root.findViewById(R.id.tableForTimeTable);
                tableForTimeTable.removeAllViews();
                for (int counter1 = 0; counter1 < timeTable.size(); counter1++) {
                    if (isWeekSameLocalDate((timeTable.get(counter1).getDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()), datad.getDateTime())) {
                        //loop through them all and check if same week
                        ArrayList<TimeTable> timeTable2 = data.getTimeTable();
                        TimeTable currentData = timeTable2.get(counter1);
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


    //Use this to remove all the weeks from the mondays that dont have anything entered by the user, also used to check
    //if 2 dates are in the same week when adding to the scrollview
    public static boolean isWeekSameLocalDate(LocalDate firstDate, Calendar cal) {
        LocalDate secondDate = cal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        WeekFields weeks = WeekFields.of(Locale.getDefault());
        int firstDatesCalendarWeek = firstDate.get(weeks.weekOfWeekBasedYear());
        int secondDatesCalendarWeek = secondDate.get(weeks.weekOfWeekBasedYear());
        int firstWeek = firstDate.get(weeks.weekBasedYear());
        int secondWeek = secondDate.get(weeks.weekBasedYear());
        return firstDatesCalendarWeek == secondDatesCalendarWeek
                && firstWeek == secondWeek;
    }
}