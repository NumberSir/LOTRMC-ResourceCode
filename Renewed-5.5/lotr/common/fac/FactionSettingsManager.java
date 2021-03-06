package lotr.common.fac;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lotr.common.LOTRLog;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.SPacketFactionSettings;
import lotr.common.resources.InstancedJsonReloadListener;
import lotr.common.resources.PostServerLoadedValidator;
import lotr.common.world.map.MapSettings;
import lotr.common.world.map.MapSettingsManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;

public class FactionSettingsManager extends InstancedJsonReloadListener implements PostServerLoadedValidator {
   private static final String FACTION_FOLDER = "factions";
   private static final String REGION_SUBFOLDER = "regions/";
   private static final String RELATIONS_SUBFOLDER = "relations/";
   private static final FactionSettingsManager CLIENT_INSTANCE;
   private static final FactionSettingsManager SERVER_INSTANCE;
   private FactionSettings currentLoadedFactions;

   private FactionSettingsManager(LogicalSide side) {
      super("factions", "FactionSettings", side);
   }

   public static FactionSettingsManager clientInstance() {
      return CLIENT_INSTANCE;
   }

   public static FactionSettingsManager serverInstance() {
      return SERVER_INSTANCE;
   }

   public static FactionSettingsManager sidedInstance(IWorldReader world) {
      return !world.func_201670_d() ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public static FactionSettingsManager sidedInstance(LogicalSide side) {
      return side == LogicalSide.SERVER ? SERVER_INSTANCE : CLIENT_INSTANCE;
   }

   public void loadClientFactionsFromServer(IResourceManager resMgr, FactionSettings facSettings) {
      this.currentLoadedFactions = facSettings;
      this.logFactionsLoad("Loaded clientside faction settings from server", this.currentLoadedFactions);
   }

   private FactionSettings loadFactionsFromJsons(IResourceManager resMgr, Map regionJsons, Map factionJsons, Map relationsJsons, MapSettings mapSettings) {
      List regions = new ArrayList();
      int nextRegionId = 0;
      Iterator var8 = regionJsons.entrySet().iterator();

      while(var8.hasNext()) {
         Entry entry = (Entry)var8.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         ResourceLocation regionName = trimSubFolderResource(res, "regions/");
         JsonObject regionJson = (JsonObject)entry.getValue();

         try {
            FactionRegion region = FactionRegion.read(regionName, regionJson, nextRegionId);
            if (region != null) {
               regions.add(region);
            }

            ++nextRegionId;
         } catch (Exception var20) {
            LOTRLog.warn("Failed to load faction region %s from file", regionName);
            var20.printStackTrace();
         }
      }

      FactionSettings facSettings = new FactionSettings(regions);
      List factions = new ArrayList();
      int nextFactionId = 0;
      int nextFactionId = TechnicalFactions.registerTechnicalFactions(facSettings, mapSettings, factions, nextFactionId);
      Iterator var25 = factionJsons.entrySet().iterator();

      ResourceLocation res;
      while(var25.hasNext()) {
         Entry entry = (Entry)var25.next();
         ResourceLocation res = (ResourceLocation)entry.getKey();
         res = new ResourceLocation(res.func_110624_b(), res.func_110623_a());
         JsonObject factionJson = (JsonObject)entry.getValue();

         try {
            Faction faction = Faction.read(facSettings, res, factionJson, nextFactionId, mapSettings);
            if (faction != null) {
               factions.add(faction);
            }

            ++nextFactionId;
         } catch (Exception var19) {
            LOTRLog.warn("Failed to load faction %s from file", res);
            var19.printStackTrace();
         }
      }

      facSettings.setFactions(factions);
      List relationsTables = new ArrayList();
      Iterator var28 = relationsJsons.entrySet().iterator();

      while(var28.hasNext()) {
         Entry entry = (Entry)var28.next();
         res = (ResourceLocation)entry.getKey();
         ResourceLocation relationsName = trimSubFolderResource(res, "relations/");
         JsonObject relationsJson = (JsonObject)entry.getValue();

         try {
            FactionRelationsTable relations = FactionRelationsTable.read(facSettings, relationsName, relationsJson);
            if (relations != null) {
               relationsTables.add(relations);
            }
         } catch (Exception var18) {
            LOTRLog.warn("Failed to load faction relations table %s from file", relationsName);
            var18.printStackTrace();
         }
      }

      FactionRelationsTable combinedRelations = FactionRelationsTable.combine(relationsTables);
      facSettings.setRelations(combinedRelations);
      return facSettings;
   }

   public FactionSettings getCurrentLoadedFactions() {
      return this.currentLoadedFactions;
   }

   protected void apply(Map jsons, IResourceManager serverResMgr, IProfiler profiler) {
      Map regionJsons = this.filterDataJsonsBySubFolder(jsons, "regions/");
      Map factionJsons = this.filterDataJsonsByRootFolderOnly(jsons);
      Map relationsJsons = this.filterDataJsonsBySubFolder(jsons, "relations/");
      MapSettings mapSettings = MapSettingsManager.serverInstance().getCurrentLoadedMap();
      this.currentLoadedFactions = this.loadFactionsFromJsons(serverResMgr, regionJsons, factionJsons, relationsJsons, mapSettings);
      this.logFactionsLoad("Loaded serverside faction settings", this.currentLoadedFactions);
   }

   private void logFactionsLoad(String prefix, FactionSettings facSettings) {
      LOTRLog.info("%s - %d factions in %d regions, with %d relations (combined from %d relations lists)", prefix, facSettings.getFactions().size(), facSettings.getRegions().size(), facSettings.getRelations().size(), facSettings.getRelations().getNumCombinedFrom());
   }

   public void sendFactionsToPlayer(ServerPlayerEntity player) {
      SPacketFactionSettings packet = new SPacketFactionSettings(this.currentLoadedFactions);
      LOTRPacketHandler.sendTo(packet, player);
   }

   public void performPostServerLoadValidation(World mainWorld) {
      this.currentLoadedFactions.postLoadValidateBiomes(mainWorld);
   }

   static {
      CLIENT_INSTANCE = new FactionSettingsManager(LogicalSide.CLIENT);
      SERVER_INSTANCE = new FactionSettingsManager(LogicalSide.SERVER);
      PostServerLoadedValidator.validators.add(SERVER_INSTANCE);
   }
}
