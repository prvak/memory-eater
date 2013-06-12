package net.prvak.memoryeater;

import android.app.ActivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import net.prvak.memoryeater.exceptions.MemoryEaterBiteFailedException;

import java.security.InvalidParameterException;

public class MainActivity extends Activity implements View.OnClickListener {

    private Stomach stomach = new Stomach();
    private TextView errorLabel = null;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorLabel = (TextView) findViewById(R.id.errorDescription);

        updateSizes();
        updateInfo();
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
        Bite bite = null;
        try {
            switch (id){
                case R.id.eat_java_memory_button:
                    bite = new JavaBite(5*1024*1024);
                    break;
                case R.id.eat_native_memory_button:
                    bite = new NativeBite(50*1024*1024);
                    break;
                case R.id.eat_bitmap_memory_button:
                    bite = new BitmapBite(getResources());
                    break;
                default:
                    throw new InvalidParameterException();
            }
            eat(bite);
            errorLabel.setVisibility(View.GONE);
            errorLabel.setText(R.string.error_none);
        } catch (MemoryEaterBiteFailedException e) {
            Bite b = e.getBite();
            if (b instanceof JavaBite) {
                errorLabel.setText(R.string.error_out_of_java_memory);
            } else if (b instanceof NativeBite) {
                errorLabel.setText(R.string.error_out_of_native_memory);
            } else if (b instanceof BitmapBite) {
                errorLabel.setText(R.string.error_out_of_bitmap_memory);
            }
            errorLabel.setVisibility(View.VISIBLE);
        }
    }

    private void eat(Bite bite) {
        bite.grab();
        stomach.digest(bite);
        updateSizes();
    }

    private void updateSizes() {
        updateJavaSize();
        updateNativeSize();
        updateTotalSize();
    }

    private void updateJavaSize() {
        TextView totalHeapLabel = (TextView) findViewById(R.id.javaHeap);
        long sizeAsInt = stomach.getJavaSize();
        String sizeAsString = toHumanReadable(sizeAsInt);
        totalHeapLabel.setText(sizeAsString);
    }

    private void updateNativeSize() {
        TextView totalHeapLabel = (TextView) findViewById(R.id.nativeHeap);
        long sizeAsInt = stomach.getNativeSize();
        String sizeAsString = toHumanReadable(sizeAsInt);
        totalHeapLabel.setText(sizeAsString);
    }

    private void updateTotalSize() {
        TextView totalHeapLabel = (TextView) findViewById(R.id.totalHeap);
        long sizeAsInt = stomach.getSize();
        String sizeAsString = toHumanReadable(sizeAsInt);
        totalHeapLabel.setText(sizeAsString);
    }

    private void updateInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);

        TextView memoryClassLabel = (TextView) findViewById(R.id.memoryClass);
        int memoryClass = activityManager.getMemoryClass();
        memoryClassLabel.setText(Integer.toString(memoryClass) + "MB");

        TextView largeMemoryClassLabel = (TextView) findViewById(R.id.largeMemoryClass);
        int largeMemoryClass = activityManager.getLargeMemoryClass();
        largeMemoryClassLabel.setText(Integer.toString(largeMemoryClass) + "MB");
    }

    private String toHumanReadable(long size) {
        // TODO: convert at least to MB
        String sizeAsString = Long.toString(size/(1024*1024));
        return sizeAsString + " MB";
    }
}
