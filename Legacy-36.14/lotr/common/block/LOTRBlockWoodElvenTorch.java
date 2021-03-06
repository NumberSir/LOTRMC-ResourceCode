package lotr.common.block;

import java.util.Random;

public class LOTRBlockWoodElvenTorch extends LOTRBlockTorch {
   public LOTRBlockTorch.TorchParticle createTorchParticle(Random random) {
      String s = "leafRed_" + (20 + random.nextInt(30));
      double x = -0.01D + (double)(random.nextFloat() * 0.02F);
      double y = -0.01D + (double)(random.nextFloat() * 0.02F);
      double z = -0.01D + (double)(random.nextFloat() * 0.02F);
      return new LOTRBlockTorch.TorchParticle(s, 0.0D, 0.0D, 0.0D, x, y, z);
   }
}
