package lotr.client.render.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class LOTRRenderArrowPoisoned extends RenderArrow {
   private static final ResourceLocation arrowPoisonTexture = new ResourceLocation("lotr:item/arrowPoisoned.png");

   protected ResourceLocation func_110775_a(Entity entity) {
      return arrowPoisonTexture;
   }
}
