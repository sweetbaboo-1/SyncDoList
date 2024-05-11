package sweetbaboo.syncdolist.entries;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.dy.masa.malilib.MaLiLibConfigs;
import fi.dy.masa.malilib.util.StringUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.WorldSavePath;
import sweetbaboo.syncdolist.SyncDoList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task extends Entry {
  public static final String FILENAME="tasks";
  public static final String ARCHIVE="archive";
  private static final Gson GSON=new GsonBuilder().setPrettyPrinting().create();
  public String date;
  public boolean wasCompleted;
  private List<Step> steps;

  public Task(String text, List<Step> steps, String date, boolean wasCompleted, String metaData) {
    super(text, metaData);
    this.steps=steps;
    this.date=date;
    this.wasCompleted=wasCompleted;
  }

  public Task() {
    super();
    this.steps=new ArrayList<>();
    this.date="";
    this.wasCompleted=false;
  }

  public static File getSaveFile() {
    MinecraftClient mc=MinecraftClient.getInstance();
    if (mc.isIntegratedServerRunning()) {
      net.minecraft.server.integrated.IntegratedServer server=mc.getServer();
      if (server != null) {
        String name=String.valueOf(server.getSavePath(WorldSavePath.ROOT));
        return new File(name.substring(0, name.length() - 2)); // remove the "\." that is there for some reason
      }
    } else {
      if (mc.isConnectedToRealms()) {
        if (MaLiLibConfigs.Generic.REALMS_COMMON_CONFIG.getBooleanValue()) {
          return new File("realms");
        } else {
          net.minecraft.client.network.ClientPlayNetworkHandler handler=mc.getNetworkHandler();
          net.minecraft.network.ClientConnection connection=handler != null ? handler.getConnection() : null;

          if (connection != null) {
            return new File("realms_" + StringUtils.stringifyAddress(connection.getAddress()));
          }
        }
      }
      net.minecraft.client.network.ServerInfo server=mc.getCurrentServerEntry();
      if (server != null) // strange logic here, need to pull it out
      {
        return null;
      }
      return new File("syncdolist_multiplayer_fallback");
    }
    return new File("syncdolist");
  }

  public static void toJson(List<Task> entries, boolean archive) {
    File saveFolder = getSaveFolder();

    if (saveFolder == null) {
      return;
    }

    if (!saveFolder.exists()) {
      if (!saveFolder.mkdirs()) {
        SyncDoList.logger.error("Failed to create directory: %s".formatted(saveFolder));
        return;
      }
    }

    try (FileWriter writer=new FileWriter(saveFolder + File.separator + getFilename(archive))) {
      SyncDoList.logger.info("Saving to: " + saveFolder + File.separator + getFilename(archive));
      GSON.toJson(entries, writer);
    } catch (IOException e) {
      SyncDoList.logger.error("Error serializing tasks", e);
    }
  }

  public static List<Task> readTasksFromFile(boolean archived) {
    File saveFolder = getSaveFolder();
    try (FileReader fileReader=new FileReader(saveFolder + File.separator + getFilename(archived))) {
      SyncDoList.logger.info("Loading from: " + saveFolder + File.separator + getFilename(archived));
      return GSON.fromJson(fileReader, new TypeToken<List<Task>>() {
      }.getType());
    } catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  public static void archived(Task task) {
    List<Task> archivedTasks=readTasksFromFile(true);
    if (archivedTasks == null) { // this should only happen if there is nothing archived already
      archivedTasks=new ArrayList<>();
    }
    LocalDateTime now=LocalDateTime.now();
    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    task.date=now.format(formatter);
    archivedTasks.add(task);
    toJson(archivedTasks, true);
  }

  private static String getFilename(boolean archive) {
    return archive ? ARCHIVE : FILENAME;
  }

  private static File getSaveFolder() {
    File saveFile=getSaveFile();
    File saveFolder;

    if (saveFile == null) {
      MinecraftClient mc=MinecraftClient.getInstance();
      net.minecraft.client.network.ServerInfo server=mc.getCurrentServerEntry();

      if (server == null) {
        return null;
      }

      saveFolder=FabricLoader.getInstance().getConfigDir().resolve("syncdolist" + File.separator + server.name).toFile();
    } else {
      saveFolder=new File(saveFile + File.separator + "syncdolist");
    }
    return saveFolder;
  }

  public List<Step> getSteps() {
    List<Step> result=new ArrayList<>();
    for (Step step : steps) {
      if (!step.text.isEmpty()) {
        result.add(step);
      }
    }
    steps=result;
    return steps;
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

  public String getMetaData() {
    return metaData;
  }

  public void addStep(Step step) {
    if (steps == null) {
      steps=new ArrayList<>();
    }
    this.getSteps().add(step);
  }
}
