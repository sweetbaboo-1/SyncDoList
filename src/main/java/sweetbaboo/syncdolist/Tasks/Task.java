package sweetbaboo.syncdolist.Tasks;

import fi.dy.masa.malilib.interfaces.IStringValue;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Date;

public class Task {
  // meta data
  public String name, author;
  public Date creationDate;
  public boolean completed;
  public String[] steps;
  public String[] notes;
  public PlayerEntity[] workers;

  public Task(String name, String author, Date creationDate, boolean completed, String[] steps, String[] notes, PlayerEntity[] workers) {
    this.name=name;
    this.author=author;
    this.creationDate = creationDate;
    this.completed=completed;
    this.steps=steps;
    this.notes = notes;
    this.workers=workers;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
