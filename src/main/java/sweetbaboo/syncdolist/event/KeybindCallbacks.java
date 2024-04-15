package sweetbaboo.syncdolist.event;


import net.minecraft.client.MinecraftClient;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import sweetbaboo.syncdolist.config.Hotkeys;
import sweetbaboo.syncdolist.gui.GuiConfigs;
import sweetbaboo.syncdolist.gui.GuiMainMenu;

public class KeybindCallbacks implements IHotkeyCallback, IClientTickHandler {
  private static final KeybindCallbacks INSTANCE = new KeybindCallbacks();

  public static KeybindCallbacks getInstance() {
    return INSTANCE;
  }

  private KeybindCallbacks() {
  }

  public void setCallbacks() {
    for (ConfigHotkey hotkey : Hotkeys.HOTKEY_LIST) {
      hotkey.getKeybind().setCallback(this);
    }
  }

  @Override
  public boolean onKeyAction(KeyAction action, IKeybind key) {
    boolean cancel = this.onKeyActionImpl(action, key);
    return cancel;
  }

  private boolean onKeyActionImpl(KeyAction action, IKeybind key) {
    MinecraftClient mc = MinecraftClient.getInstance();

    if (mc.player == null || mc.world == null) {
      return false;
    }

    if (key == Hotkeys.OPEN_TODO_LIST.getKeybind()) {
      GuiBase.openGui(new GuiMainMenu());
      return true;
    }
    return false;
  }

  @Override
  public void onClientTick(MinecraftClient mc) {
  }
}

