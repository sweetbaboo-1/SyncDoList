package sweetbaboo.syncdolist.widgets;

import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.gui.widgets.WidgetStringListEntry;
import sweetbaboo.syncdolist.gui.Icons;

public class WidgetTaskStep extends WidgetContainer {

  private boolean isCompleted;
  private String step, notes;
  private boolean isOdd;
  private WidgetCheckBox checkBox;

  public WidgetTaskStep(int x, int y, int width, int height, boolean isOdd, String step, String notes) {
    super(x, y, width, height);
    this.isCompleted = false;
    this.isOdd = isOdd;
    this.step = step;
    this.notes = notes;
    this.checkBox = new WidgetCheckBox(x + 100, y, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, "DeleteMe?");
    this.subWidgets.add(this.checkBox);
    this.subWidgets.add(new WidgetStringListEntry(x,y,width,height,isOdd,step,0));
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted=completed;
  }

  public String getStep() {
    return step;
  }

  public void setStep(String step) {
    this.step=step;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes=notes;
  }

  public boolean isOdd() {
    return isOdd;
  }

  public void setOdd(boolean odd) {
    isOdd=odd;
  }

  public WidgetCheckBox getCheckBox() {
    return checkBox;
  }

  public void setCheckBox(WidgetCheckBox checkBox) {
    this.checkBox=checkBox;
  }
}
