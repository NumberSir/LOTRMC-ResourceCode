package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorFortWallCorner extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorFortWallCorner(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 0);
      this.setupRandomBlocks(random);

      for(int l = -8; l <= 8; ++l) {
         int i1;
         int k1;
         if (l >= 0) {
            i1 = l / 2;
            k1 = -((l + 1) / 2);
         } else {
            i1 = -(Math.abs(l) + 1) / 2;
            k1 = Math.abs(l) / 2;
         }

         this.findSurface(world, i1, k1);
         boolean pillar = Math.abs(l) == 3;
         int j1;
         if (pillar) {
            for(j1 = 4; (j1 >= 1 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.pillar2Block, this.pillar2Meta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
         } else {
            for(j1 = 4; (j1 >= 1 || !this.isOpaque(world, i1, j1, k1)) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
               this.setGrassToDirt(world, i1, j1 - 1, k1);
            }
         }

         this.setBlockAndMetadata(world, i1, 5, k1, this.brick2WallBlock, this.brick2WallMeta);
         if (pillar) {
            this.setBlockAndMetadata(world, i1, 6, k1, Blocks.field_150478_aa, 5);
         }

         if (IntMath.mod(l, 2) == 0) {
            if (l >= 0) {
               this.setBlockAndMetadata(world, i1, 4, k1 + 1, this.rockSlabBlock, this.rockSlabMeta | 8);
            } else {
               this.setBlockAndMetadata(world, i1 + 1, 4, k1, this.rockSlabBlock, this.rockSlabMeta | 8);
            }
         }
      }

      return true;
   }
}
