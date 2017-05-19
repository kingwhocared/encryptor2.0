/**
 * Created by stannis on 12/05/17.
 */
public class DoubleEncryptionCode implements Code {
    Code code1;
    Code code2;

    public DoubleEncryptionCode(Code code1, Code code2) {
        this.code1 = code1;
        this.code2 = code2;
    }

    @Override
    public int Encode(int to_encode) {
        return code2.Encode(code1.Encode(to_encode));
    }

    @Override
    public int Decode(int to_decode) {
        return code1.Decode(code2.Decode(to_decode));
    }

}
