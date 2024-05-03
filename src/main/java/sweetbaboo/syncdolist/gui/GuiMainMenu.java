package sweetbaboo.syncdolist.gui;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.Tasks.Task;
import sweetbaboo.syncdolist.widgets.WidgetExpandableTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiMainMenu extends GuiBase {

  private List<Task> tasks = new ArrayList<>();

  public GuiMainMenu() {
    String version = String.format("v%s", Reference.MOD_VERSION);
    this.title = StringUtils.translate("syncdolist.gui.title.main_menu", version);
    tasks.add(new Task("Build Ice Farm",      "SweetBaboo", new Date(), false, new String[] {"Storage", "Farm", "Deco"}, new String[] {"Note 1", "Note 2", "Note 3"}, new PlayerEntity[]{}));
    tasks.add(new Task("Build Bonemeal Farm", "SweetBaboo", new Date(), false, new String[] {"Storage", "Farm", "Deco"}, new String[] {"Note 1", "Note 2", "Note 3"}, new PlayerEntity[]{}));
//    tasks.add(new Task("Build New Storage", "SweetBaboo", new Date(), false, new String[] {"Storage", "Haul Items", "Deco"}, new PlayerEntity[]{}));
//    tasks.add(new Task("Party", "SweetBaboo", new Date(), false, new String[] {"Candles", "Cake", "Balloons"}, new PlayerEntity[]{}));

  }

  @Override
  public void initGui() {
    super.initGui();
    genButtons();
    genTasks();
  }

  private void genButtons() {
    int x = 12;
    int y = 30;
    int width = this.getButtonWidth();

    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.CONFIGURATION);

    y = this.height - y;
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.ADD_TASK);

    x += width;
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.DELETE_TASK);

    x += width;
    this.createChangeMenuButton(x, y, width, ButtonListener.ButtonType.EDIT_TASK);
  }

  @Override
  public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
    super.render(drawContext, mouseX, mouseY, partialTicks);
  }

  private void genTasks() {
    int taskWidth = getTaskWidth();
    int x = 12;
    int ty = 50;
    boolean odd = false;
    for (Task task : tasks) {
      WidgetExpandableTask listEntry1 = new WidgetExpandableTask(x, ty,  taskWidth, 20, task, false, Icons.ARROW_UP, Icons.ARROW_DOWN);
      this.addWidget(listEntry1);
      ty += 20;
      odd = !odd;
    }
  }

  private void createChangeMenuButton(int x, int y, int width, ButtonListener.ButtonType type)
  {
    ButtonGeneric button = new ButtonGeneric(x, y, width, 20, type.getDisplayName());
    this.addButton(button, new ButtonListener(type));
  }

  private int getButtonWidth()
  {
    int width = 0;

    for (ButtonListener.ButtonType type : ButtonListener.ButtonType.values())
    {
      width = Math.max(width, this.getStringWidth(type.getDisplayName()) + 30);
    }
    return width;
  }

  private int getTaskWidth() {
    int width = 0;

    for (Task task : tasks) {
      width = Math.max(width, this.getStringWidth(task.toString()) + 30);
    }
    return width;
  }

  public static class ButtonListener implements IButtonActionListener
  {
    private final ButtonType type;

    public ButtonListener(ButtonType type)
    {
      this.type = type;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton)
    {
      switch (this.type)
      {
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

    public enum ButtonType
    {
      EDIT_TASK                   ("syncdolist.gui.button.change_menu.edit_task"),
      ADD_TASK                    ("syncdolist.gui.button.change_menu.add_task"),
      DELETE_TASK                 ("syncdolist.gui.button.change_menu.delete_task"),
      CONFIGURATION               ("syncdolist.gui.button.change_menu.config_menu");

      private final String labelKey;

      ButtonType(String labelKey)
      {
        this.labelKey = labelKey;
      }

      public String getLabelKey()
      {
        return this.labelKey;
      }

      public String getDisplayName()
      {
        return StringUtils.translate(this.getLabelKey());
      }

    }
  }
}
