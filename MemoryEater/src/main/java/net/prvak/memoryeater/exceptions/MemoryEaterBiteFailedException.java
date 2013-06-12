package net.prvak.memoryeater.exceptions;

import net.prvak.memoryeater.Bite;

public class MemoryEaterBiteFailedException extends MemoryEaterException {
    public MemoryEaterBiteFailedException(Bite bite) {
        super();
        this.bite = bite;
    }

    public MemoryEaterBiteFailedException(Bite bite, Throwable throwable) {
        super(throwable);
        this.bite = bite;
    }

    public Bite getBite() {
        return bite;
    }

    private Bite bite = null;
}
