package com.voriol.daoistgu.GuWorms;

public enum GuWormRank {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int level;

    GuWormRank(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static GuWormRank byLevel(int level) {
        for (GuWormRank rank : values()) {
            if (rank.level == level) return rank;
        }
        return ONE;
    }
}