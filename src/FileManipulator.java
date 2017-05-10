import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by stannis on 09/05/17.
 */
public abstract class FileManipulator {
    protected View.MainUserRequest request;
    public void ManipulateFile(String fileinput, String fileoutput) {
        try {
            ByteManipulator manipulator = (x) -> x;
            switch (request) {
                case ENCRIPTION:
                    manipulator = (x) -> {return EncodeByte(x);};
                    break;
                case DECRYPTION:
                    manipulator = (x) -> {return DecodeByte(x);};
                    break;
            }
            FileOutputStream out = new FileOutputStream(fileoutput);
            InputStream input = new FileInputStream(fileinput);
            int a = 0;
            a = input.read();
            while (a != -1) {
                out.write(manipulator.manipulate(a));
                a = input.read();
            }

            input.close();
            out.close();
        }
        catch (Throwable e){
            System.out.println("Failure. Exception thrown in i/o.");
        }
    }

    abstract int EncodeByte(int to_encode);
    abstract int DecodeByte(int to_decode);

    interface ByteManipulator {
        int manipulate(int a);
    }

}
