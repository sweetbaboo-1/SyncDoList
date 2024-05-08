package sweetbaboo.syncdolist.entries;

import sweetbaboo.syncdolist.gui.GuiMainMenu;

public class Step extends Entry{

  protected boolean completed;

  public Step(String text, boolean completed) {
    super(text, completed ? GuiMainMenu.COLOR_GREEN : GuiMainMenu.COLOR_WHITE);
    this.completed = completed;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed=completed;
  }
}
