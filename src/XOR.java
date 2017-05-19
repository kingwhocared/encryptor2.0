/**
 * Created by stannis on 10/05/17.
 */
public class XOR implements Code{
    int key;
    public XOR(Key key) {
        this.key = ((SingleEncryptionKey) key).key;
    }

    @Override
    public int Encode(int uncoded) {
        return ((uncoded ^ key)& 0xFF);
    }

    @Override
    public int Decode(int coded) {
        return ((coded ^ key)& 0xFF);
    }
}
