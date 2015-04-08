package com.byteshaft.namaztime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;


public class ChangeCity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    static boolean downloadRun = false;
    LinearLayout linearLayout;
    Helpers mHelpers;
    AlarmHelpers alarmHelpers;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        mHelpers = new Helpers(this);
        alarmHelpers = new AlarmHelpers(this);
        int mPreviousCity = mHelpers.getPreviouslySelectedCityIndex();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        ListView list = getListView(mPreviousCity);
        list.setOnItemClickListener(this);
    }

    private void fileNotExsist(AdapterView<?> parent, int position) {
        new NamazTimesDownloadTask(this).execute();
        parent.getItemAtPosition(position);
        parent.setSelection(position);
        String cityName = parent.getItemAtPosition(position).toString().toLowerCase();
        mHelpers.saveSelectedCity(cityName, position);
        downloadRun = true;
    }

    private void fileExsist(AdapterView<?> parent, int position) {
        mHelpers.setTimesFromDatabase(true, MainActivity.sFileName);
        parent.setSelection(position);
        String cityName = parent.getItemAtPosition(position).toString().toLowerCase();
        mHelpers.saveSelectedCity(cityName, position);
        Intent alarmIntent = new Intent("com.byteshaft.setalarm");
        sendBroadcast(alarmIntent);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private ListView getListView(int mPreviousCity) {
        ListView list = new ListView(this);
        String[] cityList = new String[]{"Karachi", "Lahore", "Multan"
                , "Islamabad", "Peshawar", "Azad Kashmir", "Faisalabad", "Bahawalpur", "Rawalpindi", "Hyderabad", "Quetta"};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cityList);
        list.setAdapter(modeAdapter);
        list.setItemChecked(mPreviousCity, true);
        linearLayout.addView(list);
        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        alarmHelpers.removePreviousAlarams();
        String city = parent.getItemAtPosition(position).toString();
        String location = getFilesDir().getAbsoluteFile().getAbsolutePath() + "/" + city;
        file = new File(location);
        MainActivity.sFileName = city;
        if (file.exists()) {
            fileExsist(parent, position);
        } else {
            fileNotExsist(parent, position);
        }

    }
}
