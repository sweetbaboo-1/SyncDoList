package sweetbaboo.syncdolist.widgets;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.gui.widgets.WidgetStringListEntry;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.gui.Icons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WidgetExpandableTask extends WidgetContainer {

  protected boolean expanded;
  private Task task;
  private List<WidgetTaskStep> steps;
  private boolean isOdd;
  private final IGuiIcon expandedIcon;
  private final IGuiIcon unexpandedIcon;
  private int height;

  public WidgetExpandableTask(int x, int y, int width, int height, Task task, boolean isOdd, IGuiIcon unexpandedIcon, IGuiIcon expandedIcon) {
    super(x, y, width, height);
    this.height = height;
    this.expandedIcon = expandedIcon;
    this.unexpandedIcon = unexpandedIcon;
    this.isOdd = isOdd;
    this.task = task;
    this.steps = new ArrayList<>();
    genSteps();
    this.subWidgets.add(new WidgetStringListEntry(x, y, width, height, isOdd, task.toString(), 0));
  }

  private void genSteps() {
    boolean tempOdd = !isOdd;
    for (int i = 0; i < task.steps.length; i++) {
      steps.add(new WidgetTaskStep(x, y + height * (i + 1), width, height, tempOdd, "\t" + task.steps[i], task.notes[i]));
    }
  }

  @Override
  public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
    setExpanded(!isExpanded());
    return super.onMouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    IGuiIcon icon = this.expanded ? this.expandedIcon : this.unexpandedIcon;

    RenderUtils.color(1f, 1f, 1f, 1f);
    this.bindTexture(icon.getTexture());
    icon.renderAt(this.x + width - icon.getWidth(), this.y, this.zLevel, false, false);

    if (this.expanded) {
      for (WidgetTaskStep step : steps) {
        step.render(mouseX, mouseY, selected, drawContext);
      }
    }
    super.render(mouseX, mouseY, selected, drawContext);
  }

  public boolean isExpanded() {
    return expanded;
  }

  public void setExpanded(boolean expanded) {
    this.expanded=expanded;
  }

  @Override
  public int getHeight() {
    return isExpanded() ? height * task.steps.length : height;
  }
}
