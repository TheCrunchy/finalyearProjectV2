package com.cameron.finalyearprojectv2.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private static long startTimeMillis = 0;
    private long timeLeftInMillis = startTimeMillis;
    private CountDownTimer CountDownTimer;
    private Calendar nearestGoalCalendar;
    private TableLayout ll;
    private Calendar calendar;
    private int goalPosition = 0;
    private int startPosition = 0;
    private ArrayList<Goal> goals;

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

        int goalsToday = 0;
        int goalsThisWeek = 0;
        goals = data.getGoals();
        Calendar cal1 = Calendar.getInstance();


        boolean timerStarted = false;
        for (int counter = 0; counter < goals.size(); counter++) {
            Goal goal = goals.get(counter);
            Calendar subGoalDeadline = goal.getDateTime();

            //Add to the int if the day is the same for the label later
            boolean sameDay = isDateSame(cal1, subGoalDeadline);
            if (sameDay) {
                goalsToday++;
            }

            //now check if the goal is in the same week
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

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 23);
            cal.set(Calendar.MINUTE, 59);
            if (subGoalDeadline.before(cal) && !isDateSame(subGoalDeadline, cal)) {
            } else if (!timerStarted) {
                startTimer(subGoalDeadline);
                nearestGoalCalendar = subGoalDeadline;
                goalPosition = counter;
                startPosition = counter;
                timerStarted = true;
            }

        }

        //populate the table for the goals and highlight the selected goal
        fillGoals();

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
        //Check if the timer is null before allowing the buttons to work
        Button next = root.findViewById(R.id.btnNext);
        if (CountDownTimer != null) {
            next.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CountDownTimer.cancel();
                    nextGoal();
                }
            });
        }

        //Check if the timer is null before allowing the buttons to work
        Button previous = root.findViewById(R.id.btnPrevious);
        if (CountDownTimer != null) {
            previous.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CountDownTimer.cancel();
                    previousGoal();
                }
            });
        }
        return root;
    }

    public void nextGoal() {
        //Dont think this null check does anything but it doesnt hurt to have
        if (goals == null) {
            return;
        }

        //check if the goal in the next position exists
        if (goals.size() > goalPosition + 1) {
            goalPosition += 1;
        }
        fillGoals();
        CountDownTimer.cancel();
        startTimer(goals.get(goalPosition).getDateTime());
    }

    public void previousGoal() {
        //Dont think this null check does anything but it doesnt hurt to have
        if (goals == null) {
            return;
        }

        //check if the goal in the previous position exists
        if (goals.size() > (goalPosition - 1)) {
            goalPosition -= 1;
            if (goalPosition < startPosition) {
                goalPosition = startPosition;
            }
        }
        fillGoals();
        CountDownTimer.cancel();
        startTimer(goals.get(goalPosition).getDateTime());
    }

    //populate the goals scrollview
    public void fillGoals() {
        TableLayout table = (TableLayout) root.findViewById(R.id.tableForGoals);
        table.removeAllViews();
        for (int counter = 0; counter < goals.size(); counter++) {
            Goal goal = goals.get(counter);
            // ll = (TableLayout) root.findViewById(R.id.tableForGoals);
            Calendar subGoalDeadline = goal.getDateTime();
            String goalTitle = goal.getTitle();
            String subGoal = goal.getSubGoal();
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
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR, 23);
            cal.set(Calendar.MINUTE, 59);


            //if the goal is complete make its colour green and bold
            if (goal.isComplete()) {
                textGoal.setTextColor(Color.GREEN);
                textGoal.setTypeface(null, Typeface.BOLD);
            }
            //make the currently selected goal italic and bold
            if (counter == goalPosition) {
                textGoal.setTypeface(null, Typeface.BOLD_ITALIC);
            } else {
                //if the deadline is passed make the colour red
                if (subGoalDeadline.before(cal) && !isDateSame(subGoalDeadline, cal)) {
                    textGoal.setTextColor(Color.RED);

                }
            }
            //Add the new textview to the table in the scrollview
            tableRow.addView(textGoal);
            table.addView(tableRow);

        }
    }

    //i would make this a public static but each use is slightly different
    private boolean isDateSame(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }


    //start the timer for the time until deadline timer, pass it a calendar to use then calculate the time between the calendar and local time, then start the timer
    private void startTimer(Calendar cal) {
        nearestGoalCalendar = cal;
        Calendar localTime = Calendar.getInstance();
        startTimeMillis = (cal.getTimeInMillis() - localTime.getTimeInMillis());

        //pass the startTimeMillis for when the timer should start and an interval for how often to update
        CountDownTimer = new CountDownTimer(startTimeMillis, 1000) {

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


    //Set the timer label to a formatted string
    private void updateCountDown() {

        long days = TimeUnit.MILLISECONDS.toDays(timeLeftInMillis);
        timeLeftInMillis -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(timeLeftInMillis);
        timeLeftInMillis -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftInMillis);
        timeLeftInMillis -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftInMillis);
        String timeLeftFormatted = String.format(days + "d " + hours + "h " + minutes + "m " + seconds + "s");

        textViewClosestDeadlineLabel = root.findViewById(R.id.textViewClosestDeadlineLabel);
        textViewClosestDeadlineLabel.setText(timeLeftFormatted);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (nearestGoalCalendar != null) {
            updateCountDown();
            //Dont think this is needed anymore, it updates the time on the timer when the user re-enters the page
        }
    }
}
