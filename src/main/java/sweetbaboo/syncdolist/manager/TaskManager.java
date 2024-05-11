package sweetbaboo.syncdolist.manager;

import sweetbaboo.syncdolist.entries.Step;
import sweetbaboo.syncdolist.entries.Task;

import javax.annotation.Nullable;
import java.util.List;

public class TaskManager {
  @Nullable
  private Task selectedTask;
  private static TaskManager instance;

  private final List<Task> tasks;

  private TaskManager() {
    selectedTask = null;
    tasks = Task.readTasksFromFile(false);
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

  public static TaskManager refreshInstance() {
    instance = null;
    instance = getInstance();
    return instance;
  }

  public Task getSelectedTask() {
    return selectedTask;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void removeTask(Task task) {
    if (selectedTask == task) {
      selectedTask = null;
    }
    tasks.remove(task);
  }

  public void addTask(Task task) {
    if (!this.tasks.contains(task)) {
      this.tasks.add(task);
    }
  }

  public void complete(Task task) {
    for (var step : task.getSteps()) {
      step.setCompleted(true);
    }
  }

  private <T> void swap(List<T> list, int index, int i) {
    T temp = list.get(index);
    list.set(index, list.get(i));
    list.set(i, temp);
  }

  public void moveTaskUp(Task task) {
    int index = tasks.indexOf(task);
    if (index > 0) {
      swap(tasks, index, index - 1);
    }
  }

  public void moveStepUp(Step step) {
    if (getSelectedTask() != null) {
      int index=getSelectedTask().getSteps().indexOf(step);
      if (index > 0) {
        swap(getSelectedTask().getSteps(), index, index - 1);
      }
    }
  }

  public void moveStepUp(Step step, Task task) {
    if (task != null) {
      int index=task.getSteps().indexOf(step);
      if (index > 0) {
        swap(task.getSteps(), index, index - 1);
      }
    }
  }

  public void moveTaskDown(Task task) {
    int index = tasks.indexOf(task);
    if (index < tasks.size() - 1) {
      swap(tasks, index, index + 1);
    }
  }

  public void moveStepDown(Step step) {
    if (getSelectedTask() != null) {
      int index = getSelectedTask().getSteps().indexOf(step);
      if (index < getSelectedTask().getSteps().size() - 1) {
        swap(getSelectedTask().getSteps(), index, index + 1);
      }
    }
  }

  public void moveStepDown(Step step, Task task) {
    if (task != null) {
      int index = task.getSteps().indexOf(step);
      if (index < task.getSteps().size() - 1) {
        swap(task.getSteps(), index, index + 1);
      }
    }
  }
}
