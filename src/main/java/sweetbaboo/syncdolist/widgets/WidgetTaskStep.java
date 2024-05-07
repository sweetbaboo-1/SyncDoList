package sweetbaboo.syncdolist.widgets;

import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.gui.Icons;

public class WidgetTaskStep extends WidgetContainer {

  private boolean isCompleted;
  private String step, notes;
  private boolean isOdd;
  private WidgetCheckBox checkBox;
  private WidgetColorfulStringListEntry entry;

  public WidgetTaskStep(int x, int y, int width, int height, boolean isOdd, String step, String notes) {
    super(x, y, width + 100, height);
    this.isCompleted = false;
    this.isOdd = isOdd;
    this.step = step;
    this.notes = notes;
    this.checkBox = new WidgetCheckBox(x + width + 5, y + (height / 4), Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, String.format("x:%d, y:%d", x + width + 5, y + (height / 4)));
//    this.checkBox = new WidgetCheckBox(x, y, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, String.format("x:%d, y:%d, width:%d, height:%d", x, y, width, height));
    this.entry = new WidgetColorfulStringListEntry(x, y, width, height, isOdd, step,0, this.checkBox.isChecked() ? GuiMainMenu.COLOR_GREEN : GuiMainMenu.COLOR_RED);
    this.subWidgets.add(checkBox);
    this.subWidgets.add(entry);
  }

  public void setPos(int newPos) {
    this.y = newPos;
    this.entry.setY(this.y);
    this.checkBox.setY(this.y);
  }
}
