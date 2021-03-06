package lotr.client.render.entity;

import lotr.client.model.LOTRModelBoar;
import lotr.common.entity.animal.LOTREntityWildBoar;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderWildBoar extends RenderLiving {
   public static ResourceLocation boarSkin = new ResourceLocation("lotr:mob/boar/boar.png");
   private static ResourceLocation saddleTexture = new ResourceLocation("lotr:mob/boar/saddle.png");

   public LOTRRenderWildBoar() {
      super(new LOTRModelBoar(), 0.7F);
      this.func_77042_a(new LOTRModelBoar(0.5F));
   }

   protected ResourceLocation func_110775_a(Entity entity) {
      LOTREntityWildBoar boar = (LOTREntityWildBoar)entity;
      return LOTRRenderHorse.getLayeredMountTexture(boar, boarSkin);
   }

   protected int func_77032_a(EntityLivingBase entity, int pass, float f) {
      if (pass == 0 && ((LOTREntityWildBoar)entity).isMountSaddled()) {
         this.func_110776_a(saddleTexture);
         return 1;
      } else {
         return super.func_77032_a(entity, pass, f);
      }
   }
}
