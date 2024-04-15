package sweetbaboo.syncdolist.event;


import fi.dy.masa.malilib.hotkeys.*;
import fi.dy.masa.malilib.util.KeyCodes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.config.Hotkeys;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler, IMouseInputHandler {
  private final KeybindCallbacks callbacks;

  public InputHandler() {
    this.callbacks=KeybindCallbacks.getInstance();
  }

  @Override
  public void addKeysToMap(IKeybindManager manager) {
    for (IHotkey hotkey : Hotkeys.HOTKEY_LIST) {
      manager.addKeybindToMap(hotkey.getKeybind());
    }
  }

  @Override
  public void addHotkeys(IKeybindManager manager) {
    manager.addHotkeysForCategory(Reference.MOD_NAME, "itemscroller.hotkeys.category.hotkeys", Hotkeys.HOTKEY_LIST);
  }

  @Override
  public boolean onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState) {
    return this.handleInput(keyCode, eventKeyState, 0);
  }

  @Override
  public boolean onMouseScroll(int mouseX, int mouseY, double amount) {
    return this.handleInput(KeyCodes.KEY_NONE, false, amount);
  }

  @Override
  public boolean onMouseClick(int mouseX, int mouseY, int eventButton, boolean eventButtonState) {
    return this.handleInput(eventButton - 100, eventButtonState, 0);
  }

  private boolean handleInput(int keyCode, boolean keyState, double dWheel) {
    MinecraftClient mc=MinecraftClient.getInstance();

    if (mc.player == null) {
      return false;
    }

    return this.handleInputImpl(keyCode, keyState, dWheel, mc);
  }

  private boolean handleInputImpl(int keyCode, boolean keyState, double dWheel, MinecraftClient mc) {
    return false;
  }

  @Override
  public void onMouseMove(int mouseX, int mouseY) {
  }

  private boolean handleDragging(HandledScreen<?> gui, MinecraftClient mc, int mouseX, int mouseY, boolean isClick) {
    return false;
  }
}

