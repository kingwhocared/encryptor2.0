import java.io.*;

/**
 * Created by stannis on 15/05/17.
 */
public abstract class Key {
    public Key() {
        this.WriteToFile("key.bin");
    }

    static public Key ReadFromFile(String key_file_name) {
        try {
            FileInputStream fileIn = new FileInputStream(key_file_name);
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


    public void WriteToFile(String key_file_name) {
        try {
            FileOutputStream fileOut = new FileOutputStream(key_file_name);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + key_file_name);
        }catch(IOException i) {
            i.printStackTrace();
        }
    }
}
