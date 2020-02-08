package com.cameron.finalyearprojectv2.ui.home;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String day = LocalDate.now().getDayOfWeek().name();
    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private View root;
    private String date = simpleDateFormat.format(new Date());

    private TextView textViewClosestDeadlineLabel;
    private boolean timerRunning;
    private static long START_TIME_IN_MILLIS = 0;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer CountDownTimer;


    private TableLayout ll;
    private Calendar calendar;
    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }


    //start the timer for the thing, pass it a calendar to use then calculate the time between the calendar and local time, then start the timer
    private void startTimer(Calendar cal) {
        Calendar localTime = Calendar.getInstance();
        START_TIME_IN_MILLIS = (cal.getTimeInMillis() - localTime.getTimeInMillis() );

        System.out.println(timeLeftInMillis);
        System.out.println(START_TIME_IN_MILLIS);
        CountDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDown();
            }
            @Override
            public void onFinish() {
                timerRunning = false;
            }

        }.start();

        timerRunning = true;
    }

    private void updateCountDown() {

        long days = TimeUnit.MILLISECONDS.toDays(timeLeftInMillis);
        timeLeftInMillis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(timeLeftInMillis);
        timeLeftInMillis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftInMillis);
        timeLeftInMillis -= TimeUnit.MINUTES.toMillis(minutes);

       long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftInMillis);
        String timeLeftFormatted = String.format(days + "d " + hours + "h " + minutes + "m " + seconds + "s" );


        textViewClosestDeadlineLabel = root.findViewById(R.id.textViewClosestDeadlineLabel);
        textViewClosestDeadlineLabel.setText(timeLeftFormatted);
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

        //get the user data
        UserData data = MainActivity.getData();

        //Make the day of the week not all caps
        day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

        //System.out.println("GOALS " + data.getGoals().getAllGoals().size());

        int goalsToday = 0;
        int goalsThisWeek = 0;
        ArrayList<Goal> goals = data.getGoals();
       // closestDeadline.setText("0" + "");
        Calendar cal1 = Calendar.getInstance();
        ll = (TableLayout) root.findViewById(R.id.tableForGoals);
        TableLayout table = (TableLayout) root.findViewById(R.id.tableForGoals);



        boolean timerStarted = false;
        for (int counter = 0; counter < goals.size(); counter++) {
            Goal goal = goals.get(counter);
            Calendar subGoalDeadline = goal.getDateTime();
            String goalTitle = goal.getTitle();
            String subGoal = goal.getSubGoal();


            System.out.println("CALENDARS " + "\n" + cal1.getTime() + "\n" + subGoalDeadline.getTime());
            boolean sameDay = isDateSame(cal1, subGoalDeadline);
            if (sameDay){
                goalsToday++;
            }

            calendar = Calendar.getInstance();
            int week1 = calendar.get(Calendar.WEEK_OF_YEAR);

            Date date2 = null;
            date2 = subGoalDeadline.getTime();

            if (date2 != null) {
                int week2;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date2);
                week2 = calendar.get(Calendar.WEEK_OF_YEAR);
                if (week1 == week2) {
                    goalsThisWeek++;
                }
            }

            if (goal.isComplete()){
            }
            else {
                if (!timerStarted){
                    startTimer(subGoalDeadline);
                    timerStarted = true;
                    System.out.println("GOAL " + goal.getTitle());
                    System.out.println("SUBGOAL " + subGoalDeadline.getTime());
                }
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



                textGoal.setText(goalTitle + "\n" + subGoal + "\n" + " Deadline: "
                        + "" + subGoalDeadline.getTime() +
                        "\n");
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
