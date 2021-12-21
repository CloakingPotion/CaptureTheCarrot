import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.exitium.capturethecarrot.Main;
import org.bukkit.Bukkit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

public class MainTest {
  private ServerMock server;
  private Main plugin;

  @Before
  public void setUp() {
    // Start the mock server
    server = MockBukkit.mock();
    // Load your plugin
    plugin = MockBukkit.load(Main.class);
  }

  @After
  public void tearDown() {
    // Stop the mock server
    MockBukkit.unmock();
  }

  @Test
  public void thisTestWillFail() {
    Logger logger = Bukkit.getLogger();

    logger.info("Creating 20 players...");
    server.setPlayers(20);

    String joinCommand = "ctc join test";

    logger.info("Having players join game...");

    for (int i = 0; i < 20; i++) {
      logger.info("Player " + i + " running join command...");
      server.getPlayer(i).performCommand(joinCommand);
      logger.info("Player " + i + " join command complete");
    }
  }
}
