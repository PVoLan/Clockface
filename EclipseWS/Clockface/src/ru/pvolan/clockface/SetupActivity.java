package ru.pvolan.clockface;

import com.google.analytics.tracking.android.GAServiceManager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SetupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        
        //GAServiceManager.getInstance().
        GAServiceManager.getInstance().dispatch();
    }


}
