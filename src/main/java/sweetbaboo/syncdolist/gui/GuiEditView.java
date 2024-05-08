package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.StringUtils;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.widgets.Task.WidgetListTasks;

public class GuiEditView extends GuiBase {

  public GuiEditView() {
    super();
    this.setParent(new GuiMainMenu());
    String version=String.format("v%s", Reference.MOD_VERSION);
    this.title=StringUtils.translate("syncdolist.gui.title.edit_task", version);
  }
}
