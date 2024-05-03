package sweetbaboo.syncdolist;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncDoList implements ModInitializer {
	public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

	@Override
	public void onInitialize()
	{
		InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());
	}
}

/*
 * Feature to be able to add some note to each step i.e. "The mats are in a chest"
 * Syncing waypoints to a map.
 * Discord integration. Look at defnot's mod Kiwitech admin
 * Priority ranking
 */