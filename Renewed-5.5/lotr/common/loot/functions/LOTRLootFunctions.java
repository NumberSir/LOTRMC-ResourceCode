package lotr.common.loot.functions;

import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LOTRLootFunctions {
   public static LootFunctionType VESSEL_DRINK;
   public static LootFunctionType SET_NPC_DRINK_POTENCY;
   public static LootFunctionType SET_POUCH_COLOR_FROM_ENTITY_FACTION;

   public static void register() {
      VESSEL_DRINK = registerFunction("vessel_drink", new VesselDrink.Serializer());
      SET_NPC_DRINK_POTENCY = registerFunction("set_npc_drink_potency", new SetNPCDrinkPotency.Serializer());
      SET_POUCH_COLOR_FROM_ENTITY_FACTION = registerFunction("set_pouch_color_from_entity_faction", new SetPouchColorFromEntityFaction.Serializer());
   }

   private static LootFunctionType registerFunction(String name, ILootSerializer serializer) {
      return (LootFunctionType)Registry.func_218322_a(Registry.field_239694_aZ_, new ResourceLocation("lotr", name), new LootFunctionType(serializer));
   }
}
