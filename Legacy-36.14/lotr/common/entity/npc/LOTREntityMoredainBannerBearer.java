package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityMoredainBannerBearer extends LOTREntityMoredainWarrior implements LOTRBannerBearer {
   public LOTREntityMoredainBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.MOREDAIN;
   }
}
