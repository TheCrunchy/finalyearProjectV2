package com.cameron.finalyearprojectv2.ui.home;

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

import com.cameron.finalyearprojectv2.Goal;
import com.cameron.finalyearprojectv2.MainActivity;
import com.cameron.finalyearprojectv2.R;
import com.cameron.finalyearprojectv2.UserData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String day = LocalDate.now().getDayOfWeek().name();
    String pattern = "dd/MM/yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    View root;
    String date = simpleDateFormat.format(new Date());

    TableLayout ll;
    Calendar calendar;
    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }

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
        day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

        //System.out.println("GOALS " + data.getGoals().getAllGoals().size());

        int goalsToday = 0;
        int goalsThisWeek = 0;
        ArrayList<Goal> goals = data.getGoals();
        closestDeadline.setText("0" + "");

        ll = (TableLayout) root.findViewById(R.id.tableForGoals);
        TableLayout table = (TableLayout) root.findViewById(R.id.tableForGoals);
        for (int counter = 0; counter < goals.size(); counter++) {
            Goal goal = goals.get(counter);
            Date subGoalDeadline = goal.getDateTime();
            String goalTitle = goal.getTitle();
            String subGoal = goal.getSubGoal();
          //  System.out.println("DATE " + subGoalDeadline);
           // System.out.println("DATE " + compareDate);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(subGoalDeadline);
            System.out.println("CALENDARS " + "\n" + cal1.getTime() + "\n" + cal2.getTime());
            boolean sameDay = isDateSame(cal1, cal2);
            if (sameDay){
                goalsToday++;
            }

            calendar = Calendar.getInstance();
            int week1 = calendar.get(Calendar.WEEK_OF_YEAR);

            Date date2 = null;
            date2 = subGoalDeadline;

            if (date2 != null) {
                int week2;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date2);
                week2 = calendar.get(Calendar.WEEK_OF_YEAR);
                if (week1 == week2) {
                    goalsThisWeek++;
                }
            }
            if (cal2.before(cal1)){
                break;
            }
            else {
                TableRow tableRow = new TableRow(root.getContext());
                tableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f));
                TextView textGoal = new TextView(root.getContext());
                textGoal.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));


                String month = null, day = null, hour = null, minute = null;


                //format the strings so they look nice, there is probably a better way to do this
                if (String.valueOf(subGoalDeadline.getDate()).length() == 1) {
                    day = "0" + subGoalDeadline.getDate();
                } else {
                    day = String.valueOf(subGoalDeadline.getDate());
                }
                if (String.valueOf(subGoalDeadline.getMonth()).length() == 1) {
                    month = "0" + subGoalDeadline.getMonth();
                } else {
                    month = String.valueOf(subGoalDeadline.getMonth());
                }
                if (String.valueOf(subGoalDeadline.getHours()).length() == 1) {
                    hour = "0" + subGoalDeadline.getHours();
                } else {
                    hour = String.valueOf(subGoalDeadline.getHours());
                }
                if (String.valueOf(subGoalDeadline.getMinutes()).length() == 1) {
                    minute = "0" + subGoalDeadline.getMinutes();
                } else {
                    minute = String.valueOf(subGoalDeadline.getMinutes());
                }

                textGoal.setText(goalTitle + "\n" + subGoal + "\n" + " Deadline: "
                        + day +
                        "/" + month +
                        "/" + subGoalDeadline.getYear() +
                        " " + hour +
                        ":" + minute
                        + "\n");
                textGoal.setGravity(Gravity.CENTER);
                textGoal.setBottom(10);
                //textGoal.setWidth(520);
                tableRow.addView(textGoal);
                table.addView(tableRow);

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
        } else {
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
