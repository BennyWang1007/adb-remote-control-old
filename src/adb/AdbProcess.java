package adb;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import utils.Config;

public class AdbProcess implements Closeable {
    public BufferedInputStream in;
    public BufferedWriter out;
    private Process process;

    public AdbProcess(String... command) {
        try {
            process = new ProcessBuilder(combine(Config.adbPath, command))
                .redirectInput(ProcessBuilder.Redirect.PIPE)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .start();
            in = new BufferedInputStream(process.getInputStream());
            out = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String[] combine(String a, String[] b) {
        ArrayList<Object> combined = new ArrayList<>();
        combined.add(a);
        Collections.addAll(combined, b);
        return combined.toArray(new String[combined.size()]);
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
    }
}
