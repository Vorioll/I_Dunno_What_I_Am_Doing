package com.voriol.daoistgu.GuWorms;

public enum GuWormPath {
    POWER,
    TRANSFORMATION,
    FIRE,
    REINFORCEMENT,
    SUBJUGATE,
    SPACE,
    BLOOD;

    public int getLevel() {
        return ordinal();
    }
}