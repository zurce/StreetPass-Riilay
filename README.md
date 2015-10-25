
# StreetPass-Riilay-
Open Source code of the StreetPass Riilay App

![Header](https://i.imgur.com/5ajm4zR.png)

# Hi! I'm Zurce (formerly DanteMx).

I have developed an App for Android that cycles the method i use to change the Mac Address of our devices, It's called StreetPass Riilay and you can download it today.

### What are the requirements for this app?
As far as i know, this app should work for anyone that can change it's mac by editing the file /data/etc/wlan_macaddr0, have mobile data and root. If you fulfill this requirements this app should work out of the box with you and you should be a Happy User.

#### Ohh man! the app didn't work for me!
Don't worry, that's the reason the app is here , if the method that the App use to change the Mac doesn't work for you (you should see a permission denied Toast) it's probably because you're on a different device or Android version that i am, I'm currently running Sony Jelly Bean on my Xperia Z, so anyone with that set up could make the app work, and if you have a different set up and you have found a way to change the mac, please explain it to me on an Image tutorial, since (I repeat) I don't have the device and it doesn't work for me if you tell me "hey bro edit this file in /data/ called nvum.something" if i don't have the device and I don't know what i should edit from that file.

The app still a beta and most of the work that it needs to be done it's about the compatibility with the different kernels and their way to manage the Mac Address. If you guys help me we probably can get this working for most of device in 1 or 2 weeks.

##### Cool! You Got Any Screens?
I sure do:

![Screen1](https://i.imgur.com/3DZSnOAh.png)
![Screen2](https://i.imgur.com/1IcMhUWh.png)

Awesome, another question, Will this App work for Tablets?
While i don't recommend to run this app on a Tablet it should run with no problem, most of the Tablets out there are Wi-Fi so i didn't take the time to adjust the layout for Tablet, it should work with no problems but it probably would look ugly as f*ck or really small, but this is something i'll fix in future updates.

#### Hey I don't like "this" or "that", Change it!
**UPDATE**  
#### *NOW YOU CAN, I'll be checking Issues and Pullrequest Frequently* 

If you don't like something just tell me why and if I think you're right I'll probably change it, remember StreetPass Riilay is made by me for you guys so feedback is well received.

Also if you want to help build my app better and you know how to do UI or have some tips for me, PM me, Any Help is well received too.

Everything sounds great, Is This App on the Play Store?
Unfortunately no, at least not right now, While it works for me , it probably doesn't work for a lot of people out there, so to upload it to the Play Store first i want to fix most of the problems and wide the compatibility with most devices, besides, right now I'm broke and i don't have money for the registration fee, i'll probably will in 1 week or 2 so be patient and if you wanna see this in the Play Store give me your feed back. (as well donation are super fine guys!)

So if it's not in the Play Store , How do i download it?
You need to side load the app to your phone, you simply go to Settings->Security->Unknown Sources and check that box, but you probably know this if you're already rooted.

### Then download the app from here:

[Dropbox Download V3.1](https://www.dropbox.com/s/f3tedvc8sv0rmgj/SP%20Riilay%20V3.apk)

[Google Drive Download V3.1](https://docs.google.com/file/d/0B5n38nW5GD0pSVJIQThjQ2hxM3M/edit?usp=sharing) 


### How to use it:
1. Start your mobile data.
2. Go to Settings->More->Tethering & Portable Hotspot (this may vary depending your phone)​
3. Select Portable Wi-Fi hotspot settings.
4. and configure your hotspot with this settings:

        SSID:attwifi
        Security:none
        (if you use the "_The Cloud" SSID you'll have to configure the 3DS to use the hotspot as one of the three connections.)
        
5. Start the App and configure your settings, most of the time the 1 minute cool down time works for me, but sometimes depending on your network speed you might need 2 or 3 minutes to get the StreetPass.

As for the starting Mac , I'll recommend it to let it as it is, but if you already streetpassed one of the macs start from there.
Press Start Riilay and it should automatically start your hotspot and change your mac, in this point, if you see a toast telling you "Permission denied" this does not work for you
##### and remember you need ROOT....

## UPDATE: StreetPass Riilay V2!:
Hello! i have added a few methods more in the app, while i tested those with dummy files it probably works now in more devices, also i add a busybox method which in theory it should be compatible with all the devices out there but with sadly is not , if your device has a file with the Mac on it, like my case or hard code it in other file the busy box method will not work, but in those devices where the mac is nowhere to be found in theory it will work.
The Methods i added are:

1. /data/.nvmac.info

2. /factory/wifi/.mac.info

3. /data/etc/wlan_macaddr 

4. BusyBox wlan0 devices.

Also, i changed the reconnection code so now it takes 10 to 15 minutes to reconnect the device so the 1 minute cold down it's not gonna work at least you have very good Internet.
Any doubt or question don't be afraid to ask, i'll probably update the app by Wednesday adding more Macs (i think there's 50 out there! ) a pause mode, and a notification mode where the app will notify you every 10 macs to check the Mii Plaza before to continue.
Hope i can fix all the compatibility issues as soon as possible but at least i expect the people that can use the app (that should be at least people with Xperia Z/ZL, and LG LX series i believe) is enjoying it .

*PS: If the app looks uglier it's because i tried re doing the UI but failed , it shouldn't be that ugly next time.*

##### *Greetings and thank you for using my app.!*


## UPDATE: StreetPass Riilay V3!:

New Updated, i added some new methods as well a randomizer for the macs and Mac address up to 5F, didn't have the time to test all of them but they should work, also i extended the time for the cooldown, now instead of using 60 seconds it use 75, because the reset method for the tethering it was taking 15 seconds of the actual connection time so, now 1 minute it's equivalent to 75 seconds of time and 60 seconds of actual connection. Also i extended the times up to 15 minutes if anyone needs that much cooldown time.

**The new Methods i added are:**

1. /system/etc/wifi/nvram.txt

2. /data/misc/wifi/config

These methods use in line mac (instead of files with only the mac) so if your setup needs to edit an specific line in a file just ask for it.

The app no automatically select the method you should use based on the location of the files, so you don't have to pick it manually anymore but the option still is there but probably is going to be gone in the next update.

Also, busybox while it works in all phone or most of them, it doesn't always change the mac of some phones because they are hardcoded in some files on the system, try to search for those files if you see busybox is not working for you.