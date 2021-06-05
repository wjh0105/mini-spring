import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class handle implements InvocationHandler{
    private Object o;
    public handle(Object o)
    {
        this.o = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("1");
      o =  method.invoke(o,args);
        System.out.println("2");
        return o;
    }
}
