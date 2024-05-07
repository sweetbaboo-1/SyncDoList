package sweetbaboo.syncdolist.Tasks;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import sweetbaboo.syncdolist.SyncDoList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class Task {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static final File SAVE_PATH = FabricLoader.getInstance().getConfigDir().resolve("synddolist").toFile();
  public static final String FILENAME = "tasks";

  public String name, author;
  public Date creationDate;
  public boolean completed;
  public List<Step> steps;
  public String[] notes;
  public PlayerEntity[] workers;

  public Task(String name, String author, Date creationDate, boolean completed, List<Step> steps, String[] notes, PlayerEntity[] workers) {
    this.name=name;
    this.author=author;
    this.creationDate = creationDate;
    this.completed=completed;
    this.steps=steps;
    this.notes = notes;
    this.workers=workers;
  }

  public static boolean toJson(List<Task> tasks) {
    try (FileWriter writer = new FileWriter(SAVE_PATH + File.separator + FILENAME)) {
      GSON.toJson(tasks, writer);
      return true;
    } catch (IOException e) {
      SyncDoList.logger.error("Error serializing tasks", e);
      return false;
    }
  }

  public static List<Task> readTasksFromFile(String filePath) {
    try (FileReader fileReader = new FileReader(filePath)) {
      return GSON.fromJson(fileReader, new TypeToken<List<Task>>(){}.getType());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String toString() {
    return this.name;
  }
}
