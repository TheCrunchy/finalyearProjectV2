package com.cameron.finalyearprojectv2.ui.home;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import com.cameron.finalyearprojectv2.UserData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String day = LocalDate.now().getDayOfWeek().name();
    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    View root;
    String date = simpleDateFormat.format(new Date());
    TableLayout ll;
    Calendar calendar;
    String time;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        //Declare the textviews so they can be used
        final TextView textDay = root.findViewById(R.id.dayOfWeekTextView);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView deadlinesToday = root.findViewById(R.id.textViewDeadlinesToday);
        final TextView deadlinesThisWeek = root.findViewById(R.id.textViewDeadlinesThisWeek);
        final TextView closestDeadline = root.findViewById(R.id.textViewClosestDeadlineLabel);

        //get the user data
        UserData data = MainActivity.getData();

        //Make the day of the week not all caps
        day = day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();

        //System.out.println("GOALS " + data.getGoals().getAllGoals().size());

        int goalsToday = 0;
        int goalsThisWeek = 0;
        HashMap<String, HashMap<String, String>> allGoals = data.getGoals().getAllGoals();
        closestDeadline.setText("0" + "");

        ll = (TableLayout) root.findViewById(R.id.tableForGoals);
        TableLayout table = (TableLayout) root.findViewById(R.id.tableForGoals);

       // ll.setVerticalScrollBarEnabled(true);
        //Should probably make this one loop with the above one
        for (Map.Entry<String, HashMap<String, String>> entry : allGoals.entrySet()) {
            String goalKey = entry.getKey();
            HashMap<String, String> value = entry.getValue();
            for (Map.Entry<String, String> newEntry : value.entrySet()) {

                String subGoalKey = newEntry.getKey();
                String subGoalDeadline = newEntry.getValue();
                //System.out.println(subGoalDeadline);
                String[]temp = subGoalDeadline.split("==");
                time = temp[1];
                    if (temp[0].equals(date)){
                        goalsToday++;
                    }

                //  System.out.println("Current goal : " + key);
                // System.out.println("Current date : " + date);
                //System.out.println("Current deadline : " + value);

                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                int week1 = calendar.get(Calendar.WEEK_OF_YEAR);

                SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                Date date2 = null;
                try {
                    date2 = formatter.parse(subGoalDeadline);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date2 != null){
                    int week2;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date2);
                    week2 = calendar.get(Calendar.WEEK_OF_YEAR);
                    System.out.println("WEEKS " + week1 + " " + week2);
                    if (week1 == week2){
                        goalsThisWeek ++;
                    }
                }

                TableRow tableRow = new TableRow(root.getContext());
                tableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        0f));
                TextView textGoal = new TextView(root.getContext());
                textGoal.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        0f));
                textGoal.setText(goalKey + "\n" + subGoalKey);
                textGoal.setGravity(Gravity.CENTER);
                textGoal.setBottom(10);
                textGoal.setWidth(520);
                tableRow.addView(textGoal);
                table.addView(tableRow);

                TextView text2 = new TextView(root.getContext());
                text2.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        0.0f));
                text2.setMovementMethod(new ScrollingMovementMethod());
                text2.setText(date);
                text2.setWidth(520);
                tableRow.addView(text2);

            }
        }


//        int i = 0;
//        for (Map.Entry<String, String> entry : allGoals.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            TableRow row = new TableRow(root.getContext());
//            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
//            row.setLayoutParams(lp);
//            TextView tv = new TextView(root.getContext());
//            tv.setText(key);
//            row.addView(tv);
//           // tv.setText(value);
//            //row.addView(tv);
//            i++;
//            ll.addView(row, i);
//        }
        if (data != null && data.getGoals() != null) {
            deadlinesToday.setText(goalsToday + "");
            deadlinesThisWeek.setText(goalsThisWeek + "");
        }
        else {
            deadlinesToday.setText("0");
            deadlinesThisWeek.setText("0");
        }


        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(date);
                textDay.setText(day);
            }
        });
        return root;
    }


}