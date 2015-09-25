package com.leanote.berial.flowlayoutsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.leanote.berial.flowlayout.FlowLayout;

import java.util.Random;

/**
 * Created by berial on 15/9/25.
 */
public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.leanote.berial.flowlayoutsample.R.layout.activity_main);

		FlowLayout flowLayout = (FlowLayout) findViewById(com.leanote.berial.flowlayoutsample.R.id.flowLayout);

		Random random = new Random();

		for(int i = 0; i < 20; i++) {
			TextView textView = new TextView(this);
			textView.setText("text" + i);
			textView.setWidth(80 + random.nextInt(80));
			textView.setHeight(60);
			textView.setBackgroundResource(R.drawable.shape_text_view);
			textView.setGravity(Gravity.CENTER);
			flowLayout.addView(textView);
		}
	}
}
