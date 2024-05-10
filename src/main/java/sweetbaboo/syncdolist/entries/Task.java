package sweetbaboo.syncdolist.entries;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import sweetbaboo.syncdolist.SyncDoList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task extends Entry{
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  public static final File SAVE_PATH = FabricLoader.getInstance().getConfigDir().resolve("synddolist").toFile();
  public static final String FILENAME = "tasks";
  public static final String ARCHIVE = "archive";

  private List<Step> steps;
  public String date;
  public boolean wasCompleted;

  public Task(String text, List<Step> steps, String date, boolean wasCompleted, String metaData) {
    super(text, metaData);
    this.steps=steps;
    this.date = date;
    this.wasCompleted = wasCompleted;
  }

  public Task() {
    super();
    this.steps = new ArrayList<>();
    this.date = "";
    this.wasCompleted = false;
  }

  public static boolean toJson(List<Task> entries, boolean archive) {
    if (!SAVE_PATH.exists()) {
      if (!SAVE_PATH.mkdirs()) {
        SyncDoList.logger.error("Failed to create directory: %s".formatted(SAVE_PATH));
        return false;
      }
    }

    try (FileWriter writer = new FileWriter(getFilename(archive))) {
      GSON.toJson(entries, writer);
      return true;
    } catch (IOException e) {
      SyncDoList.logger.error("Error serializing tasks", e);
      return false;
    }
  }

  public static List<Task> readTasksFromFile(boolean archived) {
    try (FileReader fileReader = new FileReader(getFilename(archived))) {
      return GSON.fromJson(fileReader, new TypeToken<List<Task>>(){}.getType());
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public List<Step> getSteps() {
    List<Step> result = new ArrayList<>();
    for (Step step : steps) {
      if (!step.text.isEmpty()) {
        result.add(step);
      }
    }
    steps = result;
    return steps;
  }

  public static void archived(Task task) {
    List<Task> archivedTasks = readTasksFromFile(true);
    if (archivedTasks == null) { // this should only happen if there is nothing archived already
      archivedTasks = new ArrayList<>();
    }
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    task.date = now.format(formatter);
    archivedTasks.add(task);
    toJson(archivedTasks, true);
  }

  @Override
  public String toString() {
    return String.format("Task: %s\nSteps: %d", this.text, this.getSteps().size());
  }

  public boolean isCompleted() {
    for (Step step : getSteps()) {
      if (!step.completed) {
        return false;
      }
    }
    return true;
  }

  private static String getFilename(boolean archive) {
    String filename = SAVE_PATH + File.separator;
    if (archive) {
      filename += ARCHIVE;
    } else {
      filename += FILENAME;
    }
    return filename;
  }

  public String getMetaData() {
    return metaData;
  }

  public void addStep(Step step) {
    if (steps == null) {
      steps = new ArrayList<>();
    }
    this.getSteps().add(step);
  }
}
