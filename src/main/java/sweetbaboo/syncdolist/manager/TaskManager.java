package sweetbaboo.syncdolist.manager;

import sweetbaboo.syncdolist.entries.Task;

import javax.annotation.Nullable;
import java.util.List;

public class TaskManager {
  @Nullable
  private Task selectedTask;
  private static TaskManager instance;

  private List<Task> tasks;

  private TaskManager() {
    selectedTask = null;
    tasks = Task.readTasksFromFile();
  }


  public static TaskManager getInstance() {
    if (instance == null) {
      instance = new TaskManager();
    }
    return instance;
  }

  public void setSelectedTask(@Nullable Task task) {
    if (task == null || this.tasks.contains(task)) {
      this.selectedTask = task;
    }
  }

  public Task getSelectedTask() {
    return selectedTask;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public boolean removeTask(Task task) {
    if (selectedTask == task) {
      selectedTask = null;
    }
    return tasks.remove(task);
  }

  public boolean addTask(Task task) {
    if (!this.tasks.contains(task)) {
      return this.tasks.add(task);
    }
    return false;
  }
}
