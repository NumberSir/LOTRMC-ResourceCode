package lotr.common.world.biome.variant;

public class LOTRBiomeVariantForest extends LOTRBiomeVariant {
   public LOTRBiomeVariantForest(int i, String s) {
      super(i, s, LOTRBiomeVariant.VariantScale.ALL);
      this.setTemperatureRainfall(0.0F, 0.3F);
      this.setTrees(8.0F);
      this.setGrass(2.0F);
   }
}
