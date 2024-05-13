package sweetbaboo.syncdolist;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import sweetbaboo.syncdolist.config.Configs;
import sweetbaboo.syncdolist.event.InputHandler;
import sweetbaboo.syncdolist.event.KeybindCallbacks;
import sweetbaboo.syncdolist.network.ModMessages;

public class InitHandler implements IInitializationHandler
{
  @Override
  public void registerModHandlers()
  {
    ConfigManager.getInstance().registerConfigHandler(Reference.MOD_ID, new Configs());

    InputHandler handler = new InputHandler();
    InputEventHandler.getKeybindManager().registerKeybindProvider(handler);
    InputEventHandler.getInputManager().registerKeyboardInputHandler(handler);
    InputEventHandler.getInputManager().registerMouseInputHandler(handler);

    TickHandler.getInstance().registerClientTickHandler(KeybindCallbacks.getInstance());
    KeybindCallbacks.getInstance().setCallbacks();
  }
}
