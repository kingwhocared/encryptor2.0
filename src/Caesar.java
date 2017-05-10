/**
 * Created by stannis on 09/05/17.
 */
public class Caesar implements Code{

    public int key;
    public Caesar(int key) {
        this.key = key;
    }

    public int Encode(int uncoded) {
        return ((uncoded + key)& 0xFF);
    }

    public int Decode(int coded) {
        return ((1 << 8) + coded - key) & 0xFF;
    }
}
