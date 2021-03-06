package lotr.common.world.structure2;

import com.google.common.math.IntMath;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntities;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityGondorMan;
import lotr.common.world.structure.LOTRChestContents;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class LOTRWorldGenGondorWatchtower extends LOTRWorldGenGondorStructure {
   public LOTRWorldGenGondorWatchtower(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 4);
      this.setupRandomBlocks(random);
      int i1;
      int k1;
      int i2;
      int k2;
      int j1;
      if (this.restrictions) {
         i1 = 0;
         k1 = 0;

         for(i2 = -4; i2 <= 4; ++i2) {
            for(k2 = -4; k2 <= 4; ++k2) {
               j1 = this.getTopBlock(world, i2, k2) - 1;
               if (!this.isSurface(world, i2, j1, k2)) {
                  return false;
               }

               if (j1 < i1) {
                  i1 = j1;
               }

               if (j1 > k1) {
                  k1 = j1;
               }

               if (k1 - i1 > 8) {
                  return false;
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (i2 != 3 || k2 != 3) {
               for(j1 = 0; !this.isOpaque(world, i1, j1, k1) && this.getY(j1) >= 0; --j1) {
                  this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                  this.setGrassToDirt(world, i1, j1 - 1, k1);
               }

               if ((i2 != 3 || k2 != 2) && (k2 != 3 || i2 != 2)) {
                  if (i2 != 3 && k2 != 3) {
                     this.setBlockAndMetadata(world, i1, 0, k1, this.brickBlock, this.brickMeta);

                     for(j1 = 1; j1 <= 5; ++j1) {
                        this.setAir(world, i1, j1, k1);
                     }

                     this.setBlockAndMetadata(world, i1, 6, k1, this.plankBlock, this.plankMeta);

                     for(j1 = 7; j1 <= 9; ++j1) {
                        this.setAir(world, i1, j1, k1);
                     }
                  } else {
                     for(j1 = 1; j1 <= 9; ++j1) {
                        this.setBlockAndMetadata(world, i1, j1, k1, this.brickBlock, this.brickMeta);
                     }
                  }
               } else {
                  for(j1 = 1; j1 <= 9; ++j1) {
                     this.setBlockAndMetadata(world, i1, j1, k1, this.pillarBlock, this.pillarMeta);
                  }
               }
            }
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         for(k1 = -3; k1 <= 3; ++k1) {
            this.setBlockAndMetadata(world, i1, 10, k1, this.brickBlock, this.brickMeta);
         }
      }

      for(i1 = -3; i1 <= 3; ++i1) {
         this.setBlockAndMetadata(world, i1, 10, -4, this.brickStairBlock, 6);
         this.setBlockAndMetadata(world, i1, 10, 4, this.brickStairBlock, 7);
      }

      for(i1 = -2; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -4, 10, i1, this.brickStairBlock, 5);
         this.setBlockAndMetadata(world, 4, 10, i1, this.brickStairBlock, 4);
      }

      this.setBlockAndMetadata(world, -3, 10, -3, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -4, 10, -3, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 4, 10, -3, this.brickStairBlock, 6);
      this.setBlockAndMetadata(world, 3, 10, -3, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, -3, 10, 3, this.brickStairBlock, 5);
      this.setBlockAndMetadata(world, -4, 10, 3, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 4, 10, 3, this.brickStairBlock, 7);
      this.setBlockAndMetadata(world, 3, 10, 3, this.brickStairBlock, 4);
      this.setBlockAndMetadata(world, 0, 0, -3, this.brickBlock, this.brickMeta);
      this.setBlockAndMetadata(world, 0, 1, -3, this.doorBlock, 1);
      this.setBlockAndMetadata(world, 0, 2, -3, this.doorBlock, 8);

      for(i1 = 1; i1 <= 2; ++i1) {
         this.setBlockAndMetadata(world, -1, i1, -3, LOTRMod.brick, 5);
         this.setBlockAndMetadata(world, 1, i1, -3, LOTRMod.brick, 5);
      }

      this.setBlockAndMetadata(world, -1, 3, -4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 1, 3, -4, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, 0, 6, -3, LOTRMod.brick, 5);
      this.setBlockAndMetadata(world, 0, 6, 3, LOTRMod.brick, 5);
      this.setBlockAndMetadata(world, -3, 6, 0, LOTRMod.brick, 5);
      this.setBlockAndMetadata(world, 3, 6, 0, LOTRMod.brick, 5);
      this.placeWallBanner(world, 0, 5, -3, this.bannerType, 2);

      for(i1 = 1; i1 <= 9; ++i1) {
         this.setBlockAndMetadata(world, 0, i1, 2, Blocks.field_150468_ap, 2);
      }

      this.setBlockAndMetadata(world, 0, 10, 2, this.trapdoorBlock, 9);

      for(i1 = -2; i1 <= 2; ++i1) {
         if (IntMath.mod(i1, 2) == 0) {
            this.placeChest(world, random, -2, 1, i1, 4, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
            this.placeChest(world, random, 2, 1, i1, 5, LOTRChestContents.GONDOR_FORTRESS_DRINKS);
         } else {
            this.setBlockAndMetadata(world, -1, 1, i1, this.bedBlock, 3);
            this.setBlockAndMetadata(world, -2, 1, i1, this.bedBlock, 11);
            this.setBlockAndMetadata(world, 1, 1, i1, this.bedBlock, 1);
            this.setBlockAndMetadata(world, 2, 1, i1, this.bedBlock, 9);
         }
      }

      this.setBlockAndMetadata(world, -2, 3, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 2, 3, 0, Blocks.field_150478_aa, 1);
      this.setBlockAndMetadata(world, 0, 5, 0, LOTRMod.chandelier, 1);
      this.placeChest(world, random, -2, 7, -2, LOTRMod.chestLebethron, 4, LOTRChestContents.GONDOR_FORTRESS_SUPPLIES);
      this.setBlockAndMetadata(world, -2, 7, 0, LOTRMod.armorStand, 3);
      this.setBlockAndMetadata(world, -2, 8, 0, LOTRMod.armorStand, 7);
      this.setBlockAndMetadata(world, -2, 7, 2, Blocks.field_150467_bQ, 0);
      this.spawnItemFrame(world, -3, 8, -1, 1, this.getGondorFramedItem(random));
      this.spawnItemFrame(world, -3, 8, 1, 1, this.getGondorFramedItem(random));
      this.setBlockAndMetadata(world, 2, 7, -2, this.tableBlock, 0);
      this.setBlockAndMetadata(world, 2, 7, -1, Blocks.field_150462_ai, 0);
      this.setBlockAndMetadata(world, 2, 7, 0, Blocks.field_150333_U, 8);
      this.setBlockAndMetadata(world, 2, 7, 1, Blocks.field_150333_U, 8);
      this.setBlockAndMetadata(world, 2, 7, 2, Blocks.field_150333_U, 8);
      this.placeMug(world, random, 2, 8, 0, 1, LOTRFoods.GONDOR_DRINK);
      this.placePlateWithCertainty(world, random, 2, 8, 1, this.plateBlock, LOTRFoods.GONDOR);
      this.placeBarrel(world, random, 2, 8, 2, 5, LOTRFoods.GONDOR_DRINK);
      this.setBlockAndMetadata(world, 0, 9, 0, LOTRMod.chandelier, 1);

      for(i1 = -4; i1 <= 4; ++i1) {
         for(k1 = -4; k1 <= 4; ++k1) {
            i2 = Math.abs(i1);
            k2 = Math.abs(k1);
            if (i2 == 4 && k2 == 4) {
               this.setBlockAndMetadata(world, i1, 11, k1, this.brickBlock, this.brickMeta);
               this.setBlockAndMetadata(world, i1, 12, k1, this.brickBlock, this.brickMeta);
            } else if (i2 == 4 || k2 == 4) {
               if (IntMath.mod(i1 + k1, 2) == 1) {
                  this.setBlockAndMetadata(world, i1, 11, k1, this.brickWallBlock, this.brickWallMeta);
               } else {
                  this.setBlockAndMetadata(world, i1, 11, k1, this.brickBlock, this.brickMeta);
                  this.setBlockAndMetadata(world, i1, 12, k1, this.brickSlabBlock, this.brickSlabMeta);
               }
            }
         }
      }

      this.setBlockAndMetadata(world, 0, 11, 0, this.pillarBlock, this.pillarMeta);
      this.setBlockAndMetadata(world, 0, 12, 0, this.pillarBlock, this.pillarMeta);
      this.setBlockAndMetadata(world, 0, 13, 0, LOTRMod.brick, 5);
      this.placeBanner(world, 0, 14, 0, this.bannerType, 2);
      this.setBlockAndMetadata(world, 0, 11, -3, Blocks.field_150478_aa, 3);
      this.setBlockAndMetadata(world, 0, 11, 3, Blocks.field_150478_aa, 4);
      this.setBlockAndMetadata(world, -3, 11, 0, Blocks.field_150478_aa, 2);
      this.setBlockAndMetadata(world, 3, 11, 0, Blocks.field_150478_aa, 1);
      Class[] levyClasses = this.strFief.getLevyClasses();
      LOTREntityGondorMan soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(levyClasses[0], world);
      soldier.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(soldier, world, 0, 7, 0, 16);
      soldier = (LOTREntityGondorMan)LOTREntities.createEntityByClass(levyClasses[0], world);
      soldier.spawnRidingHorse = false;
      this.spawnNPCAndSetHome(soldier, world, 0, 1, 0, 16);
      i2 = 1 + random.nextInt(3);

      for(k2 = 0; k2 < i2; ++k2) {
         Class entityClass = levyClasses[0];
         if (random.nextInt(3) == 0) {
            entityClass = levyClasses[1];
         }

         LOTREntityGondorMan levyman = (LOTREntityGondorMan)LOTREntities.createEntityByClass(entityClass, world);
         levyman.spawnRidingHorse = false;
         this.spawnNPCAndSetHome(levyman, world, 0, 11, 1, 16);
      }

      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClasses(levyClasses[0], levyClasses[1]);
      respawner.setCheckRanges(16, -12, 8, 6);
      respawner.setSpawnRanges(3, -6, 6, 16);
      this.placeNPCRespawner(respawner, world, 0, 6, 0);
      return true;
   }
}
