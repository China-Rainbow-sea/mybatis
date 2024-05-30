package com.rainbowsea.javassist;


import com.rainbowsea.bank.dao.AccountDao;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.util.Arrays;

public class JavassistTest {

    /**
     * 根据dao接口生成dao接口的代理对象
     *
     * @param sqlSession   sql会话
     * @param daoInterface dao接口
     * @return dao接口代理对象
     */
    public static Object getMapper(SqlSession sqlSession, Class daoInterface) {
        ClassPool pool = ClassPool.getDefault();
        // 生成代理类
        CtClass ctClass = pool.makeClass(daoInterface.getPackageName() + ".impl." + daoInterface.getSimpleName() + "Impl");
        // 接口
        CtClass ctInterface = pool.makeClass(daoInterface.getName());
        // 代理类实现接口
        ctClass.addInterface(ctInterface);
        // 获取所有的方法
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // 拼接方法的签名
            StringBuilder methodStr = new StringBuilder();
            String returnTypeName = method.getReturnType().getName();
            methodStr.append(returnTypeName);
            methodStr.append(" ");
            String methodName = method.getName();
            methodStr.append(methodName);
            methodStr.append("(");
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                methodStr.append(parameterTypes[i].getName());
                methodStr.append(" arg");
                methodStr.append(i);
                if (i != parameterTypes.length - 1) {
                    methodStr.append(",");
                }
            }
            methodStr.append("){");
            // 方法体当中的代码怎么写？
            // 获取sqlId（这里非常重要：因为这行代码导致以后namespace必须是接口的全限定接口名，sqlId必须是接口中方法的方法名。）
            String sqlId = daoInterface.getName() + "." + methodName;
            // 获取SqlCommondType
            String sqlCommondTypeName = sqlSession.getConfiguration().getMappedStatement(sqlId).getSqlCommandType().name();
            if ("SELECT".equals(sqlCommondTypeName)) {
                methodStr.append("org.apache.ibatis.session.SqlSession sqlSession = com.powernode.bank.utils.SqlSessionUtil.openSession();");
                methodStr.append("Object obj = sqlSession.selectOne(\"" + sqlId + "\", arg0);");
                methodStr.append("return (" + returnTypeName + ")obj;");
            } else if ("UPDATE".equals(sqlCommondTypeName)) {
                methodStr.append("org.apache.ibatis.session.SqlSession sqlSession = com.powernode.bank.utils.SqlSessionUtil.openSession();");
                methodStr.append("int count = sqlSession.update(\"" + sqlId + "\", arg0);");
                methodStr.append("return count;");
            }
            methodStr.append("}");
            System.out.println(methodStr);
            try {
                // 创建CtMethod对象
                CtMethod ctMethod = CtMethod.make(methodStr.toString(), ctClass);
                ctMethod.setModifiers(Modifier.PUBLIC);
                // 将方法添加到类
                ctClass.addMethod(ctMethod);
            } catch (CannotCompileException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            // 创建代理对象
            Class<?> aClass = ctClass.toClass();
            Constructor<?> defaultCon = aClass.getDeclaredConstructor();
            Object o = defaultCon.newInstance();
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




    @Test
    public void testGenerateAccountDaoImpl() throws CannotCompileException, InstantiationException, IllegalAccessException {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();

        // 制造类
        CtClass ctClass = pool.makeClass("com.rainbowsea.bank.dao.AccountDaoImpl");

        // 制造接口
        CtClass ctInterface = pool.makeInterface("com.rainbowsea.bank.dao.AccountDao");

        // 实现接口
        ctClass.addInterface(ctInterface);

        // 实现接口中所有的方法
        // 获取接口中所有的方法
        Method[] methods = AccountDao.class.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // method 是接口中的抽象方法
            // 把 methode 抽象方法给实现了

            try {
                // public void delete();
                // public int update(String actno,Double blance){}
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public"); // 追加修饰符列表
                methodCode.append(method.getReturnType().getName()); // 追加返回值类型
                methodCode.append(" ");
                methodCode.append(method.getName());  // 追加方法名
                methodCode.append("(");

                // 拼接参数String actno,Double balance
                Class<?>[] parameterTypes = method.getParameterTypes();

                for(int i = 0; i <parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    methodCode.append(parameterType.getName());
                    methodCode.append(" ");
                    methodCode.append("age" + i);

                    if( i!= parameterTypes.length -1) { // 不是最后一个参数，参数之间需要用 , 隔离开来
                        methodCode.append(",");
                    }
                }
                methodCode.append("){System.out.println(111);}");
                // 动态的添加return语句
                String returnTypeSimpleName = method.getReturnType().getSimpleName();
                if("void".equals(returnTypeSimpleName)) {

                } else if("int".equals(returnTypeSimpleName)) {
                    methodCode.append("return 1");
                } else if("String".equals(returnTypeSimpleName)) {
                    methodCode.append("return \"hello\";");
                }

                methodCode.append("}");
                System.out.println(methodCode);

                CtMethod ctMethod = CtMethod.make(methodCode.toString(), ctClass);
                ctClass.addMethod(ctMethod);
            } catch (CannotCompileException e) {
                e.printStackTrace();
            }
        });

        // 在内存中生成 class，并且加载到JVM当中
        Class<?> clazz = ctClass.toClass();
        // 创建对象
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        // 调用方法
        accountDao.insert("aaa");
        accountDao.delete();
        accountDao.update("666", 1000.0);

    }


    @Test
    public void testGenerateImpl() throws CannotCompileException, InstantiationException, IllegalAccessException {
        // 获取类池
        ClassPool pool = ClassPool.getDefault();
        // 制造类
        CtClass ctClass = pool.makeClass("com.rainbowsea.bank.dao.AccountDao");
        // 制造接口
        CtClass ctInterface = pool.makeInterface("com.rianbowsea.bank.dao.AccountDaoImpl");
        // 添加接口到类中
        ctClass.addInterface(ctInterface);  // AccountDaoImpl implements AccountDao
        // 实现接口中的方法
        // 制造方法
        CtMethod ctMethod = CtMethod.make("public void delete() {System.out.println(\"hello delete!\");}", ctClass);
        // 将方法添加到类中
        ctClass.addMethod(ctMethod);
        // 在内存中生成类，同时将生成的类加载到JVM当中
        Class<?> clazz = ctClass.toClass();
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        accountDao.delete();


    }


    @Test
    public void testGenerateFirstClass() throws CannotCompileException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       // 获取类池，这个类池就是用来给生成class的
        ClassPool pool = ClassPool.getDefault();

        // 制造类（需要告诉javassist, 类名是啥）
        CtClass ctClass = pool.makeClass("com.rianbowsea.bank.dao.impl.AccountDaoImpl");

        // 制造方法
        String methodCode = "public void insert() {System.out.println(123);}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);

        // 将方法添加到类中
        ctClass.addMethod(ctMethod);

        // 在内存中生成 class
        ctClass.toClass();

        // 类加载到JVM当中，返回AccountDaoImpl类的字节码
        Class<?> clazz = Class.forName("com.rianbowsea.bank.dao.impl.AccountDaoImpl");
        // 创建对象
        Object obj = clazz.newInstance();
        // 获取 AccountDaoImp 中的 insert 方法
        Method insertMethod = clazz.getDeclaredMethod("insert");

        // 调用方法 insert
        insertMethod.invoke(obj);

    }
}
