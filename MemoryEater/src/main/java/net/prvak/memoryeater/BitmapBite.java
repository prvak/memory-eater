package net.prvak.memoryeater;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.prvak.memoryeater.exceptions.MemoryEaterBiteFailedException;

import java.lang.reflect.Field;

/**
 * Bite memory by loading a bitmap.
 * By using a hidden option "inNativeAlloc", the bitmap should be loaded in native memory.
 * Unfortunately since Android 3.0 (I think) the bitmap memory is always counted against the
 * java heap to improve garbage collector performance.
 */
public class BitmapBite implements Bite {

    /** Some bitmap that will be loaded again and again. */
    private static final int BITMAP_RESOURCE_ID = R.raw.bitmap_bite;
    private boolean inNative = false;
    private Bitmap bitmap = null;
    private Resources resources = null;

    public BitmapBite(Resources resources) {
        this.resources = resources;
    }

    @Override
    public void grab() {
        bitmap = decodeResource(BITMAP_RESOURCE_ID);
    }

    @Override
    public int getSize() {
        if (bitmap == null) {
            return 0;
        } else {
            int size = bitmap.getHeight() * bitmap.getRowBytes();
            return size;
        }
    }

    @Override
    public boolean isInNativeHeap() {
        return inNative;
    }

    /**
     * Method that tries to load bitmap using the native memory.
     * Code taken from here: http://stackoverflow.com/a/14928248/2035618
     */
    private Bitmap decodeResource(int id) {
        Bitmap bitmap = null;
        // Try to set inNativeAlloc flag.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        try {
            Field field = BitmapFactory.Options.class.getField("inNativeAlloc");
            field.setBoolean(options, true);
            inNative = true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // On newer Android I always get this exception. The field has probably been removed.
            e.printStackTrace();
        }

        // Try to load the bitmap,
        try {
            bitmap = BitmapFactory.decodeResource(resources, id, options);
        } catch (OutOfMemoryError e) {
            throw new MemoryEaterBiteFailedException(this, e);
        }
        return bitmap;
    }
}
