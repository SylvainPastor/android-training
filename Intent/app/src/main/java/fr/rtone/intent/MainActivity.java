package fr.rtone.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    class ReceiverMessage
        extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(
                    context,
                    intent.getStringExtra("MESSAGE"),Toast.LENGTH_SHORT).show();
        }
    }

    private static final int RESULT_CODE = 10;
    private TextView textResult;
    ReceiverMessage receiverMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView) findViewById(R.id.textResult);
        findViewById(R.id.btnStartActivity).setOnClickListener(this);
        findViewById(R.id.btnStartServ).setOnClickListener(this);
        findViewById(R.id.btnStopServ).setOnClickListener(this);

        receiverMsg = new ReceiverMessage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStartActivity:
                startActivityForResult(new Intent(
                        MainActivity.this,FormActivity.class),
                        RESULT_CODE
                );
                break;
            case R.id.btnStartServ:
                startService(new Intent(
                        MainActivity.this,
                        MyService.class
                ));
                break;
            case R.id.btnStopServ:
                stopService(new Intent(
                        MainActivity.this,
                        MyService.class
                ));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiverMsg,new IntentFilter("fr.rtone.intent.signal"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverMsg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==RESULT_CODE && resultCode==RESULT_OK) {
            // mise a jour du textView
            if (data != null)
                textResult.setText(data.getStringExtra("firstname"));
        }
        else
            textResult.setText("");

        super.onActivityResult(requestCode, resultCode, data);
    }
}
