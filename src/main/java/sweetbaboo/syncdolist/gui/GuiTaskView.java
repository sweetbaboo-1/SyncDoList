package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.entries.Step;
import sweetbaboo.syncdolist.widgets.step.WidgetListSteps;
import sweetbaboo.syncdolist.widgets.step.WidgetStepItem;

public class GuiTaskView extends GuiListBase<Step, WidgetStepItem, WidgetListSteps> implements ISelectionListener<Step> {
  public GuiTaskView(GuiBase parent) {
    super(12, 30);
    this.setParent(parent);
  }

  @Override
  protected WidgetListSteps createListWidget(int listX, int listY) {
    return null;
  }

  @Override
  protected int getBrowserWidth() {
    return 0;
  }

  @Override
  protected int getBrowserHeight() {
    return 0;
  }

  @Override
  public void onSelectionChange(@Nullable Step entry) {

  }
}
