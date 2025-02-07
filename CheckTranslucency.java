import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;

public class CheckTranslucency {
    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        if (gc.isTranslucencyCapable()) {
            System.out.println("PERPIXEL_TRANSLUCENT is supported.");
        } else {
            System.out.println("PERPIXEL_TRANSLUCENT is not supported.");
        }
    }
}