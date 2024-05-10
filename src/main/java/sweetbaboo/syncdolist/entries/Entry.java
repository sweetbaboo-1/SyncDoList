package sweetbaboo.syncdolist.entries;

public abstract class Entry {
  protected String text;
  protected String metaData;

  public Entry(String text, String metaData) {
    this.text=text;
    this.metaData = metaData;
  }

  public Entry() {
    this.text = "";
    this.metaData = "";
  }

  public String getText() {
    return text;
  }

  public String getMetaData() {
    return metaData;
  }

  public void setMetaData(String metaData) {
    this.metaData=metaData;
  }

  public void setText(String text) {
    this.text=text;
  }
}
