package sweetbaboo.syncdolist.widgets.step;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiTextInput;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.interfaces.IStringConsumer;
import fi.dy.masa.malilib.interfaces.IStringConsumerFeedback;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.DrawContext;
import sweetbaboo.syncdolist.entries.Step;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.gui.GuiMainMenu;
import sweetbaboo.syncdolist.gui.Icons;
import sweetbaboo.syncdolist.manager.TaskManager;
import sweetbaboo.syncdolist.widgets.button.ButtonYesNo;
import sweetbaboo.syncdolist.widgets.button.IconButton;

import java.util.Objects;

public class WidgetStepItem extends WidgetListEntryBase<Step> {

  public final WidgetListSteps parent;
  public final Step step;
  public final boolean isOdd;
  public int buttonsStartX;
  public TaskManager manager;
  public int browserHeight;

  public WidgetStepItem(int x, int y, int width, int height, Step step, boolean isOdd, int listIndex, WidgetListSteps parent, int browserHeight) {
    super(x, y, width, height, step, listIndex);
    this.parent=parent;
    this.step=step;
    this.isOdd=isOdd;
    this.manager=TaskManager.getInstance();
    this.browserHeight = browserHeight;

    int posX=x + width - 2;
    int posY=y + 1;

    // Note: These are placed from right to left
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.REMOVE, null);
    posX=this.createButtonYesNo(posX, posY, Objects.requireNonNull(this.getEntry()).isCompleted(), ButtonListener.ButtonType.TOGGLE_COMPLETED);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.NOTE, null);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.RENAME, null);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.DOWN, Icons.ARROW_DOWN);
    posX=this.createButtonGeneric(posX, posY, ButtonListener.ButtonType.UP, Icons.ARROW_UP);
    this.buttonsStartX=posX;
  }

  @Override
  public boolean canSelectAt(int mouseX, int mouseY, int mouseButton) {
    return mouseX < this.buttonsStartX && super.canSelectAt(mouseX, mouseY, mouseButton);
  }

  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    RenderUtils.color(1f, 1f, 1f, 1f);

    // Draw a lighter background for the hovered and the selected entry
    if (selected || this.isMouseOver(mouseX, mouseY)) {
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
        GuiMainMenu.drawSelectedEntryMetaData(this.x + this.width + 20, this.height + 55 + browserHeight / 2, entry, "Step Notes:", drawContext);
      }
    }

    RenderUtils.color(1, 1, 1, 1);

    if (!(this.entry == null)) {
      int yOffset=(this.height - this.fontHeight) / 2 + 1;
      this.drawStringWithShadow(this.x + 2, this.y + yOffset, this.entry.isCompleted() ? GuiMainMenu.COLOR_GREEN : GuiMainMenu.COLOR_RED, this.entry.getText(), drawContext);
    }

    super.render(mouseX, mouseY, false, drawContext);
  }

  public int createButtonGeneric(int xRight, int y, ButtonListener.ButtonType type, IGuiIcon icon) {
    if (icon == null) {
      return this.addButton(new ButtonGeneric(xRight, y, -1, true, type.getDisplayName()), new ButtonListener(type, this)).getX() - 1;
    } else {
      return this.addButton(new IconButton(xRight, y, 20, type.getDisplayName(), icon), new ButtonListener(type, this)).getX() - 1;
    }
  }

  public int createButtonYesNo(int xRight, int y, boolean isCurrentlyOn, ButtonListener.ButtonType type) {
    ButtonYesNo button=new ButtonYesNo(xRight, y, -1, true, type.getTranslationKey(), isCurrentlyOn);
    return this.addButton(button, new ButtonListener(type, this)).getX() - 2;
  }

  public static class ButtonListener implements IButtonActionListener {
    public final ButtonType type;
    public final WidgetStepItem widget;

    public ButtonListener(ButtonType type, WidgetStepItem widget) {
      this.type=type;
      this.widget=widget;
    }

    protected static class NotesEditor implements IStringConsumerFeedback
    {
      private final Step step;

      public NotesEditor(Step entry) {
        this.step = entry;
      }

      @Override
      public boolean setString(String string)
      {
        step.setMetaData(string);
        return true;
      }
    }

    protected static class NameEditor implements IStringConsumerFeedback
    {
      private final Step step;

      public NameEditor(Step entry) {
        this.step = entry;
      }

      @Override
      public boolean setString(String string)
      {
        step.setText(string);
        return true;
      }
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      if (this.widget.entry == null) {
        return;
      }

      switch (this.type) {
        case UP -> {
          this.widget.manager.setSelectedTask(this.widget.parent.task);
          this.widget.manager.moveStepUp(this.widget.entry, this.widget.parent.task);
          this.widget.parent.refreshEntries();
        }
        case DOWN -> {
          this.widget.manager.setSelectedTask(this.widget.parent.task);
          this.widget.manager.moveStepDown(this.widget.entry, this.widget.parent.task);
          this.widget.parent.refreshEntries();
        }
        case NOTE -> {
          GuiTextInput gui = new GuiTextInput(1024, "syncdolist.gui.title.step_note.title", this.widget.entry.getMetaData(), null, new NotesEditor(this.widget.entry));
          gui.setParent(this.widget.parent.parent);
          GuiBase.openGui(gui);
        }
        case TOGGLE_COMPLETED -> {
          this.widget.entry.setCompleted(!this.widget.entry.isCompleted());
          this.widget.parent.refreshEntries();
        }
        case REMOVE -> {
          this.widget.parent.task.getSteps().remove(this.widget.entry);
          this.widget.parent.refreshEntries();
        }
        case RENAME -> {
          GuiTextInput gui = new GuiTextInput(1024, "syncdolist.gui.title.step_note.name", this.widget.entry.getText(), null, new NameEditor(this.widget.entry));
          gui.setParent(this.widget.parent.parent);
          GuiBase.openGui(gui);
        }
      }
    }

    public enum ButtonType {
      NOTE("syncdolist.gui.button.step.note"),
      TOGGLE_COMPLETED("syncdolist.gui.button.steps.completed"),
      UP("syncdolist.gui.button.task.up"),
      REMOVE("syncdolist.gui.button.step.remove"),
      RENAME("syncdolist.gui.button.step.rename"),
      DOWN("syncdolist.gui.button.task.down");

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