package com.example.streetpassrelay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
    private ConnectivityManager dataManager;
    private int exit=0, method=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

        dataManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if(RootTools.isRootAvailable()){
            if(RootTools.isBusyboxAvailable()){
                sendToast("Busy Box Found!",false);
            }else{

            }
        }else{

        }


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
                        command = new CommandCapture(0, "chmod  644 /data/.nvmac.info");
                        break;
                    case 2:
                        command = new CommandCapture(0, "chmod  777 /factory/wifi/.mac.info");
                        break;
                    case 3:
                         command = new CommandCapture(0,"chmod 777 /data/etc/wlan_macaddr");
                }
            }catch(Exception e){
                sendToast(e.getMessage(),false);
            }

            try {
                RootTools.getShell(true).add(command).waitForFinish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                sendToast(e.getMessage(),false);
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

            if(method==4){
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
                sendToast("New MAC is:"+ Macs[cont-1],true);
            }
        }
    };

        public void disconectWifi(){
        setWifiTetheringEnabled(false);
        sendToast("",false);
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

    public void IllMakeYouAnOffer(boolean which){
        if(which){
            RootTools.offerBusyBox(this);
        }else{
            RootTools.offerSuperUser(this);
        }
    }


    public void busyBoxMethod(){
        if(RootTools.isBusyboxAvailable()){
           CommandCapture commandCapture = new CommandCapture(0, "busybox ifconfig wlan0 hw ether "+Macs[cont]);
            try{
                RootTools.getShell(true).add(commandCapture).waitForFinish();
            }catch (Exception e){
                sendToast(e.getMessage(),false);
            }

        }
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
            Spinner spin = (Spinner)findViewById(R.id.spinner3);
            method = spin.getSelectedItemPosition();
            setPermission(method);
            b.setText("Stop Riilay");
            activate=true;
            if(wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(false);
            }
            setWifiTetheringEnabled(true);
            sendToast("Starting Relay",true);
            spin = (Spinner)findViewById(R.id.spinner);
            int time = (spin.getSelectedItemPosition() + 1) *60000;
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
            b.setText("Start Riilay");
            sendToast("Stoping Relay",false);
            myTimer.cancel();
            myTimer.purge();
            activate=false;
        }


    }

    public void offTether(View view){
        sendToast("Stoping Relay",false);
        myTimer.cancel();
        myTimer.purge();


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
            }

            super.onBackPressed();
            this.finish();
        }



    }

}

