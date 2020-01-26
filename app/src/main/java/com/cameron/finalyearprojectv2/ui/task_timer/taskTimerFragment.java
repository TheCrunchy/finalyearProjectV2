package com.cameron.finalyearprojectv2.ui.task_timer;

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

public class taskTimerFragment extends Fragment {

    private TaskTimerViewModel taskTimerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskTimerViewModel =
                ViewModelProviders.of(this).get(TaskTimerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_task_timer, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        taskTimerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}