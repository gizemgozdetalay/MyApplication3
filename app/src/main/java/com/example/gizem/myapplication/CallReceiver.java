package com.example.gizem.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gizem on 02.01.2016.
 */
public class CallReceiver extends BroadcastReceiver {
    long startTime, endTime;
      long totalTime;//in seconds



      @Override
    public void onReceive(Context context, Intent intent) {

          Bundle bundle = intent.getExtras();
          if (bundle!=null)
          {
              String state = bundle.getString(TelephonyManager.EXTRA_STATE);
              Log.w("DURUM KONTROL", state);

              if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK) )
              {

                  int i =  TelephonyManager.EXTRA_STATE_OFFHOOK.length();

                  Log.e("aaa", String.valueOf(i));
                  startTime =  System.currentTimeMillis()/1000;

                  Log.e("time", String.valueOf(startTime));

                  Toast.makeText(context, "baslama" + String.valueOf(startTime), Toast.LENGTH_SHORT).show();
                  Log.w("DURUM KONTROL", state);





              }

              if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
              {

                  endTime =  System.currentTimeMillis()/1000;
                  Log.w("BITTI",state);
                  //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                  //Calendar calendar = Calendar.getInstance();
                  //calendar.setTimeInMillis(totalTime);

                  //String date=formatter.format(calendar.getTime());
                  Toast.makeText(context, "bitis" + String.valueOf(endTime), Toast.LENGTH_SHORT).show();
                  Log.e("time2", String.valueOf(endTime));
                  Log.w("İLK",String.valueOf(startTime));

                  Log.w("SON",String.valueOf(endTime));

                  totalTime  = (endTime-startTime);
                  Log.e("time3", String.valueOf(totalTime));
                  //long totalTime =(endTime)+(startTime);
                  Log.w("BITTI MI ???", state);
                  //endTime-=startTime;
                  Toast.makeText(context, "total" + totalTime, Toast.LENGTH_LONG).show();

              }

              Log.w("İLK",String.valueOf(startTime));

              Log.w("SON",String.valueOf(endTime));

              totalTime  = (endTime-startTime);
              Log.e("time3", String.valueOf(totalTime));
              //long totalTime =(endTime)+(startTime);
              Log.w("BITTI MI ???",state);
              //endTime-=startTime;
              Toast.makeText(context, "total" + totalTime, Toast.LENGTH_LONG).show();
          }








         /* if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
          {

              for(x=0;x<1;x++)
              {
                  endTime = System.currentTimeMillis() / 1000 % 60;
              }
              totalTime = (int)(endTime-startTime);


              Toast.makeText(context, String.valueOf(totalTime),Toast.LENGTH_LONG).show();
              totalTime=0;

          }*/
    }
}
