package com.cameron.finalyearprojectv2.ui.task_timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cameron.finalyearprojectv2.R;

import java.util.Locale;

public class taskTimerFragment extends Fragment {

    private TaskTimerViewModel taskTimerViewModel;
    private EditText timeInput;
    private static final long START_TIME_MILLIS = 600000;

    private TextView textViewCountDown;
    private Button buttonStartPause;
    private Button buttonReset;

    private CountDownTimer countDownTimer;

    private boolean timerRunning;

    private long timeLeftMillis = START_TIME_MILLIS;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskTimerViewModel =
                ViewModelProviders.of(this).get(TaskTimerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_task_timer, container, false);
        timeInput = root.findViewById(R.id.inputMinutes);
        taskTimerViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);


            }
        });

        textViewCountDown = root.findViewById(R.id.text_view_countdown);

        buttonStartPause = root.findViewById(R.id.button_start_pause);
        buttonReset = root.findViewById(R.id.button_reset);

        buttonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

        return root;
    }
    private void startTimer() {
        String input = timeInput.getText().toString();
        if (input.length() == 0) {
            Toast.makeText(this.getContext(), "Field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        long millisInput = Long.parseLong(input) * 60000;
        if (millisInput == 0) {
            Toast.makeText(this.getContext(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
            return;
        }
        timeInput.setText("");


        countDownTimer = new CountDownTimer(millisInput , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                buttonStartPause.setText("Start");
                buttonStartPause.setVisibility(View.INVISIBLE);
                buttonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerRunning = true;
        buttonStartPause.setText("pause");
        buttonReset.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartPause.setText("Start");
        buttonReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        timeLeftMillis = START_TIME_MILLIS;
        updateCountDownText();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int hours = (int) (timeLeftMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        textViewCountDown.setText(timeLeftFormatted);
    }

}