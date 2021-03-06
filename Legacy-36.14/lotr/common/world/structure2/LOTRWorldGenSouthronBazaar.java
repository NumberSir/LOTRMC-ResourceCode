package lotr.common.world.structure2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRFoods;
import lotr.common.LOTRMod;
import lotr.common.entity.animal.LOTREntityBird;
import lotr.common.entity.animal.LOTREntityButterfly;
import lotr.common.entity.npc.LOTREntityNearHaradBlacksmith;
import lotr.common.entity.npc.LOTREntitySouthronBaker;
import lotr.common.entity.npc.LOTREntitySouthronBrewer;
import lotr.common.entity.npc.LOTREntitySouthronButcher;
import lotr.common.entity.npc.LOTREntitySouthronFarmer;
import lotr.common.entity.npc.LOTREntitySouthronFishmonger;
import lotr.common.entity.npc.LOTREntitySouthronFlorist;
import lotr.common.entity.npc.LOTREntitySouthronGoldsmith;
import lotr.common.entity.npc.LOTREntitySouthronLumberman;
import lotr.common.entity.npc.LOTREntitySouthronMason;
import lotr.common.entity.npc.LOTREntitySouthronMiner;
import lotr.common.entity.npc.LOTREntitySouthronTrader;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LOTRWorldGenSouthronBazaar extends LOTRWorldGenSouthronStructure {
   private static Class[] stalls = new Class[]{LOTRWorldGenSouthronBazaar.Lumber.class, LOTRWorldGenSouthronBazaar.Mason.class, LOTRWorldGenSouthronBazaar.Fish.class, LOTRWorldGenSouthronBazaar.Baker.class, LOTRWorldGenSouthronBazaar.Goldsmith.class, LOTRWorldGenSouthronBazaar.Farmer.class, LOTRWorldGenSouthronBazaar.Blacksmith.class, LOTRWorldGenSouthronBazaar.Brewer.class, LOTRWorldGenSouthronBazaar.Miner.class, LOTRWorldGenSouthronBazaar.Florist.class, LOTRWorldGenSouthronBazaar.Butcher.class};

   public LOTRWorldGenSouthronBazaar(boolean flag) {
      super(flag);
   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      this.setOriginAndRotation(world, i, j, k, rotation, 10);
      this.setupRandomBlocks(random);
      int minHeight;
      int maxHeight;
      int j1;
      if (this.restrictions) {
         minHeight = 0;
         maxHeight = 0;

         for(j1 = -13; j1 <= 13; ++j1) {
            for(int k1 = -9; k1 <= 9; ++k1) {
               int j1 = this.getTopBlock(world, j1, k1) - 1;
               if (!this.isSurface(world, j1, j1, k1)) {
                  return false;
               }

               if (j1 < minHeight) {
                  minHeight = j1;
               }

               if (j1 > maxHeight) {
                  maxHeight = j1;
               }

               if (maxHeight - minHeight > 12) {
                  return false;
               }
            }
         }
      }

      for(minHeight = -13; minHeight <= 13; ++minHeight) {
         for(maxHeight = -9; maxHeight <= 9; ++maxHeight) {
            for(j1 = 1; j1 <= 8; ++j1) {
               this.setAir(world, minHeight, j1, maxHeight);
            }

            for(j1 = -1; !this.isOpaque(world, minHeight, j1, maxHeight) && this.getY(j1) >= 0; --j1) {
               this.setBlockAndMetadata(world, minHeight, j1, maxHeight, this.stoneBlock, this.stoneMeta);
               this.setGrassToDirt(world, minHeight, j1 - 1, maxHeight);
            }
         }
      }

      this.loadStrScan("southron_bazaar");
      this.associateBlockMetaAlias("STONE", this.stoneBlock, this.stoneMeta);
      this.associateBlockMetaAlias("BRICK", this.brickBlock, this.brickMeta);
      this.associateBlockMetaAlias("BRICK_SLAB", this.brickSlabBlock, this.brickSlabMeta);
      this.associateBlockMetaAlias("BRICK_SLAB_INV", this.brickSlabBlock, this.brickSlabMeta | 8);
      this.associateBlockAlias("BRICK_STAIR", this.brickStairBlock);
      this.associateBlockMetaAlias("PILLAR", this.pillarBlock, this.pillarMeta);
      this.associateBlockMetaAlias("BRICK2", this.brick2Block, this.brick2Meta);
      this.associateBlockMetaAlias("BRICK2_SLAB", this.brick2SlabBlock, this.brick2SlabMeta);
      this.associateBlockMetaAlias("BRICK2_SLAB_INV", this.brick2SlabBlock, this.brick2SlabMeta | 8);
      this.associateBlockMetaAlias("FENCE", this.fenceBlock, this.fenceMeta);
      this.associateBlockAlias("FENCE_GATE", this.fenceGateBlock);
      this.associateBlockMetaAlias("BEAM", this.woodBeamBlock, this.woodBeamMeta);
      this.generateStrScan(world, random, 0, 0, 0);
      this.placeArmorStand(world, -4, 1, -2, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetNearHaradWarlord), null, null, null});
      this.placeAnimalJar(world, -3, 1, -7, LOTRMod.butterflyJar, 0, new LOTREntityButterfly(world));
      this.placeAnimalJar(world, 11, 1, -1, LOTRMod.birdCageWood, 0, (EntityLiving)null);
      this.placeAnimalJar(world, 3, 1, 7, LOTRMod.birdCage, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, -9, 3, 0, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      this.placeAnimalJar(world, 4, 3, 3, LOTRMod.birdCageWood, 0, new LOTREntityBird(world));
      Class[] stallArray = this.getStallClasses();
      List stallClasses = Arrays.asList(Arrays.copyOf(stallArray, stallArray.length));
      Collections.shuffle(stallClasses, random);

      try {
         LOTRWorldGenStructureBase2 stall0 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(0)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall1 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(1)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall2 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(2)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall3 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(3)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall4 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(4)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         LOTRWorldGenStructureBase2 stall5 = (LOTRWorldGenStructureBase2)((Class)stallClasses.get(5)).getConstructor(Boolean.TYPE).newInstance(this.notifyChanges);
         this.generateSubstructure(stall0, world, random, -8, 1, -4, 2);
         this.generateSubstructure(stall1, world, random, 0, 1, -4, 2);
         this.generateSubstructure(stall2, world, random, 8, 1, -4, 2);
         this.generateSubstructure(stall3, world, random, -8, 1, 4, 0);
         this.generateSubstructure(stall4, world, random, 0, 1, 4, 0);
         this.generateSubstructure(stall5, world, random, 8, 1, 4, 0);
      } catch (Exception var15) {
         var15.printStackTrace();
      }

      return true;
   }

   protected Class[] getStallClasses() {
      return stalls;
   }

   private static class Butcher extends LOTRWorldGenSouthronStructure {
      public Butcher(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150460_al, 2);
         this.placeKebabStand(world, random, 0, 2, 1, LOTRMod.kebabStand, 3);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.muttonRaw, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.camelRaw, 1 + random.nextInt(3), 1), true);
         LOTREntitySouthronTrader trader = new LOTREntitySouthronButcher(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Florist extends LOTRWorldGenSouthronStructure {
      public Florist(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.placeFlowerPot(world, -2, 2, 0, this.getRandomFlower(world, random));
         this.placeFlowerPot(world, 2, 2, 0, this.getRandomFlower(world, random));
         this.setBlockAndMetadata(world, -1, 0, 1, Blocks.field_150349_c, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.doubleFlower, 3);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.doubleFlower, 11);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150349_c, 0);
         this.plantFlower(world, random, 1, 2, 1);
         this.setBlockAndMetadata(world, 1, 1, 0, this.trapdoorBlock, 12);
         this.setBlockAndMetadata(world, 0, 1, 1, this.trapdoorBlock, 15);
         LOTREntitySouthronTrader trader = new LOTREntitySouthronFlorist(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Miner extends LOTRWorldGenSouthronStructure {
      public Miner(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.oreTin, 0);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.oreCopper, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.oreCopper, 0);
         this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(LOTRMod.pickaxeBronze));
         LOTREntitySouthronTrader trader = new LOTREntitySouthronMiner(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Brewer extends LOTRWorldGenSouthronStructure {
      public Brewer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.stairsCedar, 6);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.barrel, 2);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.stairsCedar, 6);
         this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.barrel, 2);
         this.placeMug(world, random, -2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
         this.placeMug(world, random, 2, 2, 0, 1, LOTRFoods.SOUTHRON_DRINK);
         LOTREntitySouthronTrader trader = new LOTREntitySouthronBrewer(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Blacksmith extends LOTRWorldGenSouthronStructure {
      public Blacksmith(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, Blocks.field_150467_bQ, 3);
         this.placeArmorStand(world, 1, 1, 1, 0, new ItemStack[]{new ItemStack(LOTRMod.helmetNearHarad), new ItemStack(LOTRMod.bodyNearHarad), null, null});
         this.placeWeaponRack(world, -2, 2, 0, 1, (new LOTRWorldGenSouthronBazaar(false)).getRandomHaradWeapon(random));
         this.placeWeaponRack(world, 2, 2, 0, 3, (new LOTRWorldGenSouthronBazaar(false)).getRandomHaradWeapon(random));
         LOTREntityNearHaradBlacksmith trader = new LOTREntityNearHaradBlacksmith(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Farmer extends LOTRWorldGenSouthronStructure {
      public Farmer(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, Blocks.field_150383_bp, 3);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150407_cf, 0);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150407_cf, 0);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.orange, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.woodPlateBlock, new ItemStack(LOTRMod.lettuce, 1 + random.nextInt(3), 1), true);
         LOTREntitySouthronFarmer trader = new LOTREntitySouthronFarmer(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Goldsmith extends LOTRWorldGenSouthronStructure {
      public Goldsmith(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, -1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, 1, 1, -1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.goldBars, 0);
         this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, -1, LOTRMod.birdCage, 2);
         this.setBlockAndMetadata(world, random.nextBoolean() ? -1 : 1, 2, 1, LOTRMod.birdCage, 3);
         LOTREntitySouthronTrader trader = new LOTREntitySouthronGoldsmith(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Baker extends LOTRWorldGenSouthronStructure {
      public Baker(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, 0, 1, 1, Blocks.field_150460_al, 2);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.planks2, 2);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.planks2, 2);
         this.placePlate_item(world, random, -1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151025_P, 1 + random.nextInt(3)), true);
         this.placePlate_item(world, random, 1, 2, 1, LOTRMod.ceramicPlateBlock, new ItemStack(LOTRMod.oliveBread, 1 + random.nextInt(3)), true);
         this.placeFlowerPot(world, random.nextBoolean() ? -2 : 2, 2, 0, this.getRandomFlower(world, random));
         LOTREntitySouthronTrader trader = new LOTREntitySouthronBaker(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Fish extends LOTRWorldGenSouthronStructure {
      public Fish(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, 1, 1, 1, Blocks.field_150383_bp, 3);
         this.setBlockAndMetadata(world, -1, 1, -1, Blocks.field_150360_v, 0);
         this.placePlate_item(world, random, -2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 0), true);
         this.placePlate_item(world, random, 2, 2, 0, LOTRMod.ceramicPlateBlock, new ItemStack(Items.field_151115_aP, 1 + random.nextInt(3), 1), true);
         LOTREntitySouthronTrader trader = new LOTREntitySouthronFishmonger(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Mason extends LOTRWorldGenSouthronStructure {
      public Mason(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.brick, 15);
         this.setBlockAndMetadata(world, -1, 2, 1, LOTRMod.slabSingle4, 0);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.brick3, 13);
         this.placeWeaponRack(world, 1, 2, 1, 2, new ItemStack(LOTRMod.pickaxeBronze));
         LOTREntitySouthronTrader trader = new LOTREntitySouthronMason(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }

   private static class Lumber extends LOTRWorldGenSouthronStructure {
      public Lumber(boolean flag) {
         super(flag);
      }

      public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
         this.setOriginAndRotation(world, i, j, k, rotation, 0);
         this.setupRandomBlocks(random);
         this.setBlockAndMetadata(world, -1, 1, 1, LOTRMod.wood4, 10);
         this.setBlockAndMetadata(world, 1, 1, 1, LOTRMod.wood4, 2);
         this.setBlockAndMetadata(world, 1, 2, 1, LOTRMod.wood4, 2);
         this.placeFlowerPot(world, -2, 2, 0, new ItemStack(LOTRMod.sapling4, 1, 2));
         this.placeFlowerPot(world, 2, 2, 0, new ItemStack(LOTRMod.sapling8, 1, 3));
         LOTREntitySouthronTrader trader = new LOTREntitySouthronLumberman(world);
         this.spawnNPCAndSetHome(trader, world, 0, 1, 0, 4);
         return true;
      }
   }
}
