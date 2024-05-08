package sweetbaboo.syncdolist.gui;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.StringUtils;
import sweetbaboo.syncdolist.Reference;

public class GuiCreateTask extends GuiBase {
  public GuiCreateTask() {
    super();
    this.setParent(new GuiMainMenu());
    String version=String.format("v%s", Reference.MOD_VERSION);
    this.title=StringUtils.translate("syncdolist.gui.title.add_task", version);
  }

  @Override
  public void initGui() {
    super.initGui();

  }
}
