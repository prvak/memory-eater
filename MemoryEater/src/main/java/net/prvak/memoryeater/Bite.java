package net.prvak.memoryeater;

public interface Bite {
    /** Eat some portion of the memory. */
    public void grab();
    /** Returns the amount of memory that was eaten by grab(). */
    public int getSize();
    /** Returns true if the bite was counted against the native heap. */
    public boolean isInNativeHeap();
}
