package com.plsrs.promark2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends ActionBarActivity {

    public static int count_apps = 0;
    public static String[] temp;
    public static String[] packagearray;
    TableLayout tab;
    TextToSpeech t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int i=0;
        ListView l = (ListView) findViewById(R.id.listView);
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        temp = new String[packages.size()];
        packagearray = new String[packages.size()];

        String s = null;
  //       int count = 0;

        Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(pm));


        for (ApplicationInfo packageInfo : packages) {
            temp[count_apps] = packageInfo.loadLabel(getPackageManager()).toString();
            packagearray[count_apps] = packageInfo.packageName;
            count_apps++;
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, temp);
        l.setAdapter(listAdapter);

        l.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //String prof = String.valueOf(parent.getItemAtPosition(position));
                        String prof = packagearray[position];
                        Toast.makeText(MainActivity.this, prof + "  " + position, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, EditConfig.class);
                        i.putExtra("name", temp[position]);
                        i.putExtra("package", packagearray[position]);
                        startActivity(i);

                    }
                }
        );




        //DATABASE
        MyDBHandler dbHandler;



        //Notification Code

        LocalBroadcastManager.getInstance(this).registerReceiver(onNotice, new IntentFilter("Msg"));


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });



    }



    //Notification related
    private BroadcastReceiver onNotice= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String pack = intent.getStringExtra("package");
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");

            Set<String> keys = intent.getExtras().keySet();

            //my code
            //Toast.makeText(MainActivity.this,pack+"     "+title+"     "+text,Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this,pack,Toast.LENGTH_LONG).show();


            MyDBHandler dbHandler;
            dbHandler = new MyDBHandler(MainActivity.this, null ,null, 1);
            //Before Text
            String bt = dbHandler.get_bt(pack);
            //application name
            String app = dbHandler.get_app(pack);
            //after text
            String at = dbHandler.get_at(pack);

            t1.speak(bt+app+at, TextToSpeech.QUEUE_FLUSH, null);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static int getCount(){
        return count_apps;
    }

    public static String[] getTemp(){
        return temp;
    }

    public static String[] getPackagearray(){
        return packagearray;
    }
}
