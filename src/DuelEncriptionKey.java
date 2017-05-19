/**
 * Created by stannis on 15/05/17.
 */
public class DuelEncriptionKey extends Key {
    public int key_1;
    public int key_2;
    public SingleEncryptionKey singleEncryptionKey_1;
    public SingleEncryptionKey singleEncryptionKey_2;

    public DuelEncriptionKey(int key_1, int key_2) {
        super();
        this.key_1 = key_1;
        this.key_2 = key_2;
        this.singleEncryptionKey_1 = new SingleEncryptionKey(key_1);
        this.singleEncryptionKey_2 = new SingleEncryptionKey(key_2);
    }
}
