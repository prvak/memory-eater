package net.prvak.memoryeater;

import net.prvak.memoryeater.exceptions.MemoryEaterBiteFailedException;

public class JavaBite implements Bite {

    private byte [] bytes = new byte[0];
    private int size = 0;

    public JavaBite(int size) {
        this.size = size;
    }

    @Override
    public void grab() {
        try {
            bytes = new byte[size];
        } catch (OutOfMemoryError e) {
            throw new MemoryEaterBiteFailedException(this, e);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isInNativeHeap() {
        return false;
    }
}
