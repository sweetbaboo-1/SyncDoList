package sweetbaboo.syncdolist.network.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.manager.TaskManager;
import sweetbaboo.syncdolist.network.ModMessages;

import java.util.List;

public class GetTasksClientToServerPacket {
  public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                             PacketByteBuf buf, PacketSender responseSender) {
    List<Task> tasks = TaskManager.getInstance().getTasks();
    ClientPlayNetworking.send(ModMessages.GET_TASKS_ID, PacketByteBufs.create());
  }
}
