package com.voriol.daoistgu.GuWorms;

public enum GuWormPath {
    POWER,
    TRANSFORMATION,
    FIRE,
    REINFORCEMENT,
    SUBJUGATE,
    SPACE,
    STAR,
    BLOOD;

    public int getLevel() {
        return ordinal();
    }

    public String getTranslationKey() {
        // Создаём ключ типа "path.daoistgu.blood"
        return "path.daoistgu." + this.name().toLowerCase();
    }
}