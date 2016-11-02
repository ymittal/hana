package finalproject.csci205.com.ymca;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivityStartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start_screen);

        Toast.makeText(this, "TEST", Toast.LENGTH_LONG).show();
    }
}
