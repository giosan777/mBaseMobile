//package ge.giosan777.matutu.mbasemobile.service;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Build;
//import android.os.Handler;
//import android.os.IBinder;
//import android.telephony.TelephonyManager;
//import android.widget.Toast;
//
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//
//import ge.giosan777.matutu.mbasemobile.Volley.mobileBase.GetOneNumberExceptKt;
//////////////////////////////
//public class CallsService extends Service {
//
//    public Context context = this;
//    public Handler handler = null;
//    public static Runnable runnable = null;
//    public  boolean running = false;
//
//    public BroadcastReceiver MyServiceReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
//                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//                GetOneNumberExceptKt(incomingNumber,);
//                Toast.makeText(context, "Call" +incomingNumber, Toast.LENGTH_LONG).show();
//                Intent i = new Intent();
//                i.setClassName(
//                        "ge.giosan777.matutu.mbasemobile",
//                        "ge.giosan777.matutu.mbasemobile.SecondActivity"
//                );
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.putExtra("number","")
//                context.startActivity(i);
//            }
////                Toast.makeText(getApplicationContext(), "Toast from Service: I hear you!", Toast.LENGTH_LONG).show();
//        }
//    };
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return Service.START_STICKY;  // означает, что сервис будет перезапущен в случае внезапной остановки, даже если приложение будет закрыто
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        return true;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        // начало отличия от обычного сервиса
//        // Таковы требования Андроида: хочешь полноценно работать в фоне - нужно держать уведомление и иметь доп разрешение в манифесте
//        String CHANNEL_ID = "my_channel_01";
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                "Channel human readable title",
//                NotificationManager.IMPORTANCE_DEFAULT);
//
//        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("")
//                .setContentText("").build();
//        startForeground(1, notification);
//        // конец отличия
//
//        Toast.makeText(this, "Foreground Service created!", Toast.LENGTH_SHORT).show();
//
//        running = true;
//        try {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
//            registerReceiver(MyServiceReceiver, filter);
//        } catch (Exception e) {}
//
//        handler = new Handler();
//
//        runnable = new Runnable() {
//            public void run() {
//                if (running) {
//                    printMsg("Foreground Service is still running " + running);
//                    handler.postDelayed(runnable, 5000);  // создает цикл
//                } else {
//                    printMsg("Foreground Service exited");
//                }
//            }
//        };
//
//        handler.postDelayed(runnable, 5000);
//    }
//
//    @Override
//    public void onDestroy() {
//        printMsg("Foreground Service stopped");
//        try {
//            running = false;
//            unregisterReceiver(MyServiceReceiver);
//        } catch (Exception e) {}
//        super.onDestroy();
//    }
//
//    public void printMsg(String msg) {
//        Intent intent = new Intent("printMsgFromService");
//        intent.putExtra("msg", msg);
//        sendBroadcast(intent);
////        Toast.makeText(getApplicationContext(), "ping", Toast.LENGTH_SHORT).show();
//    }
//}