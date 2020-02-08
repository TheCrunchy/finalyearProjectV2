package com.cameron.finalyearprojectv2.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cameron.finalyearprojectv2.Goal;
import com.cameron.finalyearprojectv2.MainActivity;
import com.cameron.finalyearprojectv2.R;
import com.cameron.finalyearprojectv2.UserData;

import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;

    //these are for the spinner to select a goal
    private Spinner spinner;
    private EditText userInputGoal1, userInputGoal2, userInputGoal3;
    private EditText userInputGoalTitle;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox goalComplete;
    //these are for the spinner to select a goal

    //the user data
    private UserData data = MainActivity.getData();
    //the user data

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                ViewModelProviders.of(this).get(GoalsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goals, container, false);
        final TextView textView = root.findViewById(R.id.text_goalsTitle);
        goalsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
       // final View view = inflater.inflate(R.layout.fragment_goals, container, false);
        //get the edit texts to fill later
        userInputGoalTitle = (EditText) root.findViewById(R.id.text_goalsTitle);
        userInputGoal1 = (EditText) root.findViewById(R.id.editTextSubGoal1);
        datePicker=(DatePicker) root.findViewById(R.id.datePicker1);
        timePicker=(TimePicker) root.findViewById(R.id.timePicker1);
        goalComplete=(CheckBox) root.findViewById(R.id.checkBoxComplete);

        ArrayList<Goal> goals = data.getGoals();
        ArrayAdapter<Goal> adapter = new ArrayAdapter<Goal>(this.getContext(),
                android.R.layout.simple_spinner_item, goals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = root.findViewById(R.id.spinnerForGoals);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Goal g1 = (Goal) parent.getSelectedItem();
                userInputGoalTitle.setText(((Goal) parent.getSelectedItem()).getTitle());
                userInputGoal1.setText(((Goal) parent.getSelectedItem()).getSubGoal());
                datePicker.updateDate(g1.getDateTime().getTime().getYear() + 1900, g1.getDateTime().getTime().getMonth(), g1.getDateTime().getTime().getDate());
                //datePicker.
                timePicker.setIs24HourView(true);
                timePicker.setHour(g1.getDateTime().getTime().getHours());
                timePicker.setMinute(g1.getDateTime().getTime().getMinutes());
                goalComplete.setChecked(g1.isComplete());
            }
//datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(),  timePicker.getMinute()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(adapter);
        return root;
    }
}