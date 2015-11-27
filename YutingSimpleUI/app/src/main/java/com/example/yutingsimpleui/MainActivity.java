package com.example.yutingsimpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private CheckBox hideCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.inputText);
        editText.setText("1234");
        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_ENTER){
                        submit(v);
                        return true;
                    }
                }
                return false;
            }
        });

        hideCheckBox = (CheckBox)findViewById(R.id.hideCheckBox);
        //hideCheckBox.setChecked(true);
    }

    public void submit(View view){

        if(view.getId() == R.id.inputText) {
            String text = editText.getText().toString();
            if (hideCheckBox.isChecked()) {
                text = "************";
                editText.setText("************");
            }
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }
        //editText.setText("");
        //editText.setText("ABCD");
    }
}
