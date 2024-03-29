package com.cameron.finalyearprojectv2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity{
    private static final String FILE_NAME = "data.json";
    private AppBarConfiguration mAppBarConfiguration;
    private static Gson gson = new Gson();
    private static UserData data = UserData.getInstance();
    private DatePicker datePicker;
    private TimePicker timePicker;
    private CheckBox goalComplete;
    private EditText userInputGoal1;
    private EditText userInputGoalTitle, userInputTitle,  userInputDetails;


    public static UserData getData(){
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Load the users data file
        loadFile();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_daily, R.id.nav_weekly,
                R.id.nav_goals,R.id.nav_task_timer)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



    //confirmation for adding a goal
    public void onAddGoal(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Adding Goal")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onAddGoalConfirmed(v);
            }
        })
    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        })
    .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //confirmation for deleting a goal
    public void onDeleteGoal(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Deletion of Goal")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteGoalConfirmed(v);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //If the user clicks yes on the confirmation popup, add the goal to the user data then save the file
    public void onAddGoalConfirmed(View v){
        datePicker=(DatePicker)findViewById(R.id.datePicker1);
        timePicker=(TimePicker)findViewById(R.id.timePicker1);
        userInputGoalTitle = (EditText) findViewById(R.id.text_title);
        userInputGoal1 = (EditText) findViewById(R.id.text_details);
        Button button = (Button) findViewById(R.id.buttonSave);
        goalComplete = (CheckBox) findViewById(R.id.checkBoxComplete);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(),  timePicker.getMinute());

        date = cal.getTime();
        data.addGoal(userInputGoalTitle.getText().toString(), userInputGoal1.getText().toString(), goalComplete.isChecked(), cal);
        userInputGoal1.setText("");
        userInputGoalTitle.setText("");
        saveFile();
    }


    //I should move these methods into the relevant fragments for encapsulation
    //If the user clicks yes on the confirmation popup, delete the goal from user data and save the file
    public void onDeleteGoalConfirmed(View v) {
        //data
        userInputGoalTitle = (EditText) findViewById(R.id.text_title);
        userInputGoal1 = (EditText) findViewById(R.id.text_details);
        Button button = (Button) findViewById(R.id.buttonDelete);
        data.removeGoal(userInputGoalTitle.getText().toString());
        saveFile();

    }
    //confirmation for adding a goal, give the user a popup
    public void onAddTimeTable(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirm add")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onAddTimeTableConfirmed(v);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //confirmation for deleting a goal, give the user a popup
    public void onDeleteTimeTable(View v){
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteTimeTableConfirmed(v);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //add a new item to timetable after user confirms the popup
    public void onAddTimeTableConfirmed(View v){
        datePicker=(DatePicker)findViewById(R.id.datePicker1TimeTable);
        timePicker=(TimePicker)findViewById(R.id.timePicker1TimeTable);
        userInputTitle = (EditText) findViewById(R.id.text_titleTimeTable);
        userInputDetails = (EditText) findViewById(R.id.text_detailsTimeTable);
        Button button = (Button) findViewById(R.id.buttonSaveTimeTable);
        goalComplete = (CheckBox) findViewById(R.id.checkBoxComplete);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(),  timePicker.getMinute());

        date = cal.getTime();
        data.addTimeTableData(userInputTitle.getText().toString(), userInputDetails.getText().toString(), cal);
        //set these fields to empty so the user doesnt add the same item again
        userInputTitle.setText("");
        userInputDetails.setText("");
        saveFile();
    }

    //Delete an item from timetable after user confirms the popup
    public void onDeleteTimeTableConfirmed(View v) {
        //data
        datePicker=(DatePicker)findViewById(R.id.datePicker1TimeTable);
        timePicker=(TimePicker)findViewById(R.id.timePicker1TimeTable);
        userInputTitle = (EditText) findViewById(R.id.text_titleTimeTable);
        userInputDetails= (EditText) findViewById(R.id.text_detailsTimeTable);
        Button button = (Button) findViewById(R.id.buttonDeleteTimeTable);
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        cal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(),  timePicker.getMinute());
        System.out.println("Removing data");
        data.removeTimeTableData(userInputTitle.getText().toString(), cal);
        //set these fields to empty so the user cant attempt to delete the same item again
        userInputTitle.setText("");
        userInputDetails.setText("");
        saveFile();

    }


    //using googles Gson ("google/gson", 2008)
    //https://github.com/google/gson/blob/master/LICENSE
    //Save the user data object to a json file on the local device
    public void saveFile() {
        //data
        String text = gson.toJson(data);
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //load the user data file and make it into an object
    //using googles Gson ("google/gson", 2008)
    //https://github.com/google/gson/blob/master/LICENSE
    public void loadFile() {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            if (sb.equals("")){
                return;
            }
            Type temp = new TypeToken<UserData>() {}.getType();
           data = gson.fromJson(sb.toString(), temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
