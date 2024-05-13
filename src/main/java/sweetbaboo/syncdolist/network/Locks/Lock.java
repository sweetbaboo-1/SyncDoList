package sweetbaboo.syncdolist.network.Locks;

import net.minecraft.server.network.ServerPlayerEntity;

public class Lock {
  private ServerPlayerEntity player;
  private boolean lock;

  public Lock(ServerPlayerEntity player, boolean lock) {
    this.player=player;
    this.lock=lock;
  }

  public ServerPlayerEntity getPlayer() {
    return player;
  }

  public void setPlayer(ServerPlayerEntity player) {
    this.player=player;
  }

  public boolean isLock() {
    return lock;
  }

  public void setLock(boolean lock) {
    this.lock=lock;
  }
}
