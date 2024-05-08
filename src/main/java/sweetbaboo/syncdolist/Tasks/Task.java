package sweetbaboo.syncdolist.Tasks;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import sweetbaboo.syncdolist.SyncDoList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Task extends Entry{
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static final File SAVE_PATH = FabricLoader.getInstance().getConfigDir().resolve("synddolist").toFile();
  public static final String FILENAME = "tasks";

  public List<Step> steps;
  public boolean isExpanded;

  public Task(String text, List<Step> steps) {
    super(text, false);
    this.steps=steps;
  }

  public static boolean toJson(List<Entry> entries) {
    if (!SAVE_PATH.exists()) {
      if (!SAVE_PATH.mkdirs()) {
        SyncDoList.logger.error("Failed to create directory: %s".formatted(SAVE_PATH));
        return false;
      }
    }

    try (FileWriter writer = new FileWriter(SAVE_PATH + File.separator + FILENAME)) {
      GSON.toJson(entries, writer);
      return true;
    } catch (IOException e) {
      SyncDoList.logger.error("Error serializing tasks", e);
      return false;
    }
  }

  public static List<Entry> readTasksFromFile() {
    String filePath = SAVE_PATH + File.separator + FILENAME;
    try (FileReader fileReader = new FileReader(filePath)) {
      return GSON.fromJson(fileReader, new TypeToken<List<Entry>>(){}.getType());
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public boolean isExpanded() {
    return isExpanded;
  }

  public void setExpanded(boolean expanded) {
    isExpanded=expanded;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
