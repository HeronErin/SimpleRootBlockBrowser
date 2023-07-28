package me.pickleswine.simplerootblockbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ((EditText)findViewById(R.id.nump)).setText(
                String.valueOf(getSharedPreferences("settings", Context.MODE_PRIVATE).getInt("Text size", 17))
        );
        ((EditText)findViewById(R.id.nump)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    SharedPreferences.Editor ed = getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
                    ed.putInt("Text size", Integer.parseInt(charSequence.toString()));
                    ed.commit();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}