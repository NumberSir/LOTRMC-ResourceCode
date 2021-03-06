package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityMoredainMercenary;
import lotr.common.entity.npc.LOTREntityNPC;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LOTRWorldGenMoredainMercCamp extends LOTRWorldGenCampBase {
   public LOTRWorldGenMoredainMercCamp(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tableBlock = LOTRMod.commandTable;
      this.brickBlock = LOTRMod.planks2;
      this.brickMeta = 2;
      this.brickSlabBlock = LOTRMod.woodSlabSingle3;
      this.brickSlabMeta = 2;
      this.fenceBlock = LOTRMod.fence2;
      this.fenceMeta = 2;
      this.fenceGateBlock = LOTRMod.fenceGateCedar;
   }

   protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
      return new LOTRWorldGenMoredainMercTent(false);
   }

   protected LOTREntityNPC getCampCaptain(World world, Random random) {
      return null;
   }

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityMoredainMercenary.class);
      respawner.setCheckRanges(24, -12, 12, 10);
      respawner.setSpawnRanges(8, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
      int mercs = 2 + random.nextInt(5);

      for(int l = 0; l < mercs; ++l) {
         LOTREntityMoredainMercenary merc = new LOTREntityMoredainMercenary(world);
         this.spawnNPCAndSetHome(merc, world, 0, 1, 0, 16);
      }

   }

   public boolean generateWithSetRotation(World world, Random random, int i, int j, int k, int rotation) {
      if (!super.generateWithSetRotation(world, random, i, j, k, rotation)) {
         return false;
      } else {
         int dummies = 1 + random.nextInt(3);

         for(int l = 0; l < dummies; ++l) {
            for(int att = 0; att < 8; ++att) {
               int r = MathHelper.func_76136_a(random, 8, 15);
               float ang = random.nextFloat() * 3.1415927F * 2.0F;
               int i1 = (int)((float)r * MathHelper.func_76134_b(ang));
               int k1 = (int)((float)r * MathHelper.func_76126_a(ang));
               int j1 = this.getTopBlock(world, i1, k1);
               int rot = random.nextInt(4);
               if (this.generateSubstructureWithRestrictionFlag(new LOTRWorldGenMoredainMercDummy(this.notifyChanges), world, random, i1, j1, k1, rot, true)) {
                  break;
               }
            }
         }

         return true;
      }
   }
}
