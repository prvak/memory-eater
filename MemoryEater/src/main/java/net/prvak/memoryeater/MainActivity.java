package net.prvak.memoryeater;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private Stomach stomach = new Stomach();
    private TextView totalSizeLabel = null;
    private TextView errorLabel = null;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalSizeLabel = (TextView) findViewById(R.id.totalSize);
        errorLabel = (TextView) findViewById(R.id.errorDescription);
        updateTotalSize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.start_button:
                eat(1024 * 1024 * 10);
                break;
        }
    }

    private void eat(int bytes) {
        try {
            Bite bite = new Bite(bytes);
            stomach.digest(bite);
        } catch (OutOfMemoryError e) {
            errorLabel.setText(R.string.out_of_memory_error);
        }
        updateTotalSize();
    }

    private void updateTotalSize() {
        int sizeAsInt = stomach.getSize();
        String sizeAsString = toHumanReadable(sizeAsInt);
        totalSizeLabel.setText(sizeAsString);
    }

    private String toHumanReadable(int size) {
        // TODO: convert at least to MB
        String sizeAsString = Integer.toString(size);
        return sizeAsString + " B";
    }
}
