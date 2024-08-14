package thread;

import adb.AdbDevice;
import utils.Config;
import view.ScreenPanel;

import java.awt.image.BufferedImage;

public class ScreenshotThread extends Thread {
    private ScreenPanel screenPanel;
    private AdbDevice adbDevice;

    public ScreenshotThread(ScreenPanel screenPanel, AdbDevice adbDevice) {
        super("ScreenshotThread");
        this.screenPanel = screenPanel;
        this.adbDevice = adbDevice;
    }

    public void run() {
        while (!Thread.interrupted()) {
            BufferedImage screenshot = adbDevice.screenshot();
            screenPanel.setScreenshot(screenshot);
            try {
                Thread.sleep(Config.timeout);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}
