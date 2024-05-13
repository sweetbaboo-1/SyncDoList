package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.jetbrains.annotations.Nullable;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.Utils;
import sweetbaboo.syncdolist.entries.Entry;
import sweetbaboo.syncdolist.entries.Task;
import sweetbaboo.syncdolist.manager.TaskManager;
import sweetbaboo.syncdolist.network.ModMessages;
import sweetbaboo.syncdolist.widgets.task.WidgetListTasks;
import sweetbaboo.syncdolist.widgets.task.WidgetTaskItem;

import java.util.ArrayList;
import java.util.List;

public class GuiMainMenu extends GuiListBase<Task, WidgetTaskItem, WidgetListTasks> implements ISelectionListener<Task> {

  public static final int COLOR_GREEN=0x54FC54;
  public static final int COLOR_RED=0xFC5454;

  public static final int INFO_WIDTH=150;
  public static final int OBJECT_HEIGHT=20;

  public TaskManager manager;

  public GuiMainMenu() {
    super(12, 30);
    String version=String.format("v%s", Reference.MOD_VERSION);
    this.title=StringUtils.translate("syncdolist.gui.title.main_menu", version) + " => Tasks";
    this.manager = TaskManager.refreshInstance();
  }

  @Override
  protected WidgetListTasks createListWidget(int listX, int listY) {
    return new WidgetListTasks(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), this);
  }

  @Override
  public void initGui() {
    super.initGui();
    genButtons();
  }

  @Override
  protected void closeGui(boolean showParent) {
    Utils.toJson(manager.getTasks(), false);
    super.closeGui(showParent);
//    ClientPlayNetworking.send(ModMessages.SAVE_TASKS_ID, PacketByteBufs.create());
  }

  private void genButtons() {
    int x=this.width - 15 - this.getStringWidth(ButtonListener.ButtonType.CONFIGURATION.getDisplayName());
    int y=12;

    this.createChangeMenuButton(x, y, ButtonListener.ButtonType.CONFIGURATION);
    this.createChangeMenuButton(12, this.height - 30, ButtonListener.ButtonType.ADD_TASK);
  }

  @Override
  public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
    RenderUtils.drawOutlinedBox(this.getListX() + this.getBrowserWidth() + 6, this.getListY() + 22, GuiMainMenu.INFO_WIDTH, getMaxInfoHeight(), 0xA0000000, GuiBase.COLOR_HORIZONTAL_BAR);
    super.render(drawContext, mouseX, mouseY, partialTicks);
  }

  @Override
  public void onSelectionChange(@Nullable Task task) {
    manager.setSelectedTask(task);
  }

  public static class ButtonListener implements IButtonActionListener {
    private final ButtonType type;
    private final GuiBase parent;

    public ButtonListener(ButtonType type, GuiBase parent) {
      this.type=type;
      this.parent = parent;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      switch (this.type) {
        case CONFIGURATION -> {
          GuiBase.openGui(new GuiConfigs(this.parent));
        }
        case ADD_TASK -> GuiBase.openGui(new GuiCreateTask(this.parent));
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

  @Override
  protected int getBrowserWidth() {
    return this.width - 175;
  }

  @Override
  protected int getBrowserHeight() {
    return this.height - 64;
  }

  private void createChangeMenuButton(int x, int y, ButtonListener.ButtonType type) {
    ButtonGeneric button=new ButtonGeneric(x, y, -1, OBJECT_HEIGHT, type.getDisplayName());
    this.addButton(button, new ButtonListener(type, this));
  }

  public static List<String> divideString(String input, int maxWidth) {
    if (input == null) {
      return new ArrayList<>();
    }
    List<String> dividedStrings = new ArrayList<>();
    String[] words = input.split("\\s+");
    StringBuilder currentLine = new StringBuilder();

    for (String word : words) {
      if (currentLine.length() == 0) {
        currentLine.append(word);
      } else {
        String testLine = currentLine + " " + word;
        int width = StringUtils.getStringWidth(testLine);
        if (width <= maxWidth) {
          currentLine.append(" ").append(word);
        } else {
          dividedStrings.add(currentLine.toString());
          currentLine = new StringBuilder(word);
        }
      }
    }

    if (currentLine.length() > 0) {
      dividedStrings.add(currentLine.toString());
    }

    return dividedStrings;
  }

  public static void drawSelectedEntryMetaData(int x, int y, Entry entry, String title, DrawContext drawContext) {
    if (entry == null) {
      return;
    }

    var mc = MinecraftClient.getInstance();
    var textRenderer = mc.textRenderer;

    int textColor=0xC0C0C0C0;
    drawContext.drawText(textRenderer, title, x, y, textColor, false);
    y+=12;

    for (String str : GuiMainMenu.divideString(entry.getMetaData(), GuiMainMenu.INFO_WIDTH)) {
      drawContext.drawText(textRenderer, str, x, y, textColor, false);
      y+=12;
    }
  }

  public int getMaxInfoHeight() {
    return this.getBrowserHeight();
  }
}
