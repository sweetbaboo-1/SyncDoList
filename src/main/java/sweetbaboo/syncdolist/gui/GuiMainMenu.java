package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.util.StringUtils;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.manager.TaskManager;
import sweetbaboo.syncdolist.widgets.Task.WidgetListTasks;
import sweetbaboo.syncdolist.widgets.Task.WidgetTaskItem;

import java.util.Objects;

public class GuiMainMenu extends GuiListBase<Task, WidgetTaskItem, WidgetListTasks> implements ISelectionListener<Task> {

  public static final int COLOR_LIGHT_BLUE=0x3396ff;
  public static final int COLOR_GREEN=0x00FF00;
  public static final int COLOR_RED=0xFF0000;

  public static final int OBJECT_HEIGHT=20;

  public TaskManager manager;

  public GuiMainMenu() {
    super(12, 30);
    String version=String.format("v%s", Reference.MOD_VERSION);
    this.title=StringUtils.translate("syncdolist.gui.title.main_menu", version);
    this.manager=TaskManager.getInstance();
  }

  @Override
  protected WidgetListTasks createListWidget(int listX, int listY) {
    return new WidgetListTasks(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), this);
  }

  @Override
  protected int getBrowserWidth() {
    return this.width - 20;
  }

  @Override
  protected int getBrowserHeight() {
    return this.height - 64;
  }

  @Override
  public void initGui() {
    super.initGui();
    genButtons();
  }

  @Override
  protected void closeGui(boolean showParent) {
    Task.toJson(Objects.requireNonNull(this.getListWidget()).getCurrentEntries());
    super.closeGui(showParent);
  }

  private void genButtons() {
    int x=this.width - 12 - this.getButtonWidth();
    int y=12;

    int width=this.getButtonWidth();
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.CONFIGURATION);
    this.createChangeMenuButton(12, this.height - 30, width, ButtonListener.ButtonType.ADD_TASK);
  }

  private void createChangeMenuButton(int x, int y, int width, ButtonListener.ButtonType type) {
    ButtonGeneric button=new ButtonGeneric(x, y, width, OBJECT_HEIGHT, type.getDisplayName());
    this.addButton(button, new ButtonListener(type));
  }

  private int getButtonWidth() {
    int width=0;

    for (ButtonListener.ButtonType type : ButtonListener.ButtonType.values()) {
      width=Math.max(width, this.getStringWidth(type.getDisplayName()) + 30);
    }
    return width;
  }

  @Override
  public void onSelectionChange(@Nullable Task task) {
    manager.setSelectedTask(task);
  }

  public static class ButtonListener implements IButtonActionListener {
    private final ButtonType type;

    public ButtonListener(ButtonType type) {
      this.type=type;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      switch (this.type) {
        case CONFIGURATION:
          GuiBase.openGui(new GuiConfigs());
          return;
        case ADD_TASK:
          GuiBase.openGui(new GuiCreateTask());
          break;
      }
    }

    public enum ButtonType {
      ADD_TASK("syncdolist.gui.button.change_menu.add_task"),
      CONFIGURATION("syncdolist.gui.button.change_menu.config_menu");

      private final String labelKey;

      ButtonType(String labelKey) {
        this.labelKey=labelKey;
      }

      public String getLabelKey() {
        return this.labelKey;
      }

      public String getDisplayName() {
        return StringUtils.translate(this.getLabelKey());
      }
    }
  }
}
