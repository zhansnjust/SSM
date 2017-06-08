/**
 * Created by zhans-pc on 2017/5/5.
 */
public class Tests {
    public static void main(String[] args) {
        int k=1;
        g();
        k++;
        System.out.println("haha");
    }
    static void g(){
        int x=1;
        for (int i = 0; i <10 ; i++) {
            x+=i;
        }
        System.out.println(x);
    }
}
