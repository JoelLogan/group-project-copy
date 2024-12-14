package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {

    public static void serialize(Object serializable, String fileLocation) {
        try {
            FileOutputStream fileOutStream = new FileOutputStream(fileLocation);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream);
            objectOutStream.writeObject(serializable);
            objectOutStream.close();
            fileOutStream.close();
            System.out.println("Serialized data is saved to " + fileLocation);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static Object deserialize(String filename) throws IOException, ClassNotFoundException {
        Object input = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            input = in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded serialized data from " + filename);
        } catch (IOException i) {
            i.printStackTrace();
            throw new IOException("File not found.");
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            throw new ClassNotFoundException("File not loaded correctly.");
        }
        return input;
    }
}
