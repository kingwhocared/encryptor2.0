import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by stannis on 09/05/17.
 */
public class FileManipulator {
    protected View.MainUserRequest request;
    Code code;
    public FileManipulator(Code code, View.MainUserRequest request) {
        this.code = code;
        this.request = request;
    }

    protected int EncodeByte(int to_encode) {
        return code.Encode(to_encode);
    }

    protected int DecodeByte(int to_decode) {
        return code.Decode(to_decode);
    }

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

    interface ByteManipulator {
        int manipulate(int a);
    }

}
