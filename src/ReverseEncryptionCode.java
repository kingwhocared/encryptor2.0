/**
 * Created by stannis on 12/05/17.
 */
public class ReverseEncryptionCode implements Code {
    Code code;

    public ReverseEncryptionCode(Code code) {
        this.code = code;
    }

    public int Encode(int to_encode) {
        return code.Decode(to_encode);
    }

    public int Decode(int to_decode) {
        return code.Encode(to_decode);
    }



}
