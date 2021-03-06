package lotr.client.render.entity;

import lotr.client.model.LOTRModelHalfTroll;
import lotr.common.entity.npc.LOTREntityHalfTroll;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderHalfTroll extends LOTRRenderBiped {
   private static LOTRRandomSkins halfTrollSkins;

   public LOTRRenderHalfTroll() {
      super(new LOTRModelHalfTroll(), 0.5F);
      halfTrollSkins = LOTRRandomSkins.loadSkinsList("lotr:mob/halfTroll/halfTroll");
   }

   protected void func_82421_b() {
      this.field_82423_g = new LOTRModelHalfTroll(1.0F);
      this.field_82425_h = new LOTRModelHalfTroll(0.5F);
   }

   public ResourceLocation func_110775_a(Entity entity) {
      LOTREntityHalfTroll halfTroll = (LOTREntityHalfTroll)entity;
      return halfTrollSkins.getRandomSkin(halfTroll);
   }

   public float getHeldItemYTranslation() {
      return 0.45F;
   }
}
