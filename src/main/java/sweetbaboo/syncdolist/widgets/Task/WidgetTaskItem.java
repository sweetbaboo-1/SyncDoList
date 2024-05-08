package sweetbaboo.syncdolist.widgets.Task;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.gui.GuiEditView;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.manager.TaskManager;

public class WidgetTaskItem extends WidgetListEntryBase<Task> {

  public final WidgetListTasks parent;
  public final Task task;
  public final boolean isOdd;
  public int buttonsStartX;
  public TaskManager manager;

  public WidgetTaskItem(int x, int y, int width, int height, boolean isOdd,
                        @Nullable Task task, int listIndex, WidgetListTasks parent) {
    super(x, y, width, height, task, listIndex);
    this.parent=parent;
    this.task=task;
    this.isOdd=isOdd;
    this.manager = TaskManager.getInstance();

    int posX=x + width - 2;
    int posY=y + 1;

    // Note: These are placed from right to left
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.REMOVE);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.EDIT);
    this.buttonsStartX=posX;
  }

  public int createButtonGeneric(int xRight, int y, ButtonListener.ButtonType type) {
    return this.addButton(new ButtonGeneric(xRight, y, -1, true, type.getDisplayName()), new ButtonListener(type, this)).getX() - 1;
  }

  @Override
  public boolean canSelectAt(int mouseX, int mouseY, int mouseButton) {
    return mouseX < this.buttonsStartX && super.canSelectAt(mouseX, mouseY, mouseButton);
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    RenderUtils.color(1f, 1f, 1f, 1f);

    boolean placementSelected=this.manager.getSelectedTask() == this.entry;

    // Draw a lighter background for the hovered and the selected entry
    if (selected || placementSelected || this.isMouseOver(mouseX, mouseY)) {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0707070);
    } else if (this.isOdd) {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0101010);
    }
    // Draw a slightly lighter background for even entries
    else {
      RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0xA0303030);
    }

    if (placementSelected) {
      RenderUtils.drawOutline(this.x, this.y, this.width, this.height, 0xFFE0E0E0);
    }

    RenderUtils.color(1, 1, 1, 1);

    if (!(this.entry == null)) {
      int yOffset = (this.height - this.fontHeight) / 2 + 1;
      this.drawStringWithShadow(this.x + 2, this.y + yOffset, this.entry.isCompleted() ? GuiMainMenu.COLOR_GREEN : GuiBase.COLOR_WHITE, this.entry.toString(), drawContext);
    }

    super.render(mouseX, mouseY, placementSelected, drawContext);
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
      if (this.type == ButtonType.REMOVE) {
        if (!GuiBase.isShiftDown()) {
          this.widget.parent.getParentGui().addMessage(Message.MessageType.ERROR, "syncdolist.error.task.remove_fail");
        } else {
          this.widget.manager.removeTask(this.widget.task);
          this.widget.parent.refreshEntries();
        }
      } else if (this.type == ButtonType.EDIT) {
        GuiBase.openGui(new GuiEditView());
      }
    }

    public enum ButtonType {
      EDIT("syncdolist.gui.button.task.edit"),
      REMOVE("syncdolist.gui.button.task.remove");

      private final String translationKey;

      ButtonType(String translationKey) {
        this.translationKey=translationKey;
      }

      public String getTranslationKey() {
        return this.translationKey;
      }

      public String getDisplayName() {
        return StringUtils.translate(this.translationKey);
      }
    }
  }
}
