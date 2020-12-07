package services;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

public class LoggerFileTest {
    @Rule
    public final TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testWrite() throws Exception {
        File file = folder.newFile("Hello.txt");
        try (Logger logger = new LoggerFile(file.toString())) {
            logger.write("Hello");
        }

        String res = Files.readAllLines(file.toPath()).toString();
        Assert.assertEquals("[Hello]", res);
    }

    @Test
    public void testClose() {
    }
}