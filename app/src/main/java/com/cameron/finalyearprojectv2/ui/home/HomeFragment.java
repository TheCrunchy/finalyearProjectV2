package com.cameron.finalyearprojectv2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cameron.finalyearprojectv2.R;
import com.cameron.finalyearprojectv2.UserData;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String day = LocalDate.now().getDayOfWeek().name();
    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    String date = simpleDateFormat.format(new Date());
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textDay = root.findViewById(R.id.dayOfWeekTextView);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView deadlinesToday = root.findViewById(R.id.textViewDeadlinesToday);
        final TextView deadlinesThisWeek = root.findViewById(R.id.textViewDeadlinesThisWeek);
        UserData data = UserData.getInstance();
        if (data != null && data.getGoals() != null) {
            if (data.getGoals().size() < 0) {
                deadlinesToday.setText(data.getGoals().size());
                deadlinesThisWeek.setText("0");
            }
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