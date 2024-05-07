package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.player.PlayerEntity;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.Tasks.Step;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.widgets.WidgetExpandableTask;
import sweetbaboo.syncdolist.widgets.WidgetTaskStep;
import sweetbaboo.syncdolist.widgets.wtest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiMainMenu extends GuiBase {

  public static final int COLOR_LIGHT_BLUE=0x3396ff;
  public static final int COLOR_GREEN=0x00FF00;
  public static final int COLOR_RED=0xFF0000;

  public static final int OBJECT_HEIGHT=20;

  private List<Task> tasks=new ArrayList<>();
  private List<WidgetExpandableTask> widgets;

  public GuiMainMenu() {
    String version=String.format("v%s", Reference.MOD_VERSION);
    this.title=StringUtils.translate("syncdolist.gui.title.main_menu", version);
    tasks.add(new Task("Build Ice Farm", "SweetBaboo", new Date(), false, new ArrayList<>() {{
      add(new Step("Storage", false));
      add(new Step("Deco", false));
      add(new Step("Farm", false));
    }}, new String[]{"Note 1", "Note 2", "Note 3"}, new PlayerEntity[]{}));

//    tasks.add(new Task("Build Bonemeal Farm", "SweetBaboo", new Date(), false, new String[] {"Storage", "Farm", "Deco"}, new String[] {"Note 1", "Note 2", "Note 3"}, new PlayerEntity[]{}));
//    tasks.add(new Task("Build New Storage", "SweetBaboo", new Date(), false, new String[] {"Storage", "Haul Items", "Deco"}, new String[] {"Note 1", "Note 2", "Note 3"}, new PlayerEntity[]{}));
//    tasks.add(new Task("Party", "SweetBaboo", new Date(), false, new String[] {"Candles", "Cake", "Balloons"}, new String[] {"Note 1", "Note 2", "Note 3"}, new PlayerEntity[]{}));
    widgets=new ArrayList<>();
  }

  @Override
  public void initGui() {
    super.initGui();
    genButtons();
    genTasks();
//    wtest test=new wtest(180, 180, 20, 20);
//    this.addWidget(test);
//    for (WidgetExpandableTask task : widgets) {
//      this.addWidget(task);
//    }
  }

  @Override
  protected void closeGui(boolean showParent) {
    Task.toJson(tasks);
    super.closeGui(showParent);
  }

  private void genButtons() {
    int x=12;
    int y=30;
    int width=this.getButtonWidth();

    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.CONFIGURATION);

    y=this.height - y;
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.ADD_TASK);

    x+=width;
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.DELETE_TASK);

    x+=width;
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.EDIT_TASK);
  }

//  @Override
//  public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
//    this.clearWidgets();
//    wtest test = new wtest(200, 200,1,1);
//    this.addWidget(test);
//    WidgetExpandableTask prev = null;
//    for (WidgetExpandableTask task : widgets) {
//      if (prev != null) {
//        task.setPos(prev.getHeight() + prev.getY());
//      }
//      this.addWidget(task);
//      prev = task;
//    }
//    super.render(drawContext, mouseX, mouseY, partialTicks);
//  }

  private void genTasks() {
    int taskWidth=getTaskWidth();
    int x=12;
    int ty=80;
    boolean odd=false;
    for (Task task : tasks) {
      WidgetExpandableTask t=new WidgetExpandableTask(x, ty, taskWidth + 50, OBJECT_HEIGHT, task, odd);
      this.addWidget(t);
      this.widgets.add(t);
      ty+=OBJECT_HEIGHT;
      odd=!odd;
    }
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

  private int getTaskWidth() {
    int width=0;

    for (Task task : tasks) {
      width=Math.max(width, this.getStringWidth(task.toString()) + 30);
    }
    return width;
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
        case DELETE_TASK:
//          deleteTask(selectedTask);
          break;
        case EDIT_TASK:
//          editTask(selectedTask);
          break;
        case ADD_TASK:
//          addTask();
          break;
      }
    }

//    private void deleteTask(ta)

    public enum ButtonType {
      EDIT_TASK("syncdolist.gui.button.change_menu.edit_task"),
      ADD_TASK("syncdolist.gui.button.change_menu.add_task"),
      DELETE_TASK("syncdolist.gui.button.change_menu.delete_task"),
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
