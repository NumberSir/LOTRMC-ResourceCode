package lotr.common.entity.npc;

import lotr.common.item.LOTRItemBanner;
import net.minecraft.world.World;

public class LOTREntityDaleBannerBearer extends LOTREntityDaleSoldier implements LOTRBannerBearer {
   public LOTREntityDaleBannerBearer(World world) {
      super(world);
   }

   public LOTRItemBanner.BannerType getBannerType() {
      return LOTRItemBanner.BannerType.DALE;
   }
}
