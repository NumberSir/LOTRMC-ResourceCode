package lotr.common.item;

import lotr.common.LOTRCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeeds;

public class LOTRItemSeeds extends ItemSeeds {
   public LOTRItemSeeds(Block crop, Block soil) {
      super(crop, soil);
      this.func_77637_a(LOTRCreativeTabs.tabMaterials);
   }
}
