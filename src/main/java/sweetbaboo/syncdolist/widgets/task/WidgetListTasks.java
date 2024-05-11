package sweetbaboo.syncdolist.widgets.task;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.gui.Icons;
import sweetbaboo.syncdolist.manager.TaskManager;

import java.util.Collection;
import java.util.List;

public class WidgetListTasks extends WidgetListBase<Task, WidgetTaskItem> {

  public final GuiMainMenu parent;
  public TaskManager manager;

  public WidgetListTasks(int x, int y, int width, int height, GuiMainMenu parent) {
    super(x, y, width, height, parent);
    this.parent = parent;
    this.browserEntryHeight = 22;
    this.widgetSearchBar = new WidgetSearchBar(x + 2, y + 4, width - 14, 14, 0, Icons.FILE_ICON_SEARCH, LeftRight.LEFT);
    this.browserEntriesOffsetY = this.widgetSearchBar.getHeight() + 3;
    this.manager = TaskManager.getInstance();
  }

  public GuiMainMenu getParentGui()
  {
    return this.parent;
  }

  @Override
  protected Collection<Task> getAllEntries() {
    return manager.getTasks();
  }

  @Override
  protected WidgetTaskItem createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Task entry) {
    return new WidgetTaskItem(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), isOdd, entry, listIndex, this);
  }

  @Override
  protected List<String> getEntryStringsForFilter(Task entry) {
    if (entry.getText() != null) {
      if (!entry.getMetaData().equals("")) {
        return ImmutableList.of(entry.getText().toLowerCase(), entry.getMetaData().toLowerCase());
      } else {
        return ImmutableList.of(entry.getText().toLowerCase());
      }
    }
    return super.getEntryStringsForFilter(entry);
  }

}
