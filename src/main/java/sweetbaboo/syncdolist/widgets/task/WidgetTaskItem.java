package sweetbaboo.syncdolist.widgets.task;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.DrawContext;
import sweetbaboo.syncdolist.Utils;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.gui.GuiTaskView;
import sweetbaboo.syncdolist.gui.Icons;
import sweetbaboo.syncdolist.manager.TaskManager;
import sweetbaboo.syncdolist.widgets.button.IconButton;

public class WidgetTaskItem extends WidgetListEntryBase<Task> {

  public final WidgetListTasks parent;
  public final Task task;
  public final boolean isOdd;
  public int buttonsStartX;
  public TaskManager manager;

  public WidgetTaskItem(int x, int y, int width, int height, boolean isOdd, Task task, int listIndex, WidgetListTasks parent) {
    super(x, y, width, height, task, listIndex);
    this.parent=parent;
    this.task=task;
    this.isOdd=isOdd;
    this.manager = TaskManager.getInstance();

    int posX=x + width - 2;
    int posY=y + 1;

    // Note: These are placed from right to left
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.REMOVE, null);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.VIEW_EDIT, null);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.COMPLETE, null);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.DOWN, Icons.ARROW_DOWN);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.UP, Icons.ARROW_UP);

    this.buttonsStartX=posX;
  }

  public int createButtonGeneric(int xRight, int y, ButtonListener.ButtonType type, IGuiIcon icon) {
    if (icon == null) {
      return this.addButton(new ButtonGeneric(xRight, y, -1, true, type.getDisplayName()), new ButtonListener(type, this)).getX() - 1;
    } else {
      return this.addButton(new IconButton(xRight, y, 20, type.getDisplayName(), icon), new ButtonListener(type, this)).getX() - 1;
    }
  }

  @Override
  public boolean canSelectAt(int mouseX, int mouseY, int mouseButton) {
    return mouseX < this.buttonsStartX && super.canSelectAt(mouseX, mouseY, mouseButton);
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    RenderUtils.color(1f, 1f, 1f, 1f);

    // Draw a lighter background for the hovered and the selected entry
    if (this.isMouseOver(mouseX, mouseY) || selected) {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0707070);
    } else if (this.isOdd) {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0101010);
    }
    // Draw a slightly lighter background for even entries
    else {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0303030);
    }

    if (selected) {
      RenderUtils.drawOutline(this.x, this.y, this.width, this.height, 0xFFE0E0E0);
      if (this.entry != null && !this.entry.getMetaData().equals("")) {
        GuiMainMenu.drawSelectedEntryMetaData(this.x + this.width + 20, this.height + 33, entry, "Task Notes:", drawContext);
      }
    }

    RenderUtils.color(1, 1, 1, 1);

    if (!(this.entry == null)) {
      int yOffset = (this.height - this.fontHeight) / 2 + 1;
      this.drawStringWithShadow(this.x + 2, this.y + yOffset, this.entry.isCompleted() ? GuiMainMenu.COLOR_GREEN : GuiBase.COLOR_WHITE, this.entry.getText(), drawContext);
    }

    super.render(mouseX, mouseY, false, drawContext);
  }

  public static class ButtonListener implements IButtonActionListener {
    public final ButtonType type;
    public final WidgetTaskItem widget;

    public ButtonListener(ButtonType type, WidgetTaskItem widget) {
      this.type=type;
      this.widget=widget;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      if (this.widget.entry == null) {
        return;
      }
      switch (this.type) {
        case UP -> {
          this.widget.manager.moveTaskUp(this.widget.task);
          this.widget.parent.refreshEntries();
        }
        case DOWN -> {
          this.widget.manager.moveTaskDown(this.widget.task);
          this.widget.parent.refreshEntries();
        }
        case REMOVE -> {
          if (!GuiBase.isShiftDown()) {
            this.widget.parent.getParentGui().addMessage(Message.MessageType.ERROR, "syncdolist.error.task.remove_fail");
          } else {
            this.widget.manager.removeTask(this.widget.task);
            this.widget.parent.refreshEntries();
          }
        }
        case ARCHIVE -> {
          if (!GuiBase.isShiftDown()) {
            this.widget.parent.getParentGui().addMessage(Message.MessageType.ERROR, "syncdolist.error.task.archive_fail");
          } else {
            Utils.archived(this.widget.task);
            this.widget.manager.removeTask(this.widget.task);
            this.widget.parent.refreshEntries();
          }
        }
        case COMPLETE -> {
          this.widget.manager.complete(this.widget.task);
          this.widget.parent.refreshEntries();
        }
        case VIEW_EDIT -> {
          this.widget.manager.setSelectedTask(this.widget.task);
          GuiBase.openGui(new GuiTaskView(this.widget.task, this.widget.parent.parent));
        }
      }
    }

    public enum ButtonType {
      VIEW_EDIT("syncdolist.gui.button.task.edit"),
      REMOVE("syncdolist.gui.button.task.remove"),
      UP("syncdolist.gui.button.task.up"),
      DOWN("syncdolist.gui.button.task.down"),
      COMPLETE("syncdolist.gui.button.task.complete"),
      ARCHIVE("syncdolist.gui.button.task.archive");

      private final String translationKey;

      ButtonType(String translationKey) {
        this.translationKey=translationKey;
      }

      public String getDisplayName() {
        return StringUtils.translate(this.translationKey);
      }
    }
  }
}
