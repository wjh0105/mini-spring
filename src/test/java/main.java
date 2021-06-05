import java.lang.reflect.Proxy;

public class main {
    public static void main(String[] args) {
        Test t = new Test();
        handle h = new handle(t);
        Object o =Proxy.newProxyInstance(t.getClass().getClassLoader(),t.getClass().getInterfaces(),h);
//        t2.say();
        o.getClass();
    }
}
