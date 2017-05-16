/**
 * Created by stannis on 12/05/17.
 */
public class SplitFileManipulator {
    Code code1;
    Code code2;
    int i;

    public SplitFileManipulator(Code code1, Code code2) {
        this.code1 = code1;
        this.code2 = code2;
        this.i = 0;
    }

    protected int EncodeByte(int to_encode) {
        i ^= 1;
        switch (this.i) {
            case 0:
                return code1.Encode(to_encode);
            case 1:
                return  code2.Encode(to_encode);
        }
        throw new RuntimeException("This should not happen");
    }

    protected int DecodeByte(int to_decode) {
        i ^= 1;
        switch (this.i) {
            case 0:
                return code1.Decode(to_decode);
            case 1:
                return  code2.Decode(to_decode);
        }
        throw new RuntimeException("This should not happen");
    }
}
