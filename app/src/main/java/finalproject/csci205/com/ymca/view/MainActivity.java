package finalproject.csci205.com.ymca.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import finalproject.csci205.com.ymca.R;

public class MainActivity extends AppCompatActivity {

    private Button tempLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempLogin = (Button) findViewById(R.id.tempLogin);

        tempLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Goto Other Activity
                Intent i = new Intent(MainActivity.this, NavActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
