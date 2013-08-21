package com.example.streetpassrelay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class MainActivity extends StreetPass {


    private  boolean activate  = false;
    private WifiManager wifiManager;
    private Timer myTimer;
    private int exit=0, method=0;
    String previousMac;
    TextView currentMac;
    Spinner spinner;
    Spinner spinnerMacs;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        currentMac = (TextView) findViewById(R.id.currentMac);
        spinner = (Spinner) findViewById(R.id.spinner3);
        spinnerMacs = (Spinner) findViewById(R.id.spinner2);
        if(RootTools.isRootAvailable()){
        }


        try{

            method =  preferences.getInt("method",0);

            spinner.setSelection(method);

        }catch (Exception e){
            e.printStackTrace();
        }

        addListenerOnCheckbox();
        getMac();

    }

    public void getMac(){
        String[] methods = {"/data/etc/wlan_macaddr0", "/data/.nvmac.info" ,"/factory/wifi/.mac.info","/data/etc/wlan_macaddr","/system/etc/wifi/nvram.txt","/data/misc/wifi/config"};
        method =0;

        try {
            previousMac = getStringFromFile(methods[method]);
            while(previousMac.equals("file not found") && method<5){
                method++;
                previousMac = getStringFromFile(methods[method]);

            }

            if(previousMac.equals("file not found")){
                method=6;
                sendToast("Your Method is BusyBox",false);
                spinnerMacs.setSelection(6);
            }else{
                sendToast("Your Method is:"+ methods[method],false);
                spinnerMacs.setSelection(method);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public  String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public  String getStringFromFile (String filePath) throws Exception {
        CommandCapture command =  new CommandCapture(0, "chmod 777 "+filePath);
        String ret ="file not found";
        if(RootTools.isRootAvailable()){
            try{
                RootTools.getShell(true).add(command).waitForFinish();
                File fl = new File(filePath);
                FileInputStream fin = new FileInputStream(fl);
                ret = convertStreamToString(fin);
                fin.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ret;
    }
    public void setPermission(int method){
        CommandCapture command =  new CommandCapture(0, "chmod  777 /data/etc/wlan_macaddr0");

        if(RootTools.isRootAvailable()){
            try{
                switch (method){
                    case 0:
                        command = new CommandCapture(0, "chmod  777 /data/etc/wlan_macaddr0");
                        break;
                    case 1:
                        command = new CommandCapture(0, "chmod  777 /data/.nvmac.info");
                        break;
                    case 2:
                        command = new CommandCapture(0, "chmod  777 /factory/wifi/.mac.info");
                        break;
                    case 3:
                         command = new CommandCapture(0,"chmod 777 /data/etc/wlan_macaddr");
                        break;
                    case 4:
                         command = new CommandCapture(0,"chmod 777 /system/etc/wifi/nvram.txt");
                        break;
                    case 5:
                        command = new CommandCapture(0,"/data/misc/wifi/config");
                        break;

                }
            }catch(Exception e){
                e.printStackTrace();
            }

            try {
                RootTools.getShell(true).add(command).waitForFinish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (RootDeniedException e) {
                e.printStackTrace();
            }
        } else {
            sendToast("Oops, this app needs Root",true);
        }
    }



    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            disconectWifi();

            if(method==6){
                if(RootTools.isBusyboxAvailable()){
                    busyBoxMethod();
                    cont++;
                }else{
                   sendToast("This Metod Needs busybox",false);
                }
            }else{
                if(changeMac(method)){
                    cont++;
                }else{
                    sendToast("This method doesn't work for you.",true);

                }
                currentMac.setText("MAC:"+Macs[cont-1]);
            }
        }
    };

        public void disconectWifi(){
        setWifiTetheringEnabled(false);
        try{
            Thread.sleep(3000);
        }catch (Exception e){
            e.printStackTrace();
        }
        setWifiTetheringEnabled(true);
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void busyBoxMethod(){
        if(RootTools.isBusyboxAvailable()){
            if(random){
                cont = getNewMAC();
            }
            CommandCapture commandCapture = new CommandCapture(0, "busybox ifconfig wlan0 hw ether "+Macs[cont]);
            currentMac.setText("MAC:"+Macs[cont]);
            try{
                RootTools.getShell(true).add(commandCapture).waitForFinish();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void addListenerOnCheckbox() {

        check  = (CheckBox) findViewById(R.id.checkBox);

        check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    random = true;
                    spinnerMacs.setEnabled(false);
                }else{
                    random = false;
                    spinnerMacs.setEnabled(true);
                }

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getOrder())
        {
            case 1:
                Intent myIntent = new Intent(MainActivity.this, About.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onTether(View view){
        Button b = (Button)findViewById(R.id.button1);

        if(!activate){
            spinnerMacs.setEnabled(false);
            check.setEnabled(false);
            Spinner spin = (Spinner)findViewById(R.id.spinner3);
            method = spin.getSelectedItemPosition();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("method",method);
            editor.commit();
            setPermission(method);
            b.setText("Stop Riilay");
            activate=true;
            if(wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(false);
            }
            setWifiTetheringEnabled(true);
            spin = (Spinner)findViewById(R.id.spinner);
            int time = (spin.getSelectedItemPosition() + 1) *75000;
            spin = (Spinner)findViewById(R.id.spinner2);
            cont = spin.getSelectedItemPosition();
            myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                }

            }, 0,time);

        }else{
            spinnerMacs.setEnabled(true);
            check.setEnabled(true);
            check.setChecked(false);
            b.setText("Start Riilay");
            sendToast("Stoping Relay",false);
            myTimer.cancel();
            myTimer.purge();
            activate=false;
            setWifiTetheringEnabled(false);
        }


    }



    @Override
    public void onBackPressed() {
        if(exit<1){
            sendToast("Press Back Again to Exit", false);
            exit++;
        }else{
            if(activate){
                myTimer.cancel();
                myTimer.purge();
                setWifiTetheringEnabled(false);
            }

            super.onBackPressed();
            this.finish();
        }



    }

}

