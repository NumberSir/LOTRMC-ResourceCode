package lotr.common.world.structure2;

import lotr.common.entity.npc.LOTREntityNearHaradrimBase;
import lotr.common.entity.npc.LOTREntityUmbarFarmer;
import net.minecraft.world.World;

public class LOTRWorldGenUmbarFarm extends LOTRWorldGenSouthronFarm {
   public LOTRWorldGenUmbarFarm(boolean flag) {
      super(flag);
   }

   protected boolean isUmbar() {
      return true;
   }

   protected LOTREntityNearHaradrimBase createFarmer(World world) {
      return new LOTREntityUmbarFarmer(world);
   }
}
