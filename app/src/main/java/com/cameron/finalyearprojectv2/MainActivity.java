package com.cameron.finalyearprojectv2;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
    //EditText mEditText;
    String date;
    DatePicker datePicker;
    TimePicker timePicker;
    EditText userInputGoal1, userInputGoal2, userInputGoal3;
    EditText userInputGoalTitle;


    public static UserData getData(){
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Load the users data
        loadFile();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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

    public void onAddGoal(View v){
        datePicker=(DatePicker)findViewById(R.id.datePicker1);
        timePicker=(TimePicker)findViewById(R.id.timePicker1);
        userInputGoalTitle = (EditText) findViewById(R.id.text_goalsTitle);
        userInputGoal1 = (EditText) findViewById(R.id.editTextSubGoal1);
        Button button = (Button) findViewById(R.id.buttonSave);
       // System.out.println("PICKER " + picker.getMonth());

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(datePicker.getYear(), datePicker.getDayOfMonth(), datePicker.getMonth() + 1, timePicker.getMinute(), timePicker.getHour());
        date.setMonth(datePicker.getMonth() + 1);
        date.setDate(datePicker.getDayOfMonth());
        date.setYear(datePicker.getYear());
        System.out.println("YEAR " + datePicker.getYear());
        date.setMinutes(timePicker.getMinute());
        date.setHours(timePicker.getHour());
        data.addGoal(userInputGoalTitle.getText().toString(), userInputGoal1.getText().toString(), date);
        saveFile();
    }
    public void saveFile() {
        //data
        String text = gson.toJson(data);
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
                    Toast.LENGTH_LONG).show();
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
