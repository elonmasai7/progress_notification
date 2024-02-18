package com.elon.progressnotiffication

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.gfg.progressnotificationdemo.R.drawable
import drawable.ic_file_download


class MainActivity : CompatActivity(){

    private lateinit var notificationManager: NotificationManagerCompat
    val channelId = "Progress Notification" as String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.xml.activitymain)

        //Create a Notification Manager
        notificationManager = NotificationManagerCompat.from(this)

    }

    //Start() is called when the buttons is pressed.
    public fun start(view: View){

        val intent = Intent(this, MainActivity::class.java).apply{
            var flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, 0)

        //Sets the maximum progress as 100
        val progressMax = 100
        //Creating a notification and setting its various attributes
        val notification =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(ic_file_download)
                .setContentTitle("Elon masai")
                .setContentText("Downloading")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(progressMax, 0, true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        //Initial Alert
        notificationManager.notify(1, notification.build())

        Thread(Runnable{
            SystemClock.sleep(2000)
            var progress = 0
            while (progress <= progressMax) {
                SystemClock.sleep(
                    1000
                )
                progress += 20
                //Use this to make it a Fixed-duration progress indicator notification

                //notification.setContentText(progress.toString()+"%")
                //.setProgress(progressMax, progress, false)

                //notificationManager.notify(1, notification.build())
            }

            notification.setContentText("Download complete")
                .setProgress(0, 0, false)
                .setOngoing(false)
            notificationManager.notify(1, notification.build())
        }).start()
    }
}