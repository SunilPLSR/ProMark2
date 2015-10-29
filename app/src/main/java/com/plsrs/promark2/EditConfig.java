package com.plsrs.promark2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class EditConfig extends ActionBarActivity {

    TextView t;
    EditText e1;
    EditText e2;
    String pack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_config);

        Bundle data = getIntent().getExtras();
        if(data == null){
            return;
        }
        String name = data.getString("name");
        pack = data.getString("package");
        t = (TextView) findViewById(R.id.textView);
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText2);

        t.setText(name);
        MyDBHandler dbHandler;
        dbHandler = new MyDBHandler(this, null ,null, 1);
        e1.setText(dbHandler.get_bt(pack));
        e2.setText(dbHandler.get_at(pack));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButton(View view){
        MyDBHandler dbHandler;
        dbHandler = new MyDBHandler(this, null ,null, 1);
        String bt = e1.getText().toString();
        String at = e2.getText().toString();
        String text = t.getText().toString();

        dbHandler.input(bt,text,at,pack);
    }
}
