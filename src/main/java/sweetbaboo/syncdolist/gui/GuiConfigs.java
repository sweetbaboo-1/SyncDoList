package sweetbaboo.syncdolist.gui;


import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import sweetbaboo.syncdolist.Reference;
import sweetbaboo.syncdolist.config.Hotkeys;

public class GuiConfigs extends GuiConfigsBase {
  private static ConfigGuiTab configTab=ConfigGuiTab.BACK;



  public GuiConfigs() {
    super(10, 50, Reference.MOD_ID, null, "syncdolist.gui.button.config_gui.hotkeys");
  }

  @Override
  public void initGui() {
    super.initGui();
    this.clearOptions();

    int x=10;
    int y=26;

    for (ConfigGuiTab tab : ConfigGuiTab.VALUES) {
      x+=this.createButton(x, y, -1, tab);
    }
  }

  private int createButton(int x, int y, int width, ConfigGuiTab tab) {
    ButtonGeneric button=new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
    button.setEnabled(true);
    this.addButton(button, new ButtonListener());
    return button.getWidth() + 2;
  }

  @Override
  protected int getConfigWidth() {
    return super.getConfigWidth();
  }

  @Override
  public List<ConfigOptionWrapper> getConfigs() {
    List<? extends IConfigBase> configs;
    ConfigGuiTab tab=GuiConfigs.configTab;

    if (tab == ConfigGuiTab.BACK) {
      configs=Hotkeys.HOTKEY_LIST;
    } else {
      return Collections.emptyList();
    }

    return ConfigOptionWrapper.createFor(configs);
  }

  public enum ConfigGuiTab {
    BACK("syncdolist.gui.button.config_gui.back");

    public static final ImmutableList<ConfigGuiTab> VALUES=ImmutableList.copyOf(values());
    private final String translationKey;

    ConfigGuiTab(String translationKey) {
      this.translationKey=translationKey;
    }

    public String getDisplayName() {
      return StringUtils.translate(this.translationKey);
    }
  }

  private static class ButtonListener implements IButtonActionListener {
    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      GuiBase.openGui(new GuiMainMenu());
    }
  }
}

