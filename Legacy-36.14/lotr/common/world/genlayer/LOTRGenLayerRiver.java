package lotr.common.world.genlayer;

import net.minecraft.world.World;

public class LOTRGenLayerRiver extends LOTRGenLayer {
   public static final int RANDOM_RIVER = 1;
   public static final int MAP_RIVER = 2;

   public LOTRGenLayerRiver(long l, LOTRGenLayer layer) {
      super(l);
      this.lotrParent = layer;
   }

   public int[] getInts(World world, int i, int k, int xSize, int zSize) {
      int i1 = i - 1;
      int k1 = k - 1;
      int i2 = xSize + 2;
      int k2 = zSize + 2;
      int[] riverInit = this.lotrParent.getInts(world, i1, k1, i2, k2);
      int[] ints = LOTRIntCache.get(world).getIntArray(xSize * zSize);

      for(int k3 = 0; k3 < zSize; ++k3) {
         for(int i3 = 0; i3 < xSize; ++i3) {
            int centre = riverInit[i3 + 1 + (k3 + 1) * i2];
            int xn = riverInit[i3 + 0 + (k3 + 1) * i2];
            int xp = riverInit[i3 + 2 + (k3 + 1) * i2];
            int zn = riverInit[i3 + 1 + (k3 + 0) * i2];
            int zp = riverInit[i3 + 1 + (k3 + 2) * i2];
            if (centre == xn && centre == zn && centre == xp && centre == zp) {
               ints[i3 + k3 * xSize] = 0;
            } else {
               ints[i3 + k3 * xSize] = 1;
            }
         }
      }

      return ints;
   }
}
