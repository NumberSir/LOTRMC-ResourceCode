package lotr.common.network;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lotr.common.entity.item.LOTREntityBanner;
import lotr.common.fellowship.LOTRFellowship;
import lotr.common.fellowship.LOTRFellowshipProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class LOTRPacketBannerRequestInvalidName implements IMessage {
   private int bannerID;
   private int slot;
   private String username;

   public LOTRPacketBannerRequestInvalidName() {
   }

   public LOTRPacketBannerRequestInvalidName(LOTREntityBanner banner, int i, String s) {
      this.bannerID = banner.func_145782_y();
      this.slot = i;
      this.username = s;
   }

   public void toBytes(ByteBuf data) {
      data.writeInt(this.bannerID);
      data.writeShort(this.slot);
      byte[] nameBytes = this.username.getBytes(Charsets.UTF_8);
      data.writeByte(nameBytes.length);
      data.writeBytes(nameBytes);
   }

   public void fromBytes(ByteBuf data) {
      this.bannerID = data.readInt();
      this.slot = data.readShort();
      int length = data.readByte();
      this.username = data.readBytes(length).toString(Charsets.UTF_8);
   }

   public static class Handler implements IMessageHandler {
      public IMessage onMessage(LOTRPacketBannerRequestInvalidName packet, MessageContext context) {
         EntityPlayerMP entityplayer = context.getServerHandler().field_147369_b;
         World world = entityplayer.field_70170_p;
         Entity bEntity = world.func_73045_a(packet.bannerID);
         if (bEntity instanceof LOTREntityBanner) {
            LOTREntityBanner banner = (LOTREntityBanner)bEntity;
            String username = packet.username;
            boolean valid = false;
            if (LOTRFellowshipProfile.hasFellowshipCode(username)) {
               String fsName = LOTRFellowshipProfile.stripFellowshipCode(username);
               LOTRFellowship fellowship = banner.getPlacersFellowshipByName(fsName);
               if (fellowship != null) {
                  valid = true;
               }
            } else {
               GameProfile profile = MinecraftServer.func_71276_C().func_152358_ax().func_152655_a(packet.username);
               if (profile != null) {
                  valid = true;
               }
            }

            LOTRPacketBannerValidate packetResponse = new LOTRPacketBannerValidate(banner.func_145782_y(), packet.slot, packet.username, valid);
            LOTRPacketHandler.networkWrapper.sendTo(packetResponse, entityplayer);
         }

         return null;
      }
   }
}
