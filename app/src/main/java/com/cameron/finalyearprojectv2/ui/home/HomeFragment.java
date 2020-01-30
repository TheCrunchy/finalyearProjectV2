package com.cameron.finalyearprojectv2.ui.home;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textDay = root.findViewById(R.id.dayOfWeekTextView);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView deadlinesToday = root.findViewById(R.id.textViewDeadlinesToday);
        final TextView deadlinesThisWeek = root.findViewById(R.id.textViewDeadlinesThisWeek);
        UserData data = MainActivity.getData();
        System.out.println("GOALS " + data.getGoals().getAllGoals().size());

        int goalsToday = 0;
        int goalsThisWeek = 0;
        HashMap<String, String> allGoals = data.getGoals().getAllGoals();

        for (Map.Entry<String, String> entry : allGoals.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // ...
            if (value.equals(date)){
                goalsToday++;
            }
            System.out.println("Current goal : " + key);
            System.out.println("Current date : " + date);
            System.out.println("Current deadline : " + value);

            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            int week1 = cal1.get(Calendar.WEEK_OF_YEAR);

            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date2 = null;
            try {
                date2 = formatter.parse(value.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int week2;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            week2 = calendar.get(Calendar.WEEK_OF_YEAR);
            System.out.println("WEEKS " + week1 + " " + week2);
            if (week1 == week2){
                goalsThisWeek ++;
            }
        }
        ll = (TableLayout) root.findViewById(R.id.tableForGoals);
        TableLayout table = (TableLayout) root.findViewById(R.id.tableForGoals);
       // ll.setVerticalScrollBarEnabled(true);
        for (Map.Entry<String, String> entry : allGoals.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

                TableRow tableRow = new TableRow(root.getContext());
                tableRow.setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        1.0f));
                TextView textGoal = new TextView(root.getContext());
                textGoal.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                textGoal.setText(key);
                textGoal.setMovementMethod(new ScrollingMovementMethod());
                tableRow.addView(textGoal);
                table.addView(tableRow);

                    TextView text2 = new TextView(root.getContext());
                    text2.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1.0f));
                    text2.setMovementMethod(new ScrollingMovementMethod());
                    text2.setText(value);

                    tableRow.addView(text2);
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