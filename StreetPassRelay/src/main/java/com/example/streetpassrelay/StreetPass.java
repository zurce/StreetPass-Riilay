package com.example.streetpassrelay;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;


public class StreetPass extends Activity {
    public int cont=0;
    private FileWriter fw;

    public String[] Macs = {"4E:53:50:4F:4F:40","4E:53:50:4F:4F:41","4E:53:50:4F:4F:42","4E:53:50:4F:4F:43","4E:53:50:4F:4F:44",
                            "4E:53:50:4F:4F:45","4E:53:50:4F:4F:46","4E:53:50:4F:4F:47","4E:53:50:4F:4F:48","4E:53:50:4F:4F:49",
                            "4E:53:50:4F:4F:4A","4E:53:50:4F:4F:4B","4E:53:50:4F:4F:4C","4E:53:50:4F:4F:4D","4E:53:50:4F:4F:4E",
                            "4E:53:50:4F:4F:4F"};

    public String[] MacsH = {"78,83,80,79,79,64","78,83,80,79,79,65","78,83,80,79,79,66","78,83,80,79,79,67","78,83,80,79,79,68",
                             "78,83,80,79,79,69","78,83,80,79,79,70","78,83,80,79,79,71","78,83,80,79,79,72", "78,83,80,79,79,73",
                             "78,83,80,79,79,74","78,83,80,79,79,75","78,83,80,79,79,76","78,83,80,79,79,77","78,83,80,79,79,78",
                             "78,83,80,79,79,79"};


    public void sendToast(String text,boolean toolong){

        if(toolong)
            Toast.makeText(this, text,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public boolean changeMac(int method){
        try{
            switch (method){
                case 0:
                    fw = new FileWriter("/data/etc/wlan_macaddr0");
                    break;
                case 1:
                    fw = new FileWriter("/data/.nvmac.info");
                    break;
                case 2:
                    fw = new FileWriter("/factory/wifi/.mac.info");
                    break;
                case 3:
                    fw = new FileWriter("/data/etc/wlan_macaddr0");
                    fw.write(MacsH[cont]);
                    fw.close();
                    return true;

            }
            fw.write(Macs[cont]);
            fw.close();
        }catch(Exception e){
            sendToast(e.getMessage() + " "+method ,false);
            return false;
        }

        return true;
    }

    public void setWifiTetheringEnabled(boolean enable) {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

        Method[] methods = wifiManager.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("setWifiApEnabled")) {
                try {
                    method.invoke(wifiManager, null, enable);
                } catch (Exception ex) {
                    sendToast(ex.getMessage(),true);
                }
                break;
            }
        }
    }
}
