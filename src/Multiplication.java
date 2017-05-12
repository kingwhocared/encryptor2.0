/**
 * Created by stannis on 10/05/17.
 */
public class Multiplication implements Code {
    int key;
    public Multiplication(int key) {
        if (key % 2 == 0)
            throw new RuntimeException("Bad key for multiplication code");
        this.key = key;
    }

    public int Encode(int uncoded) {
        return ((uncoded + key)& 0xFF);
    }

    public int Decode(int coded) {
        return ((1 << 8) + coded - key) & 0xFF;
    }
}
