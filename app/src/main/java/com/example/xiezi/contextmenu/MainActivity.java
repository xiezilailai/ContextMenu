package com.example.xiezi.contextmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Dialog.OnDialogClickListener{

    private Button button;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new Dialog();
        dialog.setListener(this);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getFragmentManager(), "aa");
            }
        });


    }


    @Override
    public void dialogClicked(int position) {
        Toast.makeText(this,position+"",Toast.LENGTH_SHORT).show();
    }
}
