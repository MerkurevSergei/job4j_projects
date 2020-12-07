package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The Game logger implementation.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class LoggerFile implements Logger {
    private FileWriter fileWriter;

    /**
     * @param logFile init
     */
    public LoggerFile(String logFile) throws IOException {
        try {
            fileWriter = new FileWriter(logFile, StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    /**
     * @param log file for log
     */
    @Override
    public final void write(String log) {
        try {
            fileWriter.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException if close throw
     */
    @Override
    public final void close() throws IOException {
        fileWriter.close();
    }
}
