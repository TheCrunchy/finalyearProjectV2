package com.cameron.finalyearprojectv2.ui.weekly;

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

public class weeklyFragment extends Fragment {

    private weeklyViewModel weeklyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        weeklyViewModel =
                ViewModelProviders.of(this).get(weeklyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weekly, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        weeklyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}