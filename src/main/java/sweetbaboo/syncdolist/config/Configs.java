package sweetbaboo.syncdolist.config;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import sweetbaboo.syncdolist.Reference;

import java.io.File;

public class Configs implements IConfigHandler {
  private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

  public static void loadFromFile() {
    File configFile=new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

    if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
      JsonElement element=JsonUtils.parseJsonFile(configFile);

      if (element != null && element.isJsonObject()) {
        JsonObject root=element.getAsJsonObject();

        ConfigUtils.readConfigBase(root, "Generic", TodoList.TODO_LIST_ITEMS);
      }
    }
  }

  public static void saveToFile() {
    File dir=FileUtils.getConfigDirectory();

    if ((dir.exists() && dir.isDirectory()) || dir.mkdirs()) {
      JsonObject root=new JsonObject();

      ConfigUtils.writeConfigBase(root, "Generic", TodoList.TODO_LIST_ITEMS);
      JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
    }
  }

  @Override
  public void load() {
    loadFromFile();
  }

  @Override
  public void save() {
    saveToFile();
  }

  public static class TodoList {
    public static final ImmutableList<IConfigValue> TODO_LIST_ITEMS=ImmutableList.of();
  }
}

