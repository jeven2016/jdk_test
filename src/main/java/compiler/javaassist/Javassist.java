package compiler.javaassist;

import javassist.*;

/**
 * javassist 代理测试
 */
public class Javassist {
  public static void main(String[] args) {
    ClassPool classPool = ClassPool.getDefault();
    CtClass objCls = classPool.makeClass("compiler.Person");

    try {
      /**
       * Name
       */
      CtField field = new CtField(classPool.get("java.lang.String"), "name", objCls);
      field.setModifiers(Modifier.PRIVATE);

      //设置set,get方法
      objCls.addMethod(CtNewMethod.setter("setName", field));
      objCls.addMethod(CtNewMethod.getter("getName", field));
      objCls.addField(field, CtField.Initializer.constant("default"));

      /**
       * age
       */
      field = new CtField((classPool.get("java.lang.Integer")), "age", objCls);
      objCls.addMethod(CtNewMethod.getter("getAge", field));
      objCls.addMethod(CtNewMethod.setter("setAge", field));
      objCls.addField(field, CtField.Initializer.constant("default"));

//      objCls.getAnnotations()


      /**
       * map
       */

    } catch (CannotCompileException e) {
      e.printStackTrace();
    } catch (NotFoundException e) {
      e.printStackTrace();
    }

  }
}
