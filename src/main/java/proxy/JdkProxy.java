package proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Greeting {
  String say(String msg);
}

class GreetingImpl implements Greeting {

  @Override
  public String say(String msg) {
    return "hello";
  }
}

class GreetingInvocationHandler implements InvocationHandler {
  private Object subject;


  public GreetingInvocationHandler(Object subject) {
    this.subject = subject;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("Will proxy this object >>>>>");

    var result = method.invoke(subject, args);
    System.out.println("you set hello~~~");
    System.out.println("finished >>>>>");
    return result;
  }
}

public class JdkProxy {
  public static void main(String[] args) {
    var greeting = new GreetingImpl();

    var invocationHandler = new GreetingInvocationHandler(greeting);
    Greeting newGreeting = (Greeting) Proxy.newProxyInstance(JdkProxy.class.getClassLoader(),
        greeting.getClass().getInterfaces(), invocationHandler);

    newGreeting.say("hello");

  }
}
