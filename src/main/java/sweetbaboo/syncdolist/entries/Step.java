package sweetbaboo.syncdolist.entries;

public class Step extends Entry {

  public boolean completed;

  public Step(String text, boolean completed, String metaData) {
    super(text, metaData);
    this.completed = completed;
  }

  public Step() {
    this.completed = false;
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
