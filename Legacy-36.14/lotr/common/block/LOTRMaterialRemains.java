package lotr.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class LOTRMaterialRemains extends Material {
   public static LOTRMaterialRemains remains = new LOTRMaterialRemains();

   public LOTRMaterialRemains() {
      super(MapColor.field_151664_l);
      this.func_76221_f();
   }
}
