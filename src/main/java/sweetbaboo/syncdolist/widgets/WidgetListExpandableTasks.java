package sweetbaboo.syncdolist.widgets;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import fi.dy.masa.malilib.gui.widgets.WidgetSearchBar;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.Tasks.Entry;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.gui.Icons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class WidgetListExpandableTasks extends WidgetListBase<Entry, WidgetListItem> {


  public WidgetListExpandableTasks(int x, int y, int width, int height, @Nullable ISelectionListener<Entry> selectionListener) {
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

    List<Entry> result = new ArrayList<>();

    for (var entry : entries) {
      result.add(entry);
      if (entry.getState()) {
//        result.addAll(t.)
      }
    }
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
  public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
    boolean result = super.onMouseClicked(mouseX, mouseY, mouseButton);
    this.reCreateListEntryWidgets();
    return result;
  }

//  @Override
//  protected void reCreateListEntryWidgets() {
//    List<WidgetExpandableTask> copy = new ArrayList<>(this.listWidgets);
//    this.listWidgets.clear();
//    this.maxVisibleBrowserEntries = 0;
//
//    final int numEntries = this.listContents.size();
//    int usableHeight = this.browserHeight - this.browserPaddingY - this.browserEntriesOffsetY;
//    int usedHeight = 0;
//    int x = this.posX + 2;
//    int y = this.posY + 4 + this.browserEntriesOffsetY;
//    int index = this.scrollBar.getValue();
//    var widget = this.createHeaderWidget(x, y, index, usableHeight, usedHeight);
//
//    if (widget != null)
//    {
//      this.listWidgets.add(widget);
//
//      usedHeight += widget.getHeight();
//      y += widget.getHeight();
//    }
//
//    for ( ; index < numEntries; ++index)
//    {
//      widget = this.createListEntryWidgetIfSpace(x, y, index, usableHeight, usedHeight);
//
//      if (widget == null)
//      {
//        break;
//      }
//
//      // puke... this was done because the incorrect widgets were being recreated.
//      if (copy.size() > index) {
//        for (var item : copy) {
//          if (Objects.equals(Objects.requireNonNull(item.getEntry()).name, Objects.requireNonNull(widget.getEntry()).name)) {
//            widget.recreate(item.isExpanded());
//          }
//        }
//      }
//
//      this.listWidgets.add(widget);
//      this.maxVisibleBrowserEntries++;
//
//      usedHeight += widget.getHeight();
//      y += widget.getHeight();
//    }
//
//    this.scrollBar.setMaxValue(this.listContents.size() - this.maxVisibleBrowserEntries);
//  }

  @Override
  protected WidgetListItem createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Entry entry) {
    return null;
  }
//
//  @Override
//  public void drawContents(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
//    RenderUtils.color(1f, 1f, 1f, 1f);
//
//    if (this.widgetSearchBar != null)
//    {
//      this.widgetSearchBar.render(mouseX, mouseY, false, drawContext);
//    }
//
//    WidgetBase hovered = null;
//    int scrollbarHeight = this.browserHeight - this.browserEntriesOffsetY - 8;
//    int totalHeight = 0;
//
//    for (var listContent : this.listContents)
//    {
//      totalHeight += this.getBrowserEntryHeightFor(listContent);
//    }
//
//    totalHeight = Math.max(totalHeight, scrollbarHeight);
//
//    int scrollBarX = this.posX + this.browserWidth - 9;
//    int scrollBarY = this.browserEntriesStartY + this.browserEntriesOffsetY;
//    this.scrollBar.render(mouseX, mouseY, partialTicks, scrollBarX, scrollBarY, 8, scrollbarHeight, totalHeight);
//
//    // The value gets updated in the drawScrollBar() method above, if dragging
//    if (this.scrollBar.getValue() != this.lastScrollbarPosition)
//    {
//      this.lastScrollbarPosition = this.scrollBar.getValue();
//      this.reCreateListEntryWidgets();
//    }
//
//    // Draw the currently visible directory entries
//    WidgetListItem prev = null;
//    for (var widget : this.listWidgets)
//    {
//      if (prev != null) {
//        widget.setPos(prev.getY() + prev.getHeight());
//      }
//      prev = widget;
//
//      var entry = widget.getEntry();
//      boolean isSelected = this.allowMultiSelection ? this.selectedEntries.contains(entry) : entry != null && entry.equals(this.getLastSelectedEntry());
//      widget.render(mouseX, mouseY, isSelected, drawContext);
//
//      if (widget.isMouseOver(mouseX, mouseY))
//      {
//        hovered = widget;
//      }
//    }
//
//    if (hovered == null && this.widgetSearchBar != null && this.widgetSearchBar.isMouseOver(mouseX, mouseY))
//    {
//      hovered = this.widgetSearchBar;
//    }
//
//    this.hoveredWidget = hovered;
//
//    RenderUtils.color(1f, 1f, 1f, 1f);
//  }
}
