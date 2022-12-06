package com.example.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController implements ApplicationContextAware{
    private static String javaSrc = "package com.example.demo;\n" +
            "\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "\n" +
            "public class TestClass extends com.example.demo.TestService {\n" +
            "\n" +
            "    @Autowired\n" +
            "    protected TestDao dao;\n" +
            "\n" +
            "    @Override\n" +
            "    public String sayHello(String msg) {\n" +
            "        return \"我查到了数据,test\" + dao.query(msg);\n" +
            "    }\n" +
            "\n" +
            "}\n";
    private static String javaSrc2 = "package com.example.demo;\n" +
            "\n" +
            "import org.springframework.beans.factory.annotation.Autowired;\n" +
            "\n" +
            "public class TestClass2 extends com.example.demo.TestService {\n" +
            "\n" +
            "    @Autowired\n" +
            "    protected TestDao dao;\n" +
            "\n" +
            "    @Override\n" +
            "    public String sayHello(String msg) {\n" +
            "        return \"我查到了数据,test2\" + dao.query(msg);\n" +
            "    }\n" +
            "\n" +
            "}\n";

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 测试接口，实际上就是完成动态编译java源码、加载字节码变成Class，装载Class到spring容器，
     * 获取对象，调用对象的测试
     * @return
     * @throws Exception
     */
    @RequestMapping("/createtest")
    @ResponseBody
    public String createtest() throws Exception {
        /**
         * 美滋滋的注册源码到spring容器得到一个对象
         * ApplicationUtil.register(applicationContext, javaSrc);
         */
        ApplicationUtil.register(applicationContext,"testClass", javaSrc);
        /**
         * 从spring上下文中拿到指定beanName的对象
         * 也可以 TestService testService = ApplicationUtil.getBean(applicationContext,TestService.class);
         */
       TestService testService = ApplicationUtil.getBean(applicationContext,"testClass");

        /**
         * 直接调用
         */
        return testService.sayHello("testClass");
    }


    @RequestMapping("/createtest2")
    @ResponseBody
    public String createtest2() throws Exception {
        /**
         * 美滋滋的注册源码到spring容器得到一个对象
         * ApplicationUtil.register(applicationContext, javaSrc);
         */
        ApplicationUtil.register(applicationContext,"test2Class", javaSrc2);
        /**
         * 从spring上下文中拿到指定beanName的对象
         * 也可以 TestService testService = ApplicationUtil.getBean(applicationContext,TestService.class);
         */
        TestService testService = ApplicationUtil.getBean(applicationContext,"test2Class");

        /**
         * 直接调用
         */
        return testService.sayHello("test2Class");
    }
    @RequestMapping("/test")
    @ResponseBody
    public String test() throws Exception {

        TestService testService = ApplicationUtil.getBean(applicationContext,"testClass");

        /**
         * 直接调用
         */
        return testService.sayHello("testClass");
    }
    @RequestMapping("/test2")
    @ResponseBody
    public String test2() throws Exception {

        TestService testService = ApplicationUtil.getBean(applicationContext,"test2Class");

        /**
         * 直接调用
         */
        return testService.sayHello("test2Class");
    }
}
