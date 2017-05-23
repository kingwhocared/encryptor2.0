import java.io.*;
import java.util.Scanner;

/**
 * Created by stannis on 15/05/17.
 */
public abstract class Key implements java.io.Serializable{
    private static final String key_file_name = "key.bin";

    static public Key ReadFromFile(String key_file_path) {
        try {
            FileInputStream fileIn = new FileInputStream(key_file_path + key_file_name);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Key key = (Key) in.readObject();
            in.close();
            fileIn.close();
            return key;
        }catch(Throwable i) {
            i.printStackTrace();
            return null;
        }
    }

    final public void WriteToFile() {
        try {
            Scanner reader = new Scanner(System.in);
            System.out.println("Insert path to save the decryption key...");
            String path = reader.nextLine();
            String key_file_name = path + this.key_file_name;

            FileOutputStream fileOut = new FileOutputStream(key_file_name);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + key_file_name );
        }catch(IOException i) {
            i.printStackTrace();
        }
    }
}
