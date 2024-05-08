package sweetbaboo.syncdolist.Tasks;

public class Step extends Entry{
  public Step(String text, boolean completed) {
    super(text, completed);
  }

  public boolean isCompleted() {
    return state;
  }

  public void setCompleted(boolean completed) {
    this.state=completed;
  }
}
