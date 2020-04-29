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

import java.util.concurrent.TimeUnit;

public class taskTimerFragment extends Fragment {

    private TaskTimerViewModel taskTimerViewModel;
    private EditText timeInput;
    private static final long startTimeMillis = 0;

    private TextView textViewCountDown;
    private Button buttonStartPause;
    private Button buttonReset;

    private CountDownTimer countDownTimer;

    private boolean timerRunning;

    private long timeLeftMillis = startTimeMillis;

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

    //start the timer
    private void startTimer() {
        String input = timeInput.getText().toString();
        long millisInput;
        //check if the timer hasnt started yet or if the number is bad
        if (timeLeftMillis < 1) {
            if (input.length() == 0) {
                Toast.makeText(this.getContext(), "You must enter a number into the input", Toast.LENGTH_SHORT).show();
                return;
            }

            millisInput   = Long.parseLong(input) * 60000;
            if (millisInput == 0) {
                Toast.makeText(this.getContext(), "Must be a positive number", Toast.LENGTH_SHORT).show();
                return;
            }
            timeInput.setText("");
        }
        //if it has started and paused, use the paused time and start again
        else {
            millisInput = timeLeftMillis;
        }
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
        buttonStartPause.setText("Pause");
        buttonReset.setVisibility(View.INVISIBLE);
    }

    //pause the timer
    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartPause.setText("Start");
        buttonReset.setVisibility(View.VISIBLE);
    }

    //reset the timer to default value
    private void resetTimer() {
        timeLeftMillis = startTimeMillis;
        updateCountDownText();
        buttonReset.setVisibility(View.INVISIBLE);
        buttonStartPause.setVisibility(View.VISIBLE);
    }

    //update the text for the timer, cant remember why this is slightly different to the home screen countdown timer, but it works so no need to break it
    private void updateCountDownText() {
        long hours = TimeUnit.MILLISECONDS.toHours(timeLeftMillis) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeLeftMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeLeftMillis) % 60;
        String timeLeft;
        if (hours > 0) {
           timeLeft= String.format( hours + "h " + minutes + "m " + seconds + "s" );
        } else {
           timeLeft= String.format(minutes + "m " + seconds + "s" );
        }

        textViewCountDown.setText(timeLeft);
    }

}