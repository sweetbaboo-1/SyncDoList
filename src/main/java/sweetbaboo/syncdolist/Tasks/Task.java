package sweetbaboo.syncdolist.Tasks;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Date;

public class Task {
  // meta data
  public String name, author;
  public Date creationDate;
  public boolean completed;
  public String[] steps;
  public PlayerEntity[] workers;

  public Task(String name, String author, Date creationDate, boolean completed, String[] steps, PlayerEntity[] workers) {
    this.name=name;
    this.author=author;
    this.creationDate = creationDate;
    this.completed=completed;
    this.steps=steps;
    this.workers=workers;
  }
}
