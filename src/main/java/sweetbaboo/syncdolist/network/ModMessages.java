package sweetbaboo.syncdolist.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.network.packets.GetTasksClientToServerPacket;

public class ModMessages {
  public static final Identifier GET_TASKS_ID = new Identifier(Reference.MOD_ID, "get_tasks");
  public static final Identifier SAVE_TASKS_ID = new Identifier(Reference.MOD_ID, "save_tasks");
//  public static final Identifier UPDATE_TASKS_ID = new Identifier(Reference.MOD_ID, "update_tasks");

  public static void registerClientToServerPackets() {
    // create
    ServerPlayNetworking.registerGlobalReceiver(GET_TASKS_ID, GetTasksClientToServerPacket::receive);
    ServerPlayNetworking.registerGlobalReceiver(SAVE_TASKS_ID, GetTasksClientToServerPacket::receive);
  }

  public static void registerServerToClientPackets() {

  }
}
