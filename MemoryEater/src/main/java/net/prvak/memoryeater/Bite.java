package net.prvak.memoryeater;

public class Bite {

    private byte [] bytes;

    public Bite(int size) {
        bytes = new byte[size];
    }

    public int getSize() {
        return bytes.length;
    }
}
