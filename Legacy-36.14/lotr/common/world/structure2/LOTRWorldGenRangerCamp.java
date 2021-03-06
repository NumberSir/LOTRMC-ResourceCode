package lotr.common.world.structure2;

import java.util.Random;
import lotr.common.LOTRMod;
import lotr.common.entity.LOTREntityNPCRespawner;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.entity.npc.LOTREntityRangerNorth;
import lotr.common.entity.npc.LOTREntityRangerNorthCaptain;
import net.minecraft.world.World;

public class LOTRWorldGenRangerCamp extends LOTRWorldGenCampBase {
   public LOTRWorldGenRangerCamp(boolean flag) {
      super(flag);
   }

   protected void setupRandomBlocks(Random random) {
      super.setupRandomBlocks(random);
      this.tableBlock = LOTRMod.commandTable;
   }

   protected LOTRWorldGenStructureBase2 createTent(boolean flag, Random random) {
      return new LOTRWorldGenRangerTent(false);
   }

   protected LOTREntityNPC getCampCaptain(World world, Random random) {
      return new LOTREntityRangerNorthCaptain(world);
   }

   protected void placeNPCRespawner(World world, Random random, int i, int j, int k) {
      LOTREntityNPCRespawner respawner = new LOTREntityNPCRespawner(world);
      respawner.setSpawnClass(LOTREntityRangerNorth.class);
      respawner.setCheckRanges(24, -12, 12, 12);
      respawner.setSpawnRanges(8, -4, 4, 16);
      this.placeNPCRespawner(respawner, world, i, j, k);
   }
}
