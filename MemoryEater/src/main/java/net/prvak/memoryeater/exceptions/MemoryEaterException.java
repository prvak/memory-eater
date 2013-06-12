package net.prvak.memoryeater.exceptions;

public class MemoryEaterException extends RuntimeException {
    public MemoryEaterException() { super(); }
    public MemoryEaterException(String s) { super(s); }
    public MemoryEaterException(String s, Throwable throwable) { super(s, throwable); }
    public MemoryEaterException(Throwable throwable) { super(throwable); }
}
