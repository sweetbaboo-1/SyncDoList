package sweetbaboo.syncdolist.widgets;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.gui.Icons;

import java.util.ArrayList;
import java.util.List;

public class WidgetExpandableTask extends WidgetContainer {

  private static final IGuiIcon ICON_EXPANDED = Icons.ARROW_DOWN;
  private static final IGuiIcon ICON_UNEXPANDED = Icons.ARROW_UP;

  protected boolean expanded;
  private Task task;
  private List<WidgetColorfulStringListEntry> steps;
  private boolean isOdd;
  private int defaultHeight;
  private WidgetColorfulStringListEntry entry;

  public WidgetExpandableTask(int x, int y, int width, int height, @NotNull Task task, boolean isOdd) {
    super(x, y, width, height * (task.steps.size() + 1));
    this.isOdd = isOdd;
    this.task = task;
    this.height = height;
    this.defaultHeight = height;
    this.steps = new ArrayList<>();
    this.expanded = false;

    genSteps();
    genEntry();

    this.subWidgets.add(entry);
    this.subWidgets.addAll(steps);
  }

  private void genEntry() {
    this.entry = new WidgetColorfulStringListEntry(x, y, width, defaultHeight, isOdd, task.toString(), 0, GuiMainMenu.COLOR_WHITE);
  }

  private void genSteps() {
    boolean tempOdd = !isOdd;
    for (int i = 0; i < task.steps.size(); i++) {
      steps.add(new WidgetColorfulStringListEntry(x, y + defaultHeight * (i + 1), width, defaultHeight, tempOdd, task.steps.get(i).getStep(), i, task.steps.get(i).isCompleted() ? GuiMainMenu.COLOR_GREEN : GuiMainMenu.COLOR_RED));
      tempOdd = !tempOdd;
    }
  }

  @Override
  public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
    int i = (mouseY - y) / defaultHeight;
    if (i == 0) {
      setExpanded(!isExpanded());
    } else {
      i--;
      task.steps.get(i).setCompleted(!task.steps.get(i).isCompleted());
      steps.get(i).setColor(task.steps.get(i).isCompleted() ? GuiMainMenu.COLOR_GREEN : GuiMainMenu.COLOR_RED);
    }
    entry.color = expanded ? GuiMainMenu.COLOR_LIGHT_BLUE : GuiMainMenu.COLOR_WHITE;
    this.height = getHeight();
    return super.onMouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    IGuiIcon icon = this.expanded ? ICON_EXPANDED : ICON_UNEXPANDED;

    RenderUtils.color(1f, 1f, 1f, 1f);
    this.bindTexture(icon.getTexture());
    icon.renderAt(this.x + width - icon.getWidth(), this.y, this.zLevel, false, false);

    if (this.expanded) {
      super.render(mouseX, mouseY, selected, drawContext);
    } else {
      this.entry.render(mouseX, mouseY, selected, drawContext);
    }
  }

  public void setPos(int newPos) {
    this.y = newPos;
    this.entry.setY(this.y);
    int h = defaultHeight;
    for (WidgetColorfulStringListEntry step : steps) {
      step.setPos(this.y + h);
      h += defaultHeight;
    }
  }

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded(boolean expanded) {
    this.expanded=expanded;
  }

  @Override
  public int getHeight() {
    return isExpanded() ? defaultHeight + defaultHeight * task.steps.size() : defaultHeight;
  }
}
