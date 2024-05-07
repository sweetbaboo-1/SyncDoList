package sweetbaboo.syncdolist.Tasks;

public class Step {
  private String step;
  private boolean completed;

  public Step(String step, boolean completed) {
    this.step=step;
    this.completed=completed;
  }

  public String getStep() {
    return step;
  }

  public void setStep(String step) {
    this.step=step;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed=completed;
  }
}
