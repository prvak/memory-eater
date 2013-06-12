package net.prvak.memoryeater;

import java.util.ArrayList;

public class Stomach {

    /** Chunks of memory that have already been eaten. */
    private ArrayList<Bite> stomach;
    /** Total size of java bites*/
    private long javaSize = 0;
    /** Total size of native bites. */
    private long nativeSize = 0;
    /** Total size of all bites. */
    private long size = 0;

    public Stomach() {
        stomach = new ArrayList<Bite>();
    }

    /** Add another bite into the stomach. */
    public void digest(Bite bite) {
        stomach.add(bite);
        int s = bite.getSize();
        size += s;
        if (bite.isInNativeHeap()) {
            nativeSize += s;
        } else {
            javaSize += s;
        }
    }

    public long getJavaSize() {
        return javaSize;
    }

    public long getNativeSize() {
        return nativeSize;
    }

    public long getSize() {
        return size;
    }
}
