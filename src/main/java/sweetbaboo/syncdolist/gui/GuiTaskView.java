package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.*;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.interfaces.IStringConsumerFeedback;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.entries.Step;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.manager.TaskManager;
import sweetbaboo.syncdolist.widgets.step.WidgetListSteps;
import sweetbaboo.syncdolist.widgets.step.WidgetStepItem;


public class GuiTaskView extends GuiListBase<Step, WidgetStepItem, WidgetListSteps> implements ISelectionListener<Step> {

  private Task task;
  private GuiTextFieldGeneric nameTextField;


  public GuiTaskView(Task task, GuiBase parent) {
    super(8, 60);
    this.setParent(parent);
    this.title=task.getText();
    this.task=task;
  }

  @Override
  public void initGui() {
    super.initGui();
    createStepNameTextField();
    createAddStepFields();
  }

  private void createAddStepFields() {
    int x = this.getListX();
    int y = this.height - 24;

    int width = StringUtils.getStringWidth(StringUtils.translate(ButtonListener.ButtonType.SAVE_TASK.getDisplayName())) + 8;
    createGenericButton(x, y, width, ButtonListener.ButtonType.SAVE_TASK);

    x += width + 2;
    width = StringUtils.getStringWidth(StringUtils.translate(ButtonListener.ButtonType.ADD_STEP.getDisplayName())) + 8;
    createGenericButton(x, y, width, ButtonListener.ButtonType.ADD_STEP);

    x += width + 2;
    width = StringUtils.getStringWidth(StringUtils.translate(GuiCreateTask.ButtonListener.ButtonType.ADD_TASK_NOTES.getDisplayName())) + 8;
    createGenericButton(x, y, width, ButtonListener.ButtonType.ADD_TASK_NOTES);
  }

  private void createGenericButton(int x, int y, int width, ButtonListener.ButtonType type) {
    ButtonGeneric button=new ButtonGeneric(x, y, width, GuiMainMenu.OBJECT_HEIGHT, type.getDisplayName());
    this.addButton(button, new ButtonListener(type, this));
  }

  private void createStepNameTextField() {
    int x=10;
    int y=24;
    int width=202;
    this.addLabel(x, y, -1, 16, 0xFFFFFFFF, StringUtils.translate("syncdolist.gui.label.task_creation.task_name"));
    y+=13;
    this.nameTextField=new GuiTextFieldGeneric(x + 1, y + 2, width, 16, textRenderer);
    this.nameTextField.setText(this.task.getText());
    this.addTextField(this.nameTextField, new GuiCreateTask.TextFieldListenerDummy());
    x+=width + 4;
    this.createGenericButton(x, y, -1, ButtonListener.ButtonType.SET_TASK_NAME);
  }

  @Override
  public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
    RenderUtils.drawOutlinedBox(this.getListX() + this.getBrowserWidth() + 6, this.getListY() + 22, GuiMainMenu.INFO_WIDTH, getMaxInfoHeight() / 2 - 2, 0xA0000000, GuiBase.COLOR_HORIZONTAL_BAR);
    RenderUtils.drawOutlinedBox(this.getListX() + this.getBrowserWidth() + 6, this.getListY() + 22 + getMaxInfoHeight() / 2 + 2, GuiMainMenu.INFO_WIDTH, getMaxInfoHeight() / 2 - 2, 0xA0000000, GuiBase.COLOR_HORIZONTAL_BAR);
    super.render(drawContext, mouseX, mouseY, partialTicks);
    if (!task.getMetaData().equals("")) {
      GuiMainMenu.drawSelectedEntryMetaData(this.getListX() + this.getBrowserWidth() + 9, this.getListY() + 25, task, "Task Notes:", drawContext);
    }
  }

  @Override
  protected WidgetListSteps createListWidget(int listX, int listY) {
    return new WidgetListSteps(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), task, this);
  }


  public static class ButtonListener implements IButtonActionListener {

    public final ButtonType type;
    public final GuiTaskView parent;

    public ButtonListener(ButtonType type, GuiTaskView parent) {
      this.type=type;
      this.parent=parent;
      if (this.parent.task == null) {
        this.parent.task=new Task();
      }
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      switch (this.type) {
        case ADD_STEP -> {
          Step step=new Step();
          GuiTextInput gui=new GuiTextInput(256, "syncdolist.gui.button.add_step", "", null, new StepEditor(step));
          gui.setParent(this.parent);
          GuiBase.openGui(gui);
          this.parent.task.addStep(step);
        }
        case SAVE_TASK -> {
          if (this.parent.task == null) {
            this.parent.task=new Task();
          }

          if (this.parent.task.getText().equals("") && this.parent.nameTextField.getText().equals("")) {
            this.parent.addMessage(Message.MessageType.ERROR, "syncdolist.error.task.create_task_fail");
            return;
          }

          this.parent.task.setText(this.parent.nameTextField.getText());
          TaskManager.getInstance().addTask(this.parent.task);
          this.parent.closeGui(true);
        }
        case SET_TASK_NAME -> this.parent.task.setText(this.parent.nameTextField.getText());
        case ADD_TASK_NOTES -> {
          GuiTextInput gui=new GuiTextInput(256, "syncdolist.gui.title.task_note.title", "", null, new TaskNoteEditor(this.parent.task));
          gui.setParent(this.parent);
          GuiBase.openGui(gui);
        }
      }
    }

    public enum ButtonType {
      SAVE_TASK("syncdolist.gui.button.save_task"),
      ADD_STEP("syncdolist.gui.button.add_step"),
      SET_TASK_NAME("syncdolist.gui.button.set"),
      ADD_TASK_NOTES("syncdolist.gui.button.add_step_notes");

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

    protected static class StepEditor implements IStringConsumerFeedback {
      private final Step step;

      public StepEditor(Step entry) {
        this.step=entry;
      }

      @Override
      public boolean setString(String string) {
        step.setText(string);
        return true;
      }
    }

    private static class TaskNoteEditor implements IStringConsumerFeedback {
      private final Task task;

      public TaskNoteEditor(Task entry) {
        this.task=entry;
      }

      @Override
      public boolean setString(String string) {
        task.setMetaData(string);
        return true;
      }
    }
  }

  @Override
  protected int getBrowserWidth() {
    return this.width - 175;
  }

  @Override
  protected int getBrowserHeight() {
    return this.height - 86;
  }

  @Override
  public void onSelectionChange(@Nullable Step entry) {
  }

  public int getMaxInfoHeight() {
    return this.getBrowserHeight() - 20;
  }
}
