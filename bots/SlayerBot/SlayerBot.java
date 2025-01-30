package SlayerBot;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.BotInfo;
import dev.robocode.tankroyale.botapi.events.HitBotEvent;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;

import java.awt.*;

public class SlayerBot extends Bot {

    public static void main(String[] args) {
        new SlayerBot().start();
    }

    // Constructor, which loads the bot config file
    SlayerBot() {
        super(BotInfo.fromFile("SlayerBot.json"));
    }

    @Override
    public void run() {
        setBodyColor(Color.YELLOW);
        setTurretColor(Color.RED);
        setBulletColor(Color.YELLOW);
        setRadarColor(Color.WHITE);
        setScanColor(Color.YELLOW);

        while (isRunning()) {
            turnLeft(360.00);
            turnRight(360.00);
        }
    }
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        fire(2);
    }
}

