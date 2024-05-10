package sweetbaboo.syncdolist.widgets.button;

import fi.dy.masa.malilib.gui.LeftRight;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import org.apache.commons.lang3.StringUtils;

public class IconButton extends ButtonGeneric {

  public IconButton(int x, int y, int height, String displayName, IGuiIcon icon, String... hoverStrings) {
    super(x, y, icon.getWidth() + 2, height, displayName, icon, hoverStrings);
    this.x -= icon.getWidth() + 1;
  }


  @Override
  public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext)
  {
    if (this.visible)
    {
      this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

      int buttonStyle = this.getTextureOffset(this.hovered);

      RenderUtils.color(1f, 1f, 1f, 1f);

      if (this.renderDefaultBackground)
      {
        this.bindTexture(BUTTON_TEXTURES);
        RenderUtils.drawTexturedRect(this.x, this.y, 0, 46 + buttonStyle * 20, this.width / 2, this.height);
        RenderUtils.drawTexturedRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + buttonStyle * 20, this.width / 2, this.height);
      }

      if (this.icon != null)
      {
        int offset = 1;
        int x = this.alignment == LeftRight.LEFT ? this.x + offset : this.x + this.width - this.icon.getWidth() - offset;
        int y = this.y + (this.height - this.icon.getHeight()) / 2;
        int u = this.icon.getU() + buttonStyle * this.icon.getWidth();
        x -= 1;
        this.bindTexture(this.icon.getTexture());
        RenderUtils.drawTexturedRect(x, y, u, this.icon.getV(), this.icon.getWidth(), this.icon.getHeight());
      }

      if (!StringUtils.isBlank(this.displayString))
      {
        int y = this.y + (this.height - 8) / 2;
        int color = 0xE0E0E0;

        if (!this.enabled)
        {
          color = 0xA0A0A0;
        }
        else if (this.hovered)
        {
          color = 0xFFFFA0;
        }

        if (this.textCentered)
        {
          this.drawCenteredStringWithShadow(this.x + this.width / 2, y, color, this.displayString, drawContext);
        }
        else
        {
          int x = this.x + 6;

          if (this.icon != null && this.alignment == LeftRight.LEFT)
          {
            x += this.icon.getWidth() + 2;
          }

          this.drawStringWithShadow(x, y, color, this.displayString, drawContext);
        }
      }
    }
  }
}
