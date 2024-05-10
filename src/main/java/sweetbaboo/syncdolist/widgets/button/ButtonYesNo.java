package sweetbaboo.syncdolist.widgets.button;


import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;

public class ButtonYesNo extends ButtonGeneric {
  protected final String translationKey;

  public ButtonYesNo(int x, int y, int width, boolean rightAlign, String translationKey, boolean isCurrentlyOn, String... hoverStrings)
  {
    super(x, y, width, 20, "", hoverStrings);

    this.translationKey = translationKey;
    this.updateDisplayString(isCurrentlyOn);

    if (width < 0)
    {
      int w1 = this.getStringWidth(fi.dy.masa.malilib.gui.button.ButtonOnOff.getDisplayStringForStatus(translationKey, true));
      int w2 = this.getStringWidth(fi.dy.masa.malilib.gui.button.ButtonOnOff.getDisplayStringForStatus(translationKey, false));
      this.width = Math.max(w1, w2) + 10;
    }

    if (rightAlign)
    {
      this.x = x - this.width;
    }
  }

  public void updateDisplayString(boolean isCurrentlyOn)
  {
    this.displayString = getDisplayStringForStatus(this.translationKey, isCurrentlyOn);
  }

  public static String getDisplayStringForStatus(String translationKey, boolean isCurrentlyOn)
  {
    String strStatus = isCurrentlyOn ? "syncdolist.gui.label_colored.yes" : "syncdolist.gui.label_colored.no";
    return StringUtils.translate(translationKey, StringUtils.translate(strStatus));
  }
}
