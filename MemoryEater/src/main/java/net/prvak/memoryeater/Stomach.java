package net.prvak.memoryeater;

import java.util.ArrayList;

public class Stomach {

    /** Chunks of memory that have already been eaten. */
    private ArrayList<Bite> stomach;
    /** Total size of all bites. */
    private int size = 0;

    public Stomach() {
        stomach = new ArrayList<Bite>();
    }

    /** Add another bite into the stomach. */
    public void digest(Bite bite) {
        stomach.add(bite);
        size += bite.getSize();
    }

    /** Remove everything from the stomach. */
    public void vomit() {
        stomach.clear();
        size = 0;
    }

    public int getSize() {
        return size;
    }
}
