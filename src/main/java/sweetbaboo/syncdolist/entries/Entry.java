package sweetbaboo.syncdolist.entries;

public abstract class Entry {
  protected String text;
  protected int color;

  public Entry(String text, int color) {
    this.text=text;
    this.color = color;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color=color;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text=text;
  }

}
