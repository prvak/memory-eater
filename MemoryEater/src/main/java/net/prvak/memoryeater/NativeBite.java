package net.prvak.memoryeater;

import net.prvak.memoryeater.exceptions.MemoryEaterBiteFailedException;

public class NativeBite implements Bite {
    private int size = 0;
    private int success = 0;

    public NativeBite(int size) {
        this.size = size;
    }

    @Override
    public void grab() {
        success = allocate(size);
        if (success == 0) {
            throw new MemoryEaterBiteFailedException(this);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isInNativeHeap() {
        return true;
    }

    /** Load allocation function from a native library. This function just allocates
     * given amount of memory. As the whole purpose of this app is to eat memory the
     * memory allocated from native code will never be released. */
    static {
        System.loadLibrary("nativebite");
    }
    private native int allocate(int size);
}
