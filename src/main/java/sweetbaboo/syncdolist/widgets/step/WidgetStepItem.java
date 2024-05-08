package sweetbaboo.syncdolist.widgets.step;

import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.entries.Step;

public class WidgetStepItem extends WidgetListEntryBase<Step> {
  public WidgetStepItem(int x, int y, int width, int height, @Nullable Step entry, int listIndex) {
    super(x, y, width, height, entry, listIndex);
  }
}
