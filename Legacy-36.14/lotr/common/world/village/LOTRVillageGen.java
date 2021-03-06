package lotr.common.world.village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lotr.common.LOTRConfig;
import lotr.common.LOTRMod;
import lotr.common.util.CentredSquareArray;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.LOTRBiome;
import lotr.common.world.genlayer.LOTRGenLayerWorld;
import lotr.common.world.map.LOTRFixedStructures;
import lotr.common.world.map.LOTRMountains;
import lotr.common.world.map.LOTRRoadType;
import lotr.common.world.map.LOTRRoads;
import lotr.common.world.map.LOTRWaypoint;
import lotr.common.world.structure2.LOTRWorldGenStructureBase2;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class LOTRVillageGen {
   protected LOTRBiome villageBiome;
   private List spawnBiomes;
   private static Random villageRand = new Random();
   protected int gridScale;
   protected int gridRandomDisplace;
   protected float spawnChance;
   protected int villageChunkRadius;
   protected int fixedVillageChunkRadius;
   private List fixedLocations = new ArrayList();
   public static final double SQRT2 = Math.sqrt(2.0D);

   public LOTRVillageGen(LOTRBiome biome) {
      this.villageBiome = biome;
      this.spawnBiomes = new ArrayList();
      this.spawnBiomes.add(this.villageBiome);
   }

   public LocationInfo addFixedLocation(LOTRWaypoint wp, int rotation, String name) {
      return this.addFixedLocation(wp, 0, 0, rotation, name);
   }

   public LocationInfo addFixedLocationMapOffset(LOTRWaypoint wp, int addX, int addZ, int rotation, String name) {
      return this.addFixedLocation(wp, addX * LOTRGenLayerWorld.scale, addZ * LOTRGenLayerWorld.scale, rotation, name);
   }

   public LocationInfo addFixedLocation(LOTRWaypoint wp, int addX, int addZ, int rotation, String name) {
      LocationInfo loc = (new LocationInfo(wp.getXCoord() + addX, wp.getZCoord() + addZ, rotation, name)).setFixedLocation(wp);
      this.fixedLocations.add(loc);
      return loc;
   }

   public final LOTRVillageGen.AbstractInstance createAndSetupVillageInstance(World world, int i, int k, Random random, LocationInfo location) {
      LOTRVillageGen.AbstractInstance instance = this.createVillageInstance(world, i, k, random, location);
      instance.setupBaseAndVillageProperties();
      return instance;
   }

   protected abstract LOTRVillageGen.AbstractInstance createVillageInstance(World var1, int var2, int var3, Random var4, LocationInfo var5);

   private static void seedVillageRand(World world, int i, int k) {
      long seed = (long)i * 6890360793007L + (long)k * 456879569029062L + world.func_72912_H().func_76063_b() + 274893855L;
      villageRand.setSeed(seed);
   }

   private LocationInfo isVillageCentre(World world, int chunkX, int chunkZ) {
      LOTRWorldChunkManager worldChunkMgr = (LOTRWorldChunkManager)world.func_72959_q();
      LOTRVillagePositionCache cache = worldChunkMgr.getVillageCache(this);
      LocationInfo cacheLocation = cache.getLocationAt(chunkX, chunkZ);
      if (cacheLocation != null) {
         return cacheLocation;
      } else {
         int i1;
         int k1;
         int villageRange;
         if (hasFixedSettlements(world)) {
            Iterator var7 = this.fixedLocations.iterator();

            while(var7.hasNext()) {
               LocationInfo loc = (LocationInfo)var7.next();
               i1 = loc.posX >> 4;
               k1 = loc.posZ >> 4;
               if (chunkX == i1 && chunkZ == k1) {
                  return cache.markResult(chunkX, chunkZ, loc);
               }

               villageRange = Math.max(this.villageChunkRadius, this.fixedVillageChunkRadius);
               if (Math.abs(chunkX - i1) <= villageRange && Math.abs(chunkZ - k1) <= villageRange) {
                  return cache.markResult(chunkX, chunkZ, LocationInfo.NONE_HERE);
               }
            }
         }

         int i2 = MathHelper.func_76128_c((double)chunkX / (double)this.gridScale);
         int k2 = MathHelper.func_76128_c((double)chunkZ / (double)this.gridScale);
         seedVillageRand(world, i2, k2);
         i2 *= this.gridScale;
         k2 *= this.gridScale;
         i2 += MathHelper.func_76136_a(villageRand, -this.gridRandomDisplace, this.gridRandomDisplace);
         k2 += MathHelper.func_76136_a(villageRand, -this.gridRandomDisplace, this.gridRandomDisplace);
         if (chunkX == i2 && chunkZ == k2) {
            i1 = chunkX * 16 + 8;
            k1 = chunkZ * 16 + 8;
            villageRange = this.villageChunkRadius * 16;
            if (villageRand.nextFloat() < this.spawnChance) {
               int diagRange = (int)Math.round((double)(villageRange + 8) * SQRT2);
               boolean anythingNear = false;
               anythingNear = LOTRRoads.isRoadNear(i1, k1, diagRange) >= 0.0F;
               if (!anythingNear) {
                  anythingNear = LOTRMountains.mountainNear(i1, k1, diagRange);
                  if (!anythingNear) {
                     anythingNear = LOTRFixedStructures.structureNear(world, i1, k1, diagRange);
                  }
               }

               if (!anythingNear) {
                  seedVillageRand(world, i1, k1);
                  LocationInfo loc = LocationInfo.RANDOM_GEN_HERE;
                  LOTRVillageGen.AbstractInstance instance = this.createAndSetupVillageInstance(world, i1, k1, villageRand, loc);
                  boolean flat = instance.isFlat();
                  if (worldChunkMgr.func_76940_a(i1, k1, villageRange, this.spawnBiomes) && worldChunkMgr.areVariantsSuitableVillage(i1, k1, villageRange, flat)) {
                     return cache.markResult(chunkX, chunkZ, loc);
                  }
               }
            }
         }

         return cache.markResult(chunkX, chunkZ, LocationInfo.NONE_HERE);
      }
   }

   private List getNearbyVillages(World world, int chunkX, int chunkZ) {
      List villages = new ArrayList();
      int checkRange = Math.max(this.villageChunkRadius, this.fixedVillageChunkRadius);

      for(int i = chunkX - checkRange; i <= chunkX + checkRange; ++i) {
         for(int k = chunkZ - checkRange; k <= chunkZ + checkRange; ++k) {
            LocationInfo loc = this.isVillageCentre(world, i, k);
            if (loc.isPresent()) {
               int centreX;
               int centreZ;
               if (loc.isFixedLocation()) {
                  centreX = loc.posX;
                  centreZ = loc.posZ;
               } else {
                  centreX = (i << 4) + 8;
                  centreZ = (k << 4) + 8;
               }

               seedVillageRand(world, centreX, centreZ);
               LOTRVillageGen.AbstractInstance instance = this.createAndSetupVillageInstance(world, centreX, centreZ, villageRand, loc);
               villages.add(instance);
            }
         }
      }

      return villages;
   }

   public List getNearbyVillagesAtPosition(World world, int i, int k) {
      int chunkX = i >> 4;
      int chunkZ = k >> 4;
      return this.getNearbyVillages(world, chunkX, chunkZ);
   }

   public boolean anyFixedVillagesAt(World world, int i, int k) {
      if (!hasFixedSettlements(world)) {
         return false;
      } else {
         int checkRange = this.fixedVillageChunkRadius + 2;
         checkRange <<= 4;
         Iterator var5 = this.fixedLocations.iterator();

         int dx;
         int dz;
         do {
            if (!var5.hasNext()) {
               return false;
            }

            LocationInfo loc = (LocationInfo)var5.next();
            dx = Math.abs(loc.posX - i);
            dz = Math.abs(loc.posZ - k);
         } while(dx > checkRange || dz > checkRange);

         return true;
      }
   }

   public void generateInChunk(World world, int i, int k) {
      List villages = this.getNearbyVillagesAtPosition(world, i, k);
      Iterator var5 = villages.iterator();

      while(var5.hasNext()) {
         LOTRVillageGen.AbstractInstance instance = (LOTRVillageGen.AbstractInstance)var5.next();
         instance.setupVillageStructures();
         this.generateInstanceInChunk(instance, world, i, k);
      }

   }

   public void generateInstanceInChunk(LOTRVillageGen.AbstractInstance instance, World world, int i, int k) {
      for(int i1 = i; i1 <= i + 15; ++i1) {
         for(int k1 = k; k1 <= k + 15; ++k1) {
            BiomeGenBase biome = world.func_72807_a(i1, k1);
            Object[] pathData = this.getHeight_getPath_isSlab(instance, world, i1, k1, biome);
            LOTRRoadType pathType = (LOTRRoadType)pathData[1];
            if (pathType != null) {
               int j1 = (Integer)pathData[0];
               boolean isSlab = (Boolean)pathData[2];
               instance.setupWorldPositionSeed(i1, k1);
               LOTRRoadType.RoadBlock roadblock = pathType.getBlock(instance.instanceRand, biome, true, isSlab);
               LOTRRoadType.RoadBlock roadblockSolid = pathType.getBlock(instance.instanceRand, biome, false, false);
               world.func_147465_d(i1, j1, k1, roadblock.block, roadblock.meta, 2);
               world.func_147465_d(i1, j1 - 1, k1, roadblockSolid.block, roadblockSolid.meta, 2);
               Block above = world.func_147439_a(i1, j1 + 1, k1);
               if (!above.func_149718_j(world, i1, j1 + 1, k1)) {
                  world.func_147465_d(i1, j1 + 1, k1, Blocks.field_150350_a, 0, 3);
               }
            }

            instance.setupWorldPositionSeed(i1, k1);
            Iterator var16 = instance.structures.iterator();

            while(var16.hasNext()) {
               LOTRVillageGen.StructureInfo struct = (LOTRVillageGen.StructureInfo)var16.next();
               int[] coords = instance.getWorldCoords(struct.posX, struct.posZ);
               if (i1 == coords[0] && k1 == coords[1]) {
                  int j1 = world.func_72825_h(i1, k1);
                  int minHeight = 62;
                  int terrainTypeMinHeight = world.field_73011_w.field_76577_b.getMinimumSpawnHeight(world);
                  if (terrainTypeMinHeight < 62) {
                     minHeight = terrainTypeMinHeight - 1;
                  }

                  if (j1 > minHeight) {
                     struct.structure.generateWithSetRotation(world, instance.instanceRand, i1, j1, k1, instance.getStructureRotation(struct.rotation));
                  }
               }
            }
         }
      }

   }

   private Object[] getHeight_getPath_isSlab(LOTRVillageGen.AbstractInstance instance, World world, int i, int k, BiomeGenBase biome) {
      instance.setupWorldPositionSeed(i, k);
      int[] coords = instance.getRelativeCoords(i, k);
      int i1 = coords[0];
      int k1 = coords[1];
      LOTRRoadType road = instance.getPath(instance.instanceRand, i1, k1);
      boolean isPath = false;
      boolean isSlab = false;
      int j1 = this.getTopTerrainBlock(world, i, k, biome, true);
      if (road != null && j1 > 0 && LOTRWorldGenStructureBase2.isSurfaceStatic(world, i, j1, k)) {
         isPath = true;
         int slabRange = 1;
         CentredSquareArray slabArray = new CentredSquareArray(slabRange);
         slabArray.fill(j1);

         for(int i2 = -slabRange; i2 <= slabRange; ++i2) {
            for(int k2 = -slabRange; k2 <= slabRange; ++k2) {
               int i3 = i + i2;
               int k3 = k + k2;
               if (i2 != 0 || k2 != 0) {
                  int j2 = this.getTopTerrainBlock(world, i3, k3, biome, true);
                  if (j2 > 0 && j2 < j1) {
                     slabArray.set(i2, k2, j2);
                  }
               }
            }
         }

         if ((Integer)slabArray.get(-1, 0) >= j1 && (Integer)slabArray.get(1, 0) >= j1 && (Integer)slabArray.get(0, -1) >= j1 && (Integer)slabArray.get(0, 1) >= j1) {
            if ((Integer)slabArray.get(-1, -1) < j1 || (Integer)slabArray.get(1, -1) < j1 || (Integer)slabArray.get(-1, 1) < j1 || (Integer)slabArray.get(1, 1) < j1) {
               isSlab = true;
            }
         } else {
            isSlab = true;
         }

         if (isSlab && world.func_147439_a(i, j1 + 1, k).func_149662_c()) {
            isSlab = false;
         }
      }

      Object[] ret = new Object[3];
      if (isPath) {
         ret[0] = j1;
         ret[1] = road;
         ret[2] = isSlab;
      } else {
         ret[0] = -1;
         ret[1] = null;
         ret[2] = false;
      }

      return ret;
   }

   private int getTopTerrainBlock(World world, int i, int k, BiomeGenBase biome, boolean acceptSlab) {
      int j = world.func_72825_h(i, k) - 1;

      while(true) {
         if (!world.func_147439_a(i, j + 1, k).func_149688_o().func_76224_d()) {
            Block block = world.func_147439_a(i, j, k);
            Block below = world.func_147439_a(i, j - 1, k);
            if (block.func_149662_c() || acceptSlab && block instanceof BlockSlab && below.func_149662_c()) {
               return j;
            }

            --j;
            if (j > 62) {
               continue;
            }
         }

         return -1;
      }
   }

   public void generateCompleteVillageInstance(LOTRVillageGen.AbstractInstance instance, World world, int i, int k) {
      instance.setupVillageStructures();
      int checkRange = Math.max(this.villageChunkRadius, this.fixedVillageChunkRadius);

      for(int i1 = -checkRange; i1 <= checkRange; ++i1) {
         for(int k1 = -checkRange; k1 <= checkRange; ++k1) {
            int i2 = i - 8 + i1 * 16;
            int k2 = k - 8 + k1 * 16;
            this.generateInstanceInChunk(instance, world, i2, k2);
         }
      }

   }

   private static boolean hasFixedSettlements(World world) {
      if (!LOTRConfig.generateFixedSettlements) {
         return false;
      } else {
         return world.func_72912_H().func_76067_t() != LOTRMod.worldTypeMiddleEarthClassic;
      }
   }

   public abstract static class AbstractInstance {
      protected LOTRBiome instanceVillageBiome;
      private World theWorld;
      private Random instanceRand;
      private long instanceRandSeed;
      private int centreX;
      private int centreZ;
      private int rotationMode;
      private List structures = new ArrayList();
      protected final LocationInfo locationInfo;

      protected AbstractInstance(LOTRVillageGen village, World world, int i, int k, Random random, LocationInfo loc) {
         this.instanceVillageBiome = village.villageBiome;
         this.theWorld = world;
         this.instanceRand = new Random();
         this.instanceRandSeed = random.nextLong();
         this.centreX = i;
         this.centreZ = k;
         this.locationInfo = loc;
      }

      protected final void setupBaseAndVillageProperties() {
         this.setupVillageSeed();
         if (this.locationInfo.isFixedLocation()) {
            this.rotationMode = (this.locationInfo.rotation + 2) % 4;
         } else {
            this.rotationMode = this.instanceRand.nextInt(4);
         }

         this.setupVillageProperties(this.instanceRand);
      }

      protected abstract void setupVillageProperties(Random var1);

      private void setupVillageSeed() {
         long seed = (long)this.centreX * 580682095692076767L + (long)this.centreZ * 12789948968296726L + this.theWorld.func_72912_H().func_76063_b() + 49920968939865L;
         seed += this.instanceRandSeed;
         this.instanceRand.setSeed(seed);
      }

      public void setRotation(int i) {
         this.rotationMode = i;
      }

      private void setupWorldPositionSeed(int i, int k) {
         this.setupVillageSeed();
         int[] coords = this.getRelativeCoords(i, k);
         int i1 = coords[0];
         int k1 = coords[1];
         long seed1 = this.instanceRand.nextLong();
         long seed2 = this.instanceRand.nextLong();
         long seed = (long)i1 * seed1 + (long)k1 * seed2 ^ this.theWorld.func_72912_H().func_76063_b();
         this.instanceRand.setSeed(seed);
      }

      public abstract boolean isFlat();

      protected final void setupVillageStructures() {
         this.setupVillageSeed();
         this.structures.clear();
         this.addVillageStructures(this.instanceRand);
      }

      protected abstract void addVillageStructures(Random var1);

      protected void addStructure(LOTRWorldGenStructureBase2 structure, int x, int z, int r) {
         this.addStructure(structure, x, z, r, false);
      }

      protected void addStructure(LOTRWorldGenStructureBase2 structure, int x, int z, int r, boolean force) {
         structure.villageInstance = this;
         structure.restrictions = !force;
         if (force) {
            structure.shouldFindSurface = true;
         }

         this.structures.add(new LOTRVillageGen.StructureInfo(structure, x, z, r));
      }

      protected abstract LOTRRoadType getPath(Random var1, int var2, int var3);

      public abstract boolean isVillageSpecificSurface(World var1, int var2, int var3, int var4);

      private int[] getWorldCoords(int xRel, int zRel) {
         int xWorld = this.centreX;
         int zWorld = this.centreZ;
         switch(this.rotationMode) {
         case 0:
            xWorld = this.centreX - xRel;
            zWorld = this.centreZ - zRel;
            break;
         case 1:
            xWorld = this.centreX + zRel;
            zWorld = this.centreZ - xRel;
            break;
         case 2:
            xWorld = this.centreX + xRel;
            zWorld = this.centreZ + zRel;
            break;
         case 3:
            xWorld = this.centreX - zRel;
            zWorld = this.centreZ + xRel;
         }

         return new int[]{xWorld, zWorld};
      }

      private int[] getRelativeCoords(int xWorld, int zWorld) {
         int xRel = 0;
         int zRel = 0;
         switch(this.rotationMode) {
         case 0:
            xRel = this.centreX - xWorld;
            zRel = this.centreZ - zWorld;
            break;
         case 1:
            xRel = this.centreZ - zWorld;
            zRel = xWorld - this.centreX;
            break;
         case 2:
            xRel = xWorld - this.centreX;
            zRel = zWorld - this.centreZ;
            break;
         case 3:
            xRel = zWorld - this.centreZ;
            zRel = this.centreX - xWorld;
         }

         return new int[]{xRel, zRel};
      }

      private int getStructureRotation(int r) {
         return (r + this.rotationMode + 2) % 4;
      }
   }

   private static class StructureInfo {
      public final LOTRWorldGenStructureBase2 structure;
      public final int posX;
      public final int posZ;
      public final int rotation;

      public StructureInfo(LOTRWorldGenStructureBase2 s, int x, int z, int r) {
         this.structure = s;
         this.posX = x;
         this.posZ = z;
         this.rotation = r;
      }
   }
}
