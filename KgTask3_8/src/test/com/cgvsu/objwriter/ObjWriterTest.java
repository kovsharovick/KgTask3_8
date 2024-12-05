package test.com.cgvsu.objwriter;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.cgvsu.afinka.AffineTransform.affineTransform;
import static org.junit.Assert.assertEquals;

public class ObjWriterTest {
    @Test
    public void testAffine1() throws IOException {
        String expected = TestStrings.TEST_1;
        Path fileName = Path.of("3DModels/Faceform/fortest1.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);
        affineTransform(model, 2, 3, 1, 0, 0, 0, "xyz", 10, 100, 20);
        ObjWriter.write(model, new BufferedWriter(new FileWriter("test.obj")));
        String actual = Files.readString(Path.of("test.obj"));
        assertEquals(expected, actual);
    }


    @Test
    public void testAffine2() throws IOException {
        String expected = TestStrings.TEST_2;
        Path fileName = Path.of("3DModels/Faceform/fortest2.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);
        affineTransform(model, 2, 3, 1, 0, 0, 0, "xyz", 10, 100, 20);
        ObjWriter.write(model, new BufferedWriter(new FileWriter("test.obj")));
        String actual = Files.readString(Path.of("test.obj"));
        assertEquals(expected, actual);
    }

    @Test
    public void testAffine3() throws IOException {
        String expected = TestStrings.TEST_3;
        Path fileName = Path.of("3DModels/Faceform/fortest3.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);
        affineTransform(model, 1, 1, 2, 90, 180, 30, "zyx", 10, 100, 100);
        ObjWriter.write(model, new BufferedWriter(new FileWriter("test.obj")));
        String actual = Files.readString(Path.of("test.obj"));
        assertEquals(expected, actual);
    }

    private static class TestStrings {

        private static final String TEST_1 = """
                v 22.0 306.0 23.0
                v 22.0 312.0 25.0
                v 32.0 312.0 22.0
                v 22.0 303.0 21.0
                """;

        private static final String TEST_2 = "";

        private static final String TEST_3 = """
                v 38.875645 -95.334595 -210.0
                v 42.839745 -94.200615 -210.0
                v 40.375645 -97.93267 -200.0
                v 38.00962 -95.834595 -206.0
                v -3.9256258 -121.200615 -210.0
                vt 5.0 4.0
                vt 6.0 3.0
                """;
    }
}

