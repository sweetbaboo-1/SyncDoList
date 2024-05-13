package sweetbaboo.syncdolist.entries;

import sweetbaboo.syncdolist.network.Locks.Lock;

public class Step extends Entry {

  public boolean completed;
  private Lock completedLock;

  public Step(String text, boolean completed, String metaData) {
    super(text, metaData);
    this.completed = completed;
  }

  public Step() {
    this.completed = false;
  }

  public Lock getCompletedLock() {
    return completedLock;
  }

  public void setCompletedLock(Lock completedLock) {
    this.completedLock=completedLock;
  }

  @Override
  public String toString() {
    return text;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed=completed;
  }
}
