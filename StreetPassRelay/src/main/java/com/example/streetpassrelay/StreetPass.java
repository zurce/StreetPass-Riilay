package com.example.streetpassrelay;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;



public class StreetPass extends Activity {
    public int cont=0;
    private FileWriter fw;
    boolean random;
    SharedPreferences preferences;
    ArrayList<Integer> arrayUsed = new ArrayList();


    public String[] Macs = {"4E:53:50:4F:4F:40","4E:53:50:4F:4F:41","4E:53:50:4F:4F:42","4E:53:50:4F:4F:43","4E:53:50:4F:4F:44",
                            "4E:53:50:4F:4F:45","4E:53:50:4F:4F:46","4E:53:50:4F:4F:47","4E:53:50:4F:4F:48","4E:53:50:4F:4F:49",
                            "4E:53:50:4F:4F:4A","4E:53:50:4F:4F:4B","4E:53:50:4F:4F:4C","4E:53:50:4F:4F:4D","4E:53:50:4F:4F:4E",
                            "4E:53:50:4F:4F:4F","4E:53:50:4F:4F:50","4E:53:50:4F:4F:51","4E:53:50:4F:4F:52","4E:53:50:4F:4F:53",
                            "4E:53:50:4F:4F:54","4E:53:50:4F:4F:55","4E:53:50:4F:4F:56","4E:53:50:4F:4F:57","4E:53:50:4F:4F:58",
                            "4E:53:50:4F:4F:59","4E:53:50:4F:4F:5A","4E:53:50:4F:4F:5B","4E:53:50:4F:4F:5C","4E:53:50:4F:4F:5D",
                            "4E:53:50:4F:4F:5E","4E:53:50:4F:4F:5F","4E:53:50:4F:4F:60","4E:53:50:4F:4F:61","4E:53:50:4F:4F:62",
                            "4E:53:50:4F:4F:63","4E:53:50:4F:4F:64","4E:53:50:4F:4F:65","4E:53:50:4F:4F:66","4E:53:50:4F:4F:67",
                            "4E:53:50:4F:4F:68","4E:53:50:4F:4F:69","4E:53:50:4F:4F:6A","4E:53:50:4F:4F:6B","4E:53:50:4F:4F:6C",
                            "4E:53:50:4F:4F:6D","4E:53:50:4F:4F:6E","4E:53:50:4F:4F:6F","4E:53:50:4F:4F:70","4E:53:50:4F:4F:71",
                            "4E:53:50:4F:4F:72","4E:53:50:4F:4F:73","4E:53:50:4F:4F:74","4E:53:50:4F:4F:75","4E:53:50:4F:4F:76",
                            "4E:53:50:4F:4F:77","4E:53:50:4F:4F:78","4E:53:50:4F:4F:79","4E:53:50:4F:4F:7A","4E:53:50:4F:4F:7B",
                            "4E:53:50:4F:4F:7C","4E:53:50:4F:4F:7D","4E:53:50:4F:4F:7E","4E:53:50:4F:4F:7F","4E:53:50:4F:4F:80",
                            "4E:53:50:4F:4F:81","4E:53:50:4F:4F:82","4E:53:50:4F:4F:83","4E:53:50:4F:4F:84","4E:53:50:4F:4F:85",
                            "4E:53:50:4F:4F:86","4E:53:50:4F:4F:87","4E:53:50:4F:4F:88","4E:53:50:4F:4F:89","4E:53:50:4F:4F:8A",
                            "4E:53:50:4F:4F:8B","4E:53:50:4F:4F:8C","4E:53:50:4F:4F:8D","4E:53:50:4F:4F:8E","4E:53:50:4F:4F:8F",
                            "4E:53:50:4F:4F:90","4E:53:50:4F:4F:91","4E:53:50:4F:4F:92","4E:53:50:4F:4F:93","4E:53:50:4F:4F:94",
                            "4E:53:50:4F:4F:95","4E:53:50:4F:4F:96","4E:53:50:4F:4F:97","4E:53:50:4F:4F:98","4E:53:50:4F:4F:99",
                            "4E:53:50:4F:4F:9A","4E:53:50:4F:4F:9B","4E:53:50:4F:4F:9C","4E:53:50:4F:4F:9D","4E:53:50:4F:4F:9E",
                            "4E:53:50:4F:4F:9F"};

    public String[] MacsH = {"78,83,80,79,79,64","78,83,80,79,79,65","78,83,80,79,79,66","78,83,80,79,79,67","78,83,80,79,79,68",
                            "78,83,80,79,79,69","78,83,80,79,79,70","78,83,80,79,79,71","78,83,80,79,79,72","78,83,80,79,79,73",
                            "78,83,80,79,79,74","78,83,80,79,79,75","78,83,80,79,79,76","78,83,80,79,79,77","78,83,80,79,79,78",
                            "78,83,80,79,79,79","78,83,80,79,79,80","78,83,80,79,79,81","78,83,80,79,79,82","78,83,80,79,79,83",
                            "78,83,80,79,79,84","78,83,80,79,79,85","78,83,80,79,79,86","78,83,80,79,79,87","78,83,80,79,79,88",
                            "78,83,80,79,79,89","78,83,80,79,79,90","78,83,80,79,79,91","78,83,80,79,79,92","78,83,80,79,79,93",
                            "78,83,80,79,79,94","78,83,80,79,79,95","78,83,80,79,79,96","78,83,80,79,79,97","78,83,80,79,79,98",
                            "78,83,80,79,79,99","78,83,80,79,79,100","78,83,80,79,79,101","78,83,80,79,79,102","78,83,80,79,79,103",
                            "78,83,80,79,79,104","78,83,80,79,79,105","78,83,80,79,79,106","78,83,80,79,79,107","78,83,80,79,79,108",
                            "78,83,80,79,79,109","78,83,80,79,79,110","78,83,80,79,79,111","78,83,80,79,79,112","78,83,80,79,79,113",
                            "78,83,80,79,79,114","78,83,80,79,79,115","78,83,80,79,79,116","78,83,80,79,79,117","78,83,80,79,79,118",
                            "78,83,80,79,79,119","78,83,80,79,79,120","78,83,80,79,79,121","78,83,80,79,79,122","78,83,80,79,79,123",
                            "78,83,80,79,79,124","78,83,80,79,79,125","78,83,80,79,79,126","78,83,80,79,79,127","78,83,80,79,79,128",
                            "78,83,80,79,79,129","78,83,80,79,79,130","78,83,80,79,79,131","78,83,80,79,79,132","78,83,80,79,79,133",
                            "78,83,80,79,79,134","78,83,80,79,79,135","78,83,80,79,79,136","78,83,80,79,79,137","78,83,80,79,79,138",
                            "78,83,80,79,79,139","78,83,80,79,79,140","78,83,80,79,79,141","78,83,80,79,79,142","78,83,80,79,79,143",
                            "78,83,80,79,79,144","78,83,80,79,79,145","78,83,80,79,79,146","78,83,80,79,79,147","78,83,80,79,79,148",
                            "78,83,80,79,79,149","78,83,80,79,79,150","78,83,80,79,79,151","78,83,80,79,79,152","78,83,80,79,79,153",
                            "78,83,80,79,79,154","78,83,80,79,79,155","78,83,80,79,79,156","78,83,80,79,79,157","78,83,80,79,79,158",
                            "78,83,80,79,79,159"};


    public void sendToast(String text,boolean toolong){

        if(toolong)
            Toast.makeText(this, text,Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public boolean changeMac(int method){
        if(random){
            cont = getNewMAC();
        }
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
                    fw = new FileWriter("/data/etc/wlan_macaddr");
                    fw.write(MacsH[cont]);
                    fw.close();
                    return true;
                case 4:
                    nvtextline();
                    return true;
                case 5:
                    configwifimethod();
                    return true;
            }
            fw.write(Macs[cont]);
            arrayUsed.add(cont);
            fw.close();
        }catch(Exception e){
            sendToast(e.getMessage() + " "+method ,false);
            return false;
        }

        return true;
    }


    int getNewMAC(){
        int cont;

        cont = (int) (Math.random()*80 );


        while((arrayUsed.contains(cont))){
            cont = (int) (Math.random()*80);
            if(arrayUsed.size()>79){
                cont=-1;
                break;
            }
        }


        return cont;
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

    void nvtextline(){
        InputStream instream = null;
        String sfinal="";
        try {
           instream = new FileInputStream("/system/etc/wifi/nvram.txt");
        } catch (Exception ex) {
            sendToast("file not found",false);
        }
        try{
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;

                do {
                    line = buffreader.readLine();
                    try{
                        String[] s = line.split("=");
                        if(s[0].equals("macaddr")){
                            line= "macaddr="+Macs[cont];
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    sfinal+=line+ "\n";
                } while (line != null);

                fw = new FileWriter("/system/etc/wifi/nvram.txt");
                fw.write(sfinal);
                fw.close();
            }
            instream.close();
        }catch (Exception e){
            sendToast(e.toString(),false);
        }
    }


    void configwifimethod(){
        InputStream instream = null;
        String sfinal="";
        try {
            instream = new FileInputStream("/data/misc/wifi/config");
        } catch (Exception ex) {
            sendToast("file not found",false);
        }
        try{
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;

                do {
                    line = buffreader.readLine();
                    try{
                        String[] s = line.split("=");
                        if(s[0].equals("cur_etheraddr")){
                            line= "cur_etheraddr="+Macs[cont];
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    sfinal+=line+ "\n";
                } while (line != null);

                fw = new FileWriter("/data/misc/wifi/config");
                fw.write(sfinal);
                fw.close();
            }
            instream.close();
        }catch (Exception e){
            sendToast(e.toString(),false);
        }
    }

}
