package sweetbaboo.syncdolist.config;


import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IConfigValue;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import sweetbaboo.syncdolist.Reference;

import java.io.File;
import java.util.Set;

public class Configs implements IConfigHandler
{
  private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

  public static class Generic
  {
//    public static final ConfigBoolean MASS_CRAFT_HOLD                       = new ConfigBoolean("massCraftHold",                        false, "Mass craft continuously");
//    public static final ConfigInteger MASS_CRAFT_INTERVAL                   = new ConfigInteger("massCraftInterval",                    1, 1, 60, "The interval in game ticks the massCraft operation is repeated at");

    public static final ImmutableList<IConfigValue> OPTIONS = ImmutableList.of(
    );
  }

  public static class Toggles
  {
    public static final ImmutableList<IConfigValue> OPTIONS = ImmutableList.of(
    );
  }

  public static void loadFromFile()
  {
    File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

    if (configFile.exists() && configFile.isFile() && configFile.canRead())
    {
      JsonElement element = JsonUtils.parseJsonFile(configFile);

      if (element != null && element.isJsonObject())
      {
        JsonObject root = element.getAsJsonObject();

        ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
      }
    }
  }

  public static void saveToFile()
  {
    File dir = FileUtils.getConfigDirectory();

    if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
    {
      JsonObject root = new JsonObject();

      ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
      JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
    }
  }

  @Override
  public void load()
  {
    loadFromFile();
  }

  @Override
  public void save()
  {
    saveToFile();
  }

  private static void getStrings(JsonObject obj, Set<String> outputSet, String arrayName)
  {
    outputSet.clear();

    if (JsonUtils.hasArray(obj, arrayName))
    {
      JsonArray arr = obj.getAsJsonArray(arrayName);
      final int size = arr.size();

      for (int i = 0; i < size; i++)
      {
        outputSet.add(arr.get(i).getAsString());
      }
    }
  }

  private static void writeStrings(JsonObject obj, Set<String> inputSet, String arrayName)
  {
    if (inputSet.isEmpty() == false)
    {
      JsonArray arr = new JsonArray();

      for (String str : inputSet)
      {
        arr.add(str);
      }

      obj.add(arrayName, arr);
    }
  }
}

