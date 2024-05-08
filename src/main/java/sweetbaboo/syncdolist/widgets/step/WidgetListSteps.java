package sweetbaboo.syncdolist.widgets.step;

import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.entries.Step;

public class WidgetListSteps extends WidgetListBase<Step, WidgetStepItem> {


  public WidgetListSteps(int x, int y, int width, int height, @Nullable ISelectionListener<Step> selectionListener) {
    super(x, y, width, height, selectionListener);
  }

  @Override
  protected WidgetStepItem createListEntryWidget(int x, int y, int listIndex, boolean isOdd, Step entry) {
    return null;
  }
}
