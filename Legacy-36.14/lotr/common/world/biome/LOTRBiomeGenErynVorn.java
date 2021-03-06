package lotr.common.world.biome;

import lotr.common.entity.animal.LOTREntityBear;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.feature.LOTRTreeType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

public class LOTRBiomeGenErynVorn extends LOTRBiomeGenEriador {
   public LOTRBiomeGenErynVorn(int i, boolean major) {
      super(i, major);
      this.field_76762_K.add(new SpawnListEntry(EntityWolf.class, 8, 4, 8));
      this.field_76762_K.add(new SpawnListEntry(LOTREntityBear.class, 10, 1, 4));
      this.clearBiomeVariants();
      this.addBiomeVariantSet(LOTRBiomeVariant.SET_FOREST);
      this.decorator.treesPerChunk = 10;
      this.decorator.flowersPerChunk = 4;
      this.decorator.doubleFlowersPerChunk = 1;
      this.decorator.doubleGrassPerChunk = 2;
      this.decorator.addTree(LOTRTreeType.PINE, 1000);
      this.decorator.addTree(LOTRTreeType.FIR, 200);
      this.decorator.addTree(LOTRTreeType.SPRUCE, 200);
      this.registerForestFlowers();
   }

   public float getChanceToSpawnAnimals() {
      return 0.5F;
   }
}
