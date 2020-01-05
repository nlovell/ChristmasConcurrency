package machine;
import java.util.Arrays;

public class Sack {

    final Present presents[];

    public Sack(int capacity) {
        this.presents = new Present[capacity];
    }

    void replaceSack() {
        Arrays.fill(presents, null);
    }

    boolean isSpace() {
        for (Present present : presents)
            if(present == null)
                return true;
        return false;
    }
}
