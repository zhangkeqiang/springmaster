package com.agilejerry.springmaster;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * Hello world!
 *
 */


public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ApplicationContext context = new ClassPathXmlApplicationContext(   
                "applicationContext.xml"); 
        Greeter greeter = new Greeter();
        System.out.println(greeter.sayHello());
    }
}
