package sweetbaboo.syncdolist.entries;

import sweetbaboo.syncdolist.network.Locks.Lock;

import java.util.ArrayList;
import java.util.List;

public class Task extends Entry {
  public String date;
  public boolean completed;
  private List<Step> steps;
  private Lock completedLock;

  public Task(String text, List<Step> steps, String date, boolean completed, String metaData) {
    super(text, metaData);
    this.steps=steps;
    this.date=date;
    this.completed=completed;
  }

  public Task() {
    super();
    this.steps=new ArrayList<>();
    this.date="";
    this.completed=false;
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

  public Lock getCompletedLock() {
    return completedLock;
  }

  public void setCompletedLock(Lock completedLock) {
    this.completedLock=completedLock;
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
