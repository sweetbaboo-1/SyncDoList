package sweetbaboo.syncdolist.gui;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.Tasks.Task;

import javax.annotation.Nullable;
import java.util.Date;

public class GuiMainMenu extends GuiBase {

  private static Task[] tasks = {
          new Task("Build Ice Farm", "SweetBaboo", new Date(), false, new String[] {"Storage", "Farm", "Deco"}, new PlayerEntity[]{}),
          new Task("Build Bonemeal Farm", "SweetBaboo", new Date(), false, new String[] {"Storage", "Farm", "Deco"}, new PlayerEntity[]{}),
          new Task("Build New Storage", "SweetBaboo", new Date(), false, new String[] {"Storage", "Haul Items", "Deco"}, new PlayerEntity[]{}),
          new Task("Party", "SweetBaboo", new Date(), false, new String[] {"Candles", "Cake", "Balloons"}, new PlayerEntity[]{})
  };

  public GuiMainMenu() {
    String version = String.format("v%s", Reference.MOD_VERSION);
    this.title =StringUtils.translate("syncdolist.gui.title.main_menu", version);
  }

  @Override
  public void initGui() {
    super.initGui();

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

  private void createChangeMenuButton(int x, int y, int width, ButtonListener.ButtonType type)
  {
    ButtonGeneric button = new ButtonGeneric(x, y, width, 20, type.getDisplayName());
    this.addButton(button, new ButtonListener(type/*, this*/));
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

  public static class ButtonListener implements IButtonActionListener
  {
    private final ButtonType type;
//    @Nullable
//    private final Screen parent;

    public ButtonListener(ButtonType type/*, @Nullable Screen parent*/)
    {
      this.type = type;
//      this.parent = parent;
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
