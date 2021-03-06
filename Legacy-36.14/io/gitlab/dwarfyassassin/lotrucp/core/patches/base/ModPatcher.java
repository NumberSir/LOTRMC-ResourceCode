package io.gitlab.dwarfyassassin.lotrucp.core.patches.base;

import cpw.mods.fml.common.Loader;

public abstract class ModPatcher extends Patcher {
   private String modID;

   public ModPatcher(String name, String modID) {
      super(name);
      this.modID = modID;
   }

   public Patcher.LoadingPhase getLoadPhase() {
      return Patcher.LoadingPhase.FORGE_MOD_LOADING;
   }

   public boolean shouldInit() {
      return Loader.isModLoaded(this.modID);
   }
}
