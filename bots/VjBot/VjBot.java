import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import java.awt.Color;

// ------------------------------------------------------------------
// Vijays Bot
// ------------------------------------------------------------------
// A Challenge bot original made based on Fire Bot.
// ReAuthored and Ported to Robocode Tank Royale by Vijay Ravi Adyanthaya and Darsh Adyanthaya.
//
// Sits still. Spins gun around. Moves when hit.
// ------------------------------------------------------------------
public class VjBot extends Bot {

    int dist = 50; // Distance to move when we're hit, forward or back
    int turnCounter=8;
    // The main method starts our bot
    public static void main(String[] args) {
        new VjBot().start();
    }

    // Constructor, which loads the bot settings file
    VjBot() {
        super(BotInfo.fromFile("VjBot.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Set colors
        setBodyColor(new Color(0xFF, 0xAA, 0x00));   // orange
        setGunColor(new Color(0xFF, 0x77, 0x00));    // dark orange
        setTurretColor(new Color(0xFF, 0x77, 0x00)); // dark orange
        setRadarColor(new Color(0xFF, 0x00, 0x00));  // red
        setScanColor(new Color(0xFF, 0x00, 0x00));   // red
        setBulletColor(new Color(0x00, 0x88, 0xFF)); // light blue
        turnCounter = 0;

        setGunTurnRate(15);
        // Spin the gun around slowly... forever
//        while (isRunning()) {
//            // Turn the gun a bit if the bot if the target speed is 0
//            turnGunLeft(5);
//        }
        while (isRunning()) {

            if (turnCounter % 64 == 0) {
                // Straighten out, if we were hit by a bullet (ends turning)
                setTurnRate(0);
                fire(1);
                // Go forward with a target speed of 4
                setTargetSpeed(4);
            }
            turnCounter++;
            fire(3);
            go(); // execute turn
        }
    }

    // We scanned another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // If the other bot is close by, and we have plenty of life, fire hard!
        var distance = distanceTo(e.getX(), e.getY());
        if (distance < 50 && getEnergy() > 50) {
            fire(3);
            fire(3);
        } else {
            // Otherwise, only fire 1
            fire(1);
            fire(3);
        }
        // Rescan
        rescan();
    }

    // We were hit by a bullet -> turn perpendicular to the bullet, and move a bit
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Turn perpendicular to the bullet direction
        turnLeft(normalizeRelativeAngle(90 - (getDirection() - e.getBullet().getDirection())));

        // Move forward or backward depending if the distance is positive or negative
        forward(dist);
        dist *= -1; // Change distance, meaning forward or backward direction

        // Rescan
        rescan();
    }

    // We have hit another bot -> aim at it and fire hard!
    @Override
    public void onHitBot(HitBotEvent e) {
        // Turn gun to the bullet direction
        var direction = directionTo(e.getX(), e.getY());
        var gunBearing = normalizeRelativeAngle(direction - getGunDirection());
        turnGunLeft(gunBearing);

        // Fire hard
        fire(3);
    }
}
