package com.voriol.daoistgu.playerdata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;

public class PlayerGenes {
    private int normal_genes = 10;
    private int primitive_genes = 0;
    private int mutant_genes = 0;
    private int sacred_genes = 0;
    private int super_genes = 0;
    private int rank = 1;

    // Codec для сериализации через систему Mojang (используется в AttachmentType)
    public static final Codec<PlayerGenes> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("normal_genes").forGetter(PlayerGenes::getNormal_genes),
            Codec.INT.fieldOf("primitive_genes").forGetter(PlayerGenes::getPrimitive_genes),
            Codec.INT.fieldOf("mutant_genes").forGetter(PlayerGenes::getMutant_genes),
            Codec.INT.fieldOf("sacred_genes").forGetter(PlayerGenes::getSacred_genes),
            Codec.INT.fieldOf("super_genes").forGetter(PlayerGenes::getSuper_genes),
            Codec.INT.fieldOf("rank").forGetter(PlayerGenes::getRank)
    ).apply(instance, (n, p, m, s, su, r) -> {
        PlayerGenes genes = new PlayerGenes();
        genes.setNormal_genes(n);
        genes.setPrimitive_genes(p);
        genes.setMutant_genes(m);
        genes.setSacred_genes(s);
        genes.setSuper_genes(su);
        genes.setRank(r);
        return genes;
    }));

    // Остальные методы без изменений
    public int getNormal_genes() { return normal_genes; }
    public void setNormal_genes(int normal_genes) { this.normal_genes = normal_genes; }
    public int getPrimitive_genes() { return primitive_genes; }
    public void setPrimitive_genes(int primitive_genes) { this.primitive_genes = primitive_genes; }
    public int getMutant_genes() { return mutant_genes; }
    public void setMutant_genes(int mutant_genes) { this.mutant_genes = mutant_genes; }
    public int getSacred_genes() { return sacred_genes; }
    public void setSacred_genes(int sacred_genes) { this.sacred_genes = sacred_genes; }
    public int getSuper_genes() { return super_genes; }
    public void setSuper_genes(int super_genes) { this.super_genes = super_genes; }
    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public void addNormalGenes(int amount) { setNormal_genes(this.normal_genes + amount); }
    public void addPrimitiveGenes(int amount) { setPrimitive_genes(this.primitive_genes + amount); }
    public void addMutantGenes(int amount) { setMutant_genes(this.mutant_genes + amount); }
    public void addSacredGenes(int amount) { setSacred_genes(this.sacred_genes + amount); }
    public void addSuperGenes(int amount) { setSuper_genes(this.super_genes + amount); }

    public void copyFrom(PlayerGenes source) {
        this.normal_genes = source.normal_genes;
        this.primitive_genes = source.primitive_genes;
        this.mutant_genes = source.mutant_genes;
        this.sacred_genes = source.sacred_genes;
        this.super_genes = source.super_genes;
        this.rank = source.rank;
    }
}