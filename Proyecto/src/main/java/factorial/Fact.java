package factorial;

public class Fact {
    
    public int compute(int n) {
        int result = -1;
        if (n==0||n==1) {
            result = 1;
        }
        else if (n==2) {
            result = 2;
        }
        return result;
    }
}