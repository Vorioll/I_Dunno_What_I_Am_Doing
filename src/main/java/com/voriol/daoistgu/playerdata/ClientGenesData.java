package com.voriol.daoistgu.playerdata;


public class ClientGenesData {
    private static PlayerGenes genes = new PlayerGenes();

    public static void set(PlayerGenes newGenes) {
        genes = newGenes;
    }

    public static PlayerGenes get() {
        return genes;
    }
}