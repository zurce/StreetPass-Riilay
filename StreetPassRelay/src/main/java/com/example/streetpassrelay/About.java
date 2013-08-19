package com.example.streetpassrelay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.Date;

/**
 * Created by zurce on 8/15/13.
 */
public class About extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }



    public void goToGBA (View view) {
        goToUrl ( "http://gbatemp.net/threads/how-to-have-a-homemade-streetpass-relay.352645/");
    }

    public void goToReddit (View view) {
        goToUrl ( "http://www.reddit.com/r/3DS/comments/1k0g58/setting_up_a_streetpass_relay_at_home/");
    }

    public void donatePay(View view){
        goToUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=DRPLGA6D4KYL6&lc=US&item_name=StreetPass%20Riilay&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted");
    }

    private void goToUrl (String url) {

        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
            this.finish();
        }

    }
