package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Serializer {

    public static void serialize(Object serializable, String directory, String filename) {
        try {
            Files.createDirectories(Paths.get(directory));
            FileOutputStream fileOutStream = new FileOutputStream(directory + filename);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream);
            objectOutStream.writeObject(serializable);
            objectOutStream.close();
            fileOutStream.close();
            System.out.println("Serialized data is saved in " + filename);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Object deserialize(String filename, boolean delete) {
        Object input = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            input = in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded serialized data from " + filename);
            if (delete) {
                Files.deleteIfExists(Path.of(filename));
            }
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
        return input;
    }
}
