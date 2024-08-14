package adb;

import java.io.IOException;

public class AdbShell extends AdbProcess {
    public AdbShell() {
        super("shell");
        try {
            out.write("\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeSync(String cmd) throws IOException {
        executeAsync(cmd);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executeAsync(String cmd) throws IOException {
        // System.out.println("Execute Command `" + cmd + "`");
        out.write(cmd);
        out.write("\n");
        out.flush();
    }
}
