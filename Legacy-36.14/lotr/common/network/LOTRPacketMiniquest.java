package lotr.common.network;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import lotr.common.LOTRLevelData;
import lotr.common.LOTRMod;
import lotr.common.LOTRPlayerData;
import lotr.common.quest.LOTRMiniQuest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class LOTRPacketMiniquest implements IMessage {
   private NBTTagCompound miniquestData;
   private boolean completed;

   public LOTRPacketMiniquest() {
   }

   public LOTRPacketMiniquest(NBTTagCompound nbt, boolean flag) {
      this.miniquestData = nbt;
      this.completed = flag;
   }

   public void toBytes(ByteBuf data) {
      try {
         (new PacketBuffer(data)).func_150786_a(this.miniquestData);
      } catch (IOException var3) {
         FMLLog.severe("LOTR: Error writing miniquest data", new Object[0]);
         var3.printStackTrace();
      }

      data.writeBoolean(this.completed);
   }

   public void fromBytes(ByteBuf data) {
      try {
         this.miniquestData = (new PacketBuffer(data)).func_150793_b();
      } catch (IOException var3) {
         FMLLog.severe("LOTR: Error reading miniquest data", new Object[0]);
         var3.printStackTrace();
      }

      this.completed = data.readBoolean();
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketMiniquest packet, MessageContext context) {
         if (!LOTRMod.proxy.isSingleplayer()) {
            EntityPlayer entityplayer = LOTRMod.proxy.getClientPlayer();
            LOTRPlayerData pd = LOTRLevelData.getData(entityplayer);
            LOTRMiniQuest miniquest = LOTRMiniQuest.loadQuestFromNBT(packet.miniquestData, pd);
            if (miniquest != null) {
               LOTRMiniQuest existingQuest = pd.getMiniQuestForID(miniquest.questUUID, packet.completed);
               if (existingQuest == null) {
                  if (packet.completed) {
                     pd.addMiniQuestCompleted(miniquest);
                  } else {
                     pd.addMiniQuest(miniquest);
                  }
               } else {
                  existingQuest.readFromNBT(packet.miniquestData);
               }
            }
         }

         return null;
      }
   }
}
