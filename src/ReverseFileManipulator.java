/**
 * Created by stannis on 12/05/17.
 */
public class ReverseFileManipulator extends FileManipulator {
    Code code;
    public ReverseFileManipulator(Code code, View.MainUserRequest request) {
        this.code = code;
        this.request = request;
    }

    @Override
    protected int EncodeByte(int to_encode) {
        return code.Decode(to_encode);
    }

    @Override
    protected int DecodeByte(int to_decode) {
        return code.Encode(to_decode);
    }
}
