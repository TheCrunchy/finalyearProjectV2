package com.cameron.finalyearprojectv2.ui.editTimetable;

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

import com.cameron.finalyearprojectv2.MainActivity;
import com.cameron.finalyearprojectv2.R;
import com.cameron.finalyearprojectv2.TimeTable;
import com.cameron.finalyearprojectv2.UserData;

import java.util.ArrayList;

public class editTimetableFragment extends Fragment {

    private editTimetableViewModel editTimetableViewModel;

    //these are for the spinner to select a goal
    private Spinner spinnerSelectEdit;
    private EditText userInputDetails;
    private EditText userInputTitle;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox goalComplete;
    //these are for the spinner to select a goal

    //the user data
    private UserData data = MainActivity.getData();
    //the user data

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editTimetableViewModel =
                ViewModelProviders.of(this).get(editTimetableViewModel.class);
        View root = inflater.inflate(R.layout.fragment_edit_timetable, container, false);
        final TextView textView = root.findViewById(R.id.text_titleTimeTable);
        editTimetableViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });
        // final View view = inflater.inflate(R.layout.fragment_goals, container, false);
        //get the edit texts to fill later
        userInputTitle = (EditText) root.findViewById(R.id.text_titleTimeTable);
        userInputDetails = (EditText) root.findViewById(R.id.text_detailsTimeTable);
        datePicker=(DatePicker) root.findViewById(R.id.datePicker1TimeTable);
        timePicker=(TimePicker) root.findViewById(R.id.timePicker1TimeTable);
        goalComplete=(CheckBox) root.findViewById(R.id.checkBoxComplete);

        ArrayList<TimeTable> timetable = data.getTimeTable();
        ArrayList<TimeTable> removeThis = new ArrayList<>();
        for (int counter1 = 0; counter1 < timetable.size(); counter1++) {
            if (timetable.get(counter1).getTitle().equals("No Data to display")) {
                removeThis.add(timetable.get(counter1));
            }
        }
        timetable.removeAll(removeThis);
        ArrayAdapter<TimeTable> adapter = new ArrayAdapter<TimeTable>(this.getContext(), android.R.layout.simple_spinner_item, timetable);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectEdit = root.findViewById(R.id.spinnerForData);
        spinnerSelectEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TimeTable t1 = (TimeTable) parent.getSelectedItem();
                userInputTitle.setText(((TimeTable) parent.getSelectedItem()).getTitle());
                userInputDetails.setText(((TimeTable) parent.getSelectedItem()).getDetails());
                datePicker.updateDate(t1.getDateTime().getTime().getYear() + 1900, t1.getDateTime().getTime().getMonth(), t1.getDateTime().getTime().getDate());
                //datePicker.
                timePicker.setIs24HourView(true);
                timePicker.setHour(t1.getDateTime().getTime().getHours());
                timePicker.setMinute(t1.getDateTime().getTime().getMinutes());
            }
            //datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(),  timePicker.getMinute()
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSelectEdit.setAdapter(adapter);
        return root;
    }
}