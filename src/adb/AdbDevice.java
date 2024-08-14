package adb;

import thread.KeyboardInputThread;
import utils.AndroidKeyEvent;
import utils.Utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class AdbDevice {
    private KeyboardInputThread keyboardThread;
    private BufferedImage screenshot;
    private BufferedImage prevScreenshot;

    private String deviceName;
    private int screenWidth;
    private int screenHeight;
    
    private Process shellProcess;
    private PrintWriter shellInput;
    private BufferedReader shellOutput;

    private Process inputShellProcess;
    private PrintWriter inputShellInput;

    public AdbDevice() {
        this.keyboardThread = new KeyboardInputThread();
        keyboardThread.start();
        this.deviceName = Utils.getDeviceName();
        int[] size = Utils.getScreenSize(deviceName);
        this.screenWidth = size[0];
        this.screenHeight = size[1];
        initShell();
        initCheck();
    }

    private void initShell() {
        try {
            shellProcess = new ProcessBuilder("adb", "-s", deviceName, "shell").start();
            shellInput = new PrintWriter(shellProcess.getOutputStream(), true);
            shellOutput = new BufferedReader(new InputStreamReader(shellProcess.getInputStream()));

            inputShellProcess = new ProcessBuilder("adb", "-s", deviceName, "shell").start();
            inputShellInput = new PrintWriter(inputShellProcess.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize shell", e);
        }
    }

    private void initCheck() {
        this.prevScreenshot = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_4BYTE_ABGR);
        this.screenshot = null;
        BufferedImage screenshot = screenshot();
        if (screenshot != null) { return; }
        throw new RuntimeException("Failed to get screenshot");
    }

    public BufferedImage screenshot() {
        // byte[] img = captureScreenShell(); // faster but buggy, sometimes colors are wrong and screen is shifted
        byte[] img = captureScreenRow(); // slower but stable
        if (img.length == 0) {
            System.out.println("Failed to capture screen");
            return prevScreenshot;
        } else {
            screenshot = rawByteArrayToImage(img);
        }
        if (screenshot != null) {
            prevScreenshot = screenshot;
            return screenshot;
        }
        throw new RuntimeException("Failed to get screenshot");
    }

    public String getDeviceName() { return deviceName; }

    // TODO: fix this
    private byte[] captureScreenShell() {
        try {
            shellInput.println("screencap");
            shellInput.flush();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = shellProcess.getInputStream().read(buffer)) != -1) {
                baos.write(buffer, 0, len);
                if (baos.size() >= screenWidth * screenHeight * 4 + 12) {
                    break;
                }
            }
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screen", e);
        }
    }

    private byte[] captureScreenRow() {
        try {
            Process process = new ProcessBuilder("adb", "-s", deviceName, "exec-out", "screencap").start();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = process.getInputStream().read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screen", e);
        }
    }

    private BufferedImage rawByteArrayToImage(byte[] img) {

        // int screenWidth = ((img[0] & 0xFF) << 24) | ((img[1] & 0xFF) << 16) | ((img[2] & 0xFF) << 8) | (img[3] & 0xFF);
        // int screenHeight = ((img[4] & 0xFF) << 24) | ((img[5] & 0xFF) << 16) | ((img[6] & 0xFF) << 8) | (img[7] & 0xFF);
        // int pixelFormat = ((img[8] & 0xFF) << 24) | ((img[9] & 0xFF) << 16) | ((img[10] & 0xFF) << 8) | (img[11] & 0xFF);

        int bytesPerPixel = 4;
        int expectedSize = screenWidth * screenHeight * bytesPerPixel;

        // Create a BufferedImage and populate it with the pixel data
        BufferedImage image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_4BYTE_ABGR);
        byte[] imageData = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        // Copy the raw image data, flipping the color channels from RGBA to ABGR
        for (int i = 0; i < expectedSize; i += 4) {
            imageData[i] = img[i + 3 + 12];     // A
            imageData[i + 1] = img[i + 2 + 12]; // B
            imageData[i + 2] = img[i + 1 + 12]; // G
            imageData[i + 3] = img[i + 12];     // R
        }

        return image;
    }


    // ------------------------ Input ------------------------

    private void inputExecute(String command) {
        inputShellInput.println(command);
        inputShellInput.flush();
    }

    public void text(String text) {
        keyboardThread.addText(text);
    }

    public void type(AndroidKeyEvent key) {
        keyboardThread.addKey(key);
    }

    public void click(int x, int y) {
        inputExecute("input tap " + x + " " + y);
    }

    public void swipe(int downX, int downY, int upX, int upY, long duration) {
        inputExecute("input swipe " + downX + " " + downY + " " + upX + " " + upY + " " + duration);
    }

	public void draganddrop(int downX, int downY, int upX, int upY, long duration) {
        inputExecute("input draganddrop " + downX + " " + downY + " " + upX + " " + upY + " " + duration);
	}

    public void close() {
        if (shellProcess != null) { shellProcess.destroy(); }
        if (shellInput != null) { shellInput.close(); }
        if (shellOutput != null) { try { shellOutput.close(); } catch (IOException e) { e.printStackTrace(); } }
        if (inputShellProcess != null) { inputShellProcess.destroy(); }
        if (inputShellInput != null) { inputShellInput.close(); }

        // try {
        //     if (shellOutput != null) {
        //         shellOutput.close();
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }
}
