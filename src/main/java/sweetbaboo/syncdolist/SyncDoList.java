package sweetbaboo.syncdolist;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sweetbaboo.syncdolist.network.ModMessages;

public class SyncDoList implements ModInitializer {
	public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

	@Override
	public void onInitialize() {
		InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
		ModMessages.registerServerToClientPackets();
		ModMessages.registerClientToServerPackets();
	}
}
