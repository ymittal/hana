package finalproject.csci205.com.ymca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivityStartScreen extends AppCompatActivity {

    private Button tempLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start_screen);
        tempLogin = (Button) findViewById(R.id.tempLogin);

        tempLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Goto Other Activity
                Intent i = new Intent(MainActivityStartScreen.this, NavActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
