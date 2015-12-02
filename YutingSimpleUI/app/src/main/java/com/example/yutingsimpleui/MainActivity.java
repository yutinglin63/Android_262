package com.example.yutingsimpleui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private CheckBox hideCheckBox;
    private ListView historyListView;
    private Spinner storeInfoSpinner;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeInfoSpinner = (Spinner)findViewById(R.id.storeInfoSpinner);

        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editText = (EditText) findViewById(R.id.inputText);
        //editText.setText("1234");
        editText.setText(sharedPreferences.getString("inputText", ""));
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        submit(v);
                        return true;
                    }
                }
                return false;
            }
        });

        hideCheckBox = (CheckBox) findViewById(R.id.hideCheckBox);
        hideCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("hideCheckBox", isChecked);
                editor.commit();
            }
        });
        hideCheckBox.setChecked(sharedPreferences.getBoolean("hideCheckBox",false));
        //hideCheckBox.setChecked(true);

        historyListView = (ListView)findViewById(R.id.historylistView);
        setHistory();
        setStoreInfo();
    }

    private void setStoreInfo() {
        String[] stores = getResources().getStringArray(R.array.storeInfo);
        //String[] stores = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii","jj"};
        //String[] stores = Utils.readFile(this,"history.txt").split("\n");
        ArrayAdapter<String> storeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,stores);
        storeInfoSpinner.setAdapter(storeAdapter);
    }

    private void setHistory() {
        //String[] data = new String[]{"aa","bb","cc","dd","ee","ff","gg","hh","ii","jj"};
        //String[] data = getResources().getStringArray(R.array.storeInfo);
        String[] data = Utils.readFile(this,"history.txt").split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        historyListView.setAdapter(adapter);
    }

    public void submit(View view) {

        //if(view.getId() == R.id.inputText) {
        //}
        String text = editText.getText().toString();

        editor.putString("inputText", text);
        editor.commit();

        Utils.writeFile(this,"history.txt",text+"\n");

        if (hideCheckBox.isChecked()) {
            text = "************";
            editText.setText("************");
        }

        setHistory();
        //Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        //String fileContext = Utils.readFile(this,"history.txt");
        //Toast.makeText(this,fileContext,Toast.LENGTH_LONG).show();
        // //editText.setText("");
        //editText.setText("ABCD");
    }
}
