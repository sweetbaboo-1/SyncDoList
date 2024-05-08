package sweetbaboo.syncdolist.widgets;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.NotNull;
import sweetbaboo.syncdolist.Tasks.Entry;
import sweetbaboo.syncdolist.Tasks.Step;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.gui.Icons;

public class WidgetListItem extends WidgetListEntryBase<Entry> {
  private static final IGuiIcon ICON_EXPANDED = Icons.ARROW_DOWN;
  private static final IGuiIcon ICON_UNEXPANDED = Icons.ARROW_UP;

  private boolean isOdd;
  private int defaultHeight;


  public WidgetListItem(int x, int y, int width, int height, @NotNull Entry entry, boolean isOdd, int listIndex) {
    super(x, y, width, entry instanceof Task ? ((Task) entry).steps.size() * height + height : height, entry, listIndex);
    this.isOdd=isOdd;
    this.defaultHeight=height;
  }

  @Override
  public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
    if (entry == null) {
      return super.onMouseClicked(mouseX, mouseY, mouseButton);
    }

    entry.setState(!entry.getState());

    if (entry instanceof Step step) {
      step.setColor(step.isCompleted() ? GuiMainMenu.COLOR_GREEN : GuiMainMenu.COLOR_RED);
    } else if (entry instanceof Task task) {
      task.setColor(task.isExpanded() ? GuiMainMenu.COLOR_LIGHT_BLUE : GuiMainMenu.COLOR_WHITE);
    }
    return super.onMouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    if (entry instanceof Task task) {
      IGuiIcon icon = task.isExpanded() ? ICON_EXPANDED : ICON_UNEXPANDED;
      RenderUtils.color(1f, 1f, 1f, 1f);
      this.bindTexture(icon.getTexture());
      icon.renderAt(this.x + width - icon.getWidth(), this.y, this.zLevel, false, false);
    }
    super.render(mouseX, mouseY, selected, drawContext);
  }


}
