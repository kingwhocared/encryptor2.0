/**
 * Created by stannis on 12/05/17.
 */
public class DoubleFileManipulator extends FileManipulator {
    Code code1;
    Code code2;

    public DoubleFileManipulator(Code code1, Code code2) {
        this.code1 = code1;
        this.code2 = code2;
    }

    @Override
    protected int EncodeByte(int to_encode) {
        return code2.Encode(code1.Encode(to_encode));
    }

    @Override
    protected int DecodeByte(int to_decode) {
        return code1.Decode(code2.Decode(to_decode));
    }
}
