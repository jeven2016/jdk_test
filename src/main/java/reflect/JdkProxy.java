package reflect;


import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxy {

  @Test
  public void jdkTest() {
    NewPerson hello = new NewPerson();
    NewPerson newPerson = (NewPerson)( Proxy
        .newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(),
            new NewPersonInvocation(hello)));

    newPerson.say();
  }

}

interface Hello {

  String say();
}

class NewPerson implements Hello {

  @Override
  public String say() {
    return "say something";
  }
}

class NewPersonInvocation implements InvocationHandler {

  Object target;

  public NewPersonInvocation(Object object) {
    this.target = object;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("begin proxy...");

    Object result = method.invoke(target, args);
    System.out.println("end proxy...");

    return result;
  }
}