package sweetbaboo.syncdolist.config;

import java.util.List;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

public class Hotkeys {
  public static final ConfigHotkey OPEN_TODO_LIST=new ConfigHotkey("openToDoList", "X,L", "Open the TODO List");
  public static final ConfigHotkey SPAWN_COW=new ConfigHotkey("Spawn Cow", "S,C", "Spawn a Cow");
  public static final List<ConfigHotkey> HOTKEY_LIST=ImmutableList.of(OPEN_TODO_LIST, SPAWN_COW);
}

