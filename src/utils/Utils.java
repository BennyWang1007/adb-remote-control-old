package utils;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Utils {
    public static String getDeviceName() {
        ArrayList<String> devices = new ArrayList<>();
        try {
            Process process = new ProcessBuilder("adb", "devices").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine(); // Skip first line
            while ((line = reader.readLine()) != null) {
                if (line.contains("device")) {
                    devices.add(line.split("\t")[0]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get device name", e);
        }
        if (devices.size() == 0) {
            throw new RuntimeException("No devices found");
        }
        if (devices.size() == 1) {
            return devices.get(0);
        }
        System.out.println("Multiple devices found, please select one:");
        for (int i = 0; i < devices.size(); i++) {
            System.out.println(i + ": " + devices.get(i));
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int choice;
        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input", e);
        }
        return devices.get(choice);
    }

    public static int[] getScreenSize(String deviceName) {
        try {
            Process process = new ProcessBuilder("adb", "-s", deviceName, "shell", "wm", "size").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int[] size = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Physical size:")) {
                    String[] parts = line.split(":")[1].trim().split("x");
                    size = new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
                }
                if (line.contains("Override size:")) {
                    String[] parts = line.split(":")[1].trim().split("x");
                    return new int[] {Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
                }
            }
            if (size != null) {
                return size;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get screen size", e);
        }
        throw new RuntimeException("Failed to get screen size");
    }
}
