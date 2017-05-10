/**
 * Created by stannis on 09/05/17.
 */
public class RegularManipulator extends FileManipulator {
    Code code;
    public RegularManipulator(Code code, View.UserRequest request) {
        this.code = code;
        this.request = request;
    }

    @Override
    int EncodeByte(int to_encode) {
        return code.Encode(to_encode);
    }

    @Override
    int DecodeByte(int to_decode) {
        return code.Decode(to_decode);
    }
}
