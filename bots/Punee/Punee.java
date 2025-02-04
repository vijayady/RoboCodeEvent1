import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;


public class Punee extends Bot {

    // The main method starts our bot
    public static void main(String[] args) {
        new Punee().start();
    }

    // Constructor, which loads the bot config file
    Punee() {
        super(BotInfo.fromFile("Punee.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Repeat while the bot is running
        while (isRunning()) {
            forward(1000);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        fire(3);
    }

}
