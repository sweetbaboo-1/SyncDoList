package sweetbaboo.syncdolist.widgets;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.Tasks.Entry;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.gui.Icons;

import java.util.Collection;
import java.util.List;

public class WidgetListTasks extends WidgetListBase<Entry, WidgetListItem> {


  public WidgetListTasks(int x, int y, int width, int height, @Nullable ISelectionListener<Entry> selectionListener) {
    super(x, y, width, height, selectionListener);
    this.browserEntryHeight = 22;
    this.widgetSearchBar = new WidgetSearchBar(x + 2, y + 4, width - 14, 14, 0, Icons.FILE_ICON_SEARCH, LeftRight.LEFT);
    this.browserEntriesOffsetY = this.widgetSearchBar.getHeight() + 3;
  }

  @Override
  protected Collection<Entry> getAllEntries() {
    List<Entry> entries = Task.readTasksFromFile();
    if (entries == null)
      return super.getAllEntries();
    return entries;
  }

  @Override
  protected List<String> getEntryStringsForFilter(Entry entry) {
    if (entry.text != null) {
      return ImmutableList.of(entry.text.toLowerCase());
    }
    return super.getEntryStringsForFilter(entry);
  }

  @Override
  protected WidgetListItem createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Entry entry) {
    return null;
  }
}
