package ShadowBot;

import dev.robocode.tankroyale.botapi.Bot;
import dev.robocode.tankroyale.botapi.BotInfo;
import dev.robocode.tankroyale.botapi.events.HitBotEvent;
import dev.robocode.tankroyale.botapi.events.HitWallEvent;
import dev.robocode.tankroyale.botapi.events.ScannedBotEvent;

import java.awt.*;

public class ShadowBot extends Bot {

    public static void main(String[] args) {
        new ShadowBot().start();
    }

    // Constructor, which loads the bot config file
    ShadowBot() {
        super(BotInfo.fromFile("ShadowBot.json"));
    }

    @Override
    public void run() {
        setBodyColor(Color.BLUE);
        setTurretColor(Color.RED);
        setBulletColor(Color.YELLOW);
        setRadarColor(Color.RED);
        setScanColor(Color.YELLOW);

        while (isRunning()) {
            turnLeft(200.00);
            setForward(1500.00);
            setTurnRight(100.00);
        }
    }
    @Override
    public void onScannedBot(ScannedBotEvent e) {
            fire(2);
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        back(200);
        turnLeft(100);
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        back(100);
        turnRight(200);
        fire(3);
    }
}

