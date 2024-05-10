package sweetbaboo.syncdolist.widgets.step;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import sweetbaboo.syncdolist.entries.Step;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.gui.Icons;

import java.util.Collection;
import java.util.List;

public class WidgetListSteps extends WidgetListBase<Step, WidgetStepItem> {

  public final GuiBase parent;
  public final Task task;

  public WidgetListSteps(int x, int y, int width, int height, Task task, ISelectionListener parent) {
    super(x, y, width, height, parent);
    this.parent = (GuiBase) parent;
    this.browserEntryHeight = 22;
    this.widgetSearchBar = new WidgetSearchBar(x + 2, y + 4, width - 14, 14, 0, Icons.FILE_ICON_SEARCH, LeftRight.LEFT);
    this.browserEntriesOffsetY = this.widgetSearchBar.getHeight() + 3;
    this.task = task;
  }

  @Override
  protected Collection<Step> getAllEntries() {
    return task.getSteps();
  }

  @Override
  protected List<String> getEntryStringsForFilter(Step entry) {
    if (entry.getText() != null) {
      if (!entry.getMetaData().equals("")) {
        return ImmutableList.of(entry.getText().toLowerCase(), entry.getMetaData().toLowerCase());
      } else {
        return ImmutableList.of(entry.getText().toLowerCase());
      }
    }
    return super.getEntryStringsForFilter(entry);
  }

  @Override
  protected WidgetStepItem createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Step entry) {
    return new WidgetStepItem(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), entry, isOdd, listIndex, this, this.browserHeight);
  }
}
