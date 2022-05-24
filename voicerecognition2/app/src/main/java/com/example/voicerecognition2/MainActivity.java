package com.example.voicerecognition2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    private ListView mList;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new myRecognizerIntentListener());
        mList = (ListView) findViewById(R.id.listview);
    }

    public class myRecognizerIntentListener implements View.OnClickListener {
        public void onClick(View v) {
            try {
                // 用Intent來傳遞語音識別的模式,並且開啟語音模式
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // 語言模式和自由形式的語音識別
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "JAPANESE");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                // 提示語言開始
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "請開始語音");
                // 開始語音識別
                startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "找不到語音裝置",Toast.LENGTH_LONG).show();
            }
        }
    }
    // 語音結束時的回撥函式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // 取得語音的字元
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            // 設定檢視的更新
            mList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, results));
            String resultsString = "";
            for (int i = 0; i < results.size(); i++) {
                resultsString  = results.get(i);
            }
            Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
