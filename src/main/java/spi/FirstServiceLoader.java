package spi;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Consumer;

/**
 * 测试SPI加载机制
 */
public class FirstServiceLoader<T> {
    private static ThreadLocal<ServiceLoader> threadLocal = new ThreadLocal();

    public static void main(String[] args) {
        new FirstServiceLoader().start();
    }

    private void start() {
        invokeBasic();
        ServiceLoader<T> loader = (ServiceLoader<T>) threadLocal.get();
        loader.reload();
        invokeComplexService();
    }

    public void invokeBasic() {
        loadService(IService.class, (service) -> {
            System.out.println(service.getServiceName());
            System.out.println(service.invoke());
        });
    }

    public void invokeComplexService() {
        loadService(IService.class, (service) -> {
            System.out.println(service.getServiceName());
            System.out.println(service.invoke());
        });
    }

    /**
     * 加载实现借口的类
     *
     * @param cls
     * @param invokeMethod
     * @param <T>
     */
    public <T> void loadService(Class<T> cls, Consumer<T> invokeMethod) {
        ServiceLoader<T> loader = (ServiceLoader<T>) threadLocal.get();
        if (loader == null) {
            loader = ServiceLoader.load(cls);
            threadLocal.set(loader);
        }

        Iterator<T> iter = loader.iterator();
        while (iter.hasNext()) {
            invokeMethod.accept(iter.next());
        }

    }
}
