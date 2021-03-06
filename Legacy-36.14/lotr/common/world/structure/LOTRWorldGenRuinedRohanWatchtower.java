package lotr.common.world.structure;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.world.biome.LOTRBiome;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenRuinedRohanWatchtower extends LOTRWorldGenStructureBase {
   private Block plankBlock;
   private int plankMeta;
   private Block woodBlock;
   private int woodMeta;
   private Block stairBlock;

   public LOTRWorldGenRuinedRohanWatchtower(boolean flag) {
      super(flag);
      this.plankBlock = LOTRMod.planks;
      this.plankMeta = 3;
      this.woodBlock = LOTRMod.wood;
      this.woodMeta = 3;
      this.stairBlock = LOTRMod.stairsCharred;
   }

   public boolean func_76484_a(World world, Random random, int i, int j, int k) {
      if (!this.restrictions || world.func_147439_a(i, j - 1, k) == Blocks.field_150349_c && world.func_72807_a(i, k) == LOTRBiome.rohanUrukHighlands) {
         int height = 5 + random.nextInt(4);
         j += height;
         int i1;
         if (this.restrictions) {
            for(i1 = i - 4; i1 <= i + 4; ++i1) {
               for(int j1 = j - 3; j1 <= j + 4; ++j1) {
                  for(int k1 = k - 4; k1 <= k + 4; ++k1) {
                     if (!world.func_147437_c(i1, j1, k1)) {
                        return false;
                     }
                  }
               }
            }
         }

         this.generateBasicStructure(world, random, i, j, k);
         i1 = random.nextInt(4);
         if (!this.restrictions && this.usingPlayer != null) {
            i1 = this.usingPlayerRotation();
         }

         switch(i1) {
         case 0:
            return this.generateFacingSouth(world, random, i, j, k);
         case 1:
            return this.generateFacingWest(world, random, i, j, k);
         case 2:
            return this.generateFacingNorth(world, random, i, j, k);
         case 3:
            return this.generateFacingEast(world, random, i, j, k);
         default:
            return true;
         }
      } else {
         return false;
      }
   }

   private void generateBasicStructure(World world, Random random, int i, int j, int k) {
      int k1;
      for(k1 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i - 3, k1, k - 3) && k1 >= 0; --k1) {
         this.func_150516_a(world, i - 3, k1, k - 3, this.plankBlock, this.plankMeta);
      }

      for(k1 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i - 3, k1, k + 3) && k1 >= 0; --k1) {
         this.func_150516_a(world, i - 3, k1, k + 3, this.plankBlock, this.plankMeta);
      }

      for(k1 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i + 3, k1, k - 3) && k1 >= 0; --k1) {
         this.func_150516_a(world, i + 3, k1, k - 3, this.plankBlock, this.plankMeta);
      }

      for(k1 = j + 3 - random.nextInt(8); !LOTRMod.isOpaque(world, i + 3, k1, k + 3) && k1 >= 0; --k1) {
         this.func_150516_a(world, i + 3, k1, k + 3, this.plankBlock, this.plankMeta);
      }

      for(k1 = i - 2; k1 <= i + 2; ++k1) {
         for(int k1 = k - 2; k1 <= k + 2; ++k1) {
            if (random.nextInt(4) != 0) {
               this.func_150516_a(world, k1, j, k1, this.plankBlock, this.plankMeta);
            }
         }
      }

      for(k1 = i - 2 + random.nextInt(3); k1 <= i + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, k1, j, k - 3, this.woodBlock, this.woodMeta | 4);
      }

      for(k1 = i - 2 + random.nextInt(3); k1 <= i + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, k1, j, k + 3, this.woodBlock, this.woodMeta | 4);
      }

      for(k1 = i - 2 + random.nextInt(3); k1 <= i + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, k1, j, k - 4, this.stairBlock, 6);
      }

      for(k1 = i - 2 + random.nextInt(3); k1 <= i + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, k1, j, k + 4, this.stairBlock, 7);
      }

      for(k1 = k - 2 + random.nextInt(3); k1 <= k + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, i - 3, j, k1, this.woodBlock, this.woodMeta | 8);
      }

      for(k1 = k - 2 + random.nextInt(3); k1 <= k + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, i + 3, j, k1, this.woodBlock, this.woodMeta | 8);
      }

      for(k1 = k - 2 + random.nextInt(3); k1 <= k + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, i - 4, j, k1, this.stairBlock, 4);
      }

      for(k1 = k - 2 + random.nextInt(3); k1 <= k + 2 - random.nextInt(3); ++k1) {
         this.func_150516_a(world, i + 4, j, k1, this.stairBlock, 5);
      }

   }

   private boolean generateFacingSouth(World world, Random random, int i, int j, int k) {
      int k1;
      for(k1 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i, k1, k + 3) && k1 >= 0; --k1) {
         this.func_150516_a(world, i, k1, k + 3, this.plankBlock, this.plankMeta);
      }

      for(k1 = k - 2; k1 <= k + 2; ++k1) {
         int k2 = Math.abs(k - k1);

         int j1;
         int j2;
         for(j1 = j - 1; !LOTRMod.isOpaque(world, i - 3, j1, k1) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (k2 == 2 && j2 % 4 == 1 || k2 == 1 && j2 % 2 == 0 || k2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i - 3, j1, k1, this.woodBlock, this.woodMeta);
            }
         }

         for(j1 = j - 1; !LOTRMod.isOpaque(world, i + 3, j1, k1) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (k2 == 2 && j2 % 4 == 1 || k2 == 1 && j2 % 2 == 0 || k2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i + 3, j1, k1, this.woodBlock, this.woodMeta);
            }
         }
      }

      return true;
   }

   private boolean generateFacingWest(World world, Random random, int i, int j, int k) {
      int i1;
      for(i1 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i - 3, i1, k) && i1 >= 0; --i1) {
         this.func_150516_a(world, i - 3, i1, k, this.plankBlock, this.plankMeta);
      }

      for(i1 = i - 2; i1 <= i + 2; ++i1) {
         int i2 = Math.abs(i - i1);

         int j1;
         int j2;
         for(j1 = j - 1; !LOTRMod.isOpaque(world, i1, j1, k - 3) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (i2 == 2 && j2 % 4 == 1 || i2 == 1 && j2 % 2 == 0 || i2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i1, j1, k - 3, this.woodBlock, this.woodMeta);
            }
         }

         for(j1 = j - 1; !LOTRMod.isOpaque(world, i1, j1, k + 3) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (i2 == 2 && j2 % 4 == 1 || i2 == 1 && j2 % 2 == 0 || i2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i1, j1, k + 3, this.woodBlock, this.woodMeta);
            }
         }
      }

      return true;
   }

   private boolean generateFacingNorth(World world, Random random, int i, int j, int k) {
      int k1;
      for(k1 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i, k1, k - 3) && k1 >= 0; --k1) {
         this.func_150516_a(world, i, k1, k - 3, this.plankBlock, this.plankMeta);
      }

      for(k1 = k - 2; k1 <= k + 2; ++k1) {
         int k2 = Math.abs(k - k1);

         int j1;
         int j2;
         for(j1 = j - 1; !LOTRMod.isOpaque(world, i - 3, j1, k1) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (k2 == 2 && j2 % 4 == 1 || k2 == 1 && j2 % 2 == 0 || k2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i - 3, j1, k1, this.woodBlock, this.woodMeta);
            }
         }

         for(j1 = j - 1; !LOTRMod.isOpaque(world, i + 3, j1, k1) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (k2 == 2 && j2 % 4 == 1 || k2 == 1 && j2 % 2 == 0 || k2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i + 3, j1, k1, this.woodBlock, this.woodMeta);
            }
         }
      }

      return true;
   }

   private boolean generateFacingEast(World world, Random random, int i, int j, int k) {
      int i1;
      for(i1 = j - 1 - random.nextInt(4); !LOTRMod.isOpaque(world, i + 3, i1, k) && i1 >= 0; --i1) {
         this.func_150516_a(world, i + 3, i1, k, this.plankBlock, this.plankMeta);
      }

      for(i1 = i - 2; i1 <= i + 2; ++i1) {
         int i2 = Math.abs(i - i1);

         int j1;
         int j2;
         for(j1 = j - 1; !LOTRMod.isOpaque(world, i1, j1, k - 3) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (i2 == 2 && j2 % 4 == 1 || i2 == 1 && j2 % 2 == 0 || i2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i1, j1, k - 3, this.woodBlock, this.woodMeta);
            }
         }

         for(j1 = j - 1; !LOTRMod.isOpaque(world, i1, j1, k + 3) && j1 >= 0; --j1) {
            j2 = j - j1;
            if (i2 == 2 && j2 % 4 == 1 || i2 == 1 && j2 % 2 == 0 || i2 == 0 && j2 % 4 == 3 && random.nextInt(3) == 0) {
               this.func_150516_a(world, i1, j1, k + 3, this.woodBlock, this.woodMeta);
            }
         }
      }

      return true;
   }
}
