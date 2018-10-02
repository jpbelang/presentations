# Dependency injection

Dependency injection is the idea that the user of a class should push in dependencies, instead of having that class 
either build it's own dependencies (through a `new` or other static method) or go fetch them through a mechanism (through some sort of `Retriever`).

This allows you to bend the class to do your bidding within its interface by injecting mocks or such.
Note here that there is absolutely no imperative to use a DI framework such as Spring or Guice.  Large applications can be built with handmade
injection.
---
# How should I inject ?

For class member injection:
* It should be done at construction, in the constructor.
* It should be done in a final field.
* There should be no logic in the constructor.

---
# How should I inject ?

If you find yourself trying to use logic in a constructor, extract that logic to s static factory method so that the constrstuctor itself
has no logic.  This will allow a test to use the constructor in a flexible and intuitive way (principle of least astonishment).

```java
public class LogicInTheConstructor {

    public interface Factory {
        String createString(String someParameter);
    }
    

    private boolean flag;
    public LogicInTheConstructor(Factory fac, String param) {
        
        String point = fac.createString(param);
        if ( "bad".equals(point) ) {
            this.flag = false;
        } else {
            this.flag = true;
        }
    }
}
```

---
# How should I inject ?

```java
public class LogicGone {
    public interface Factory {
        String createString(String someParameter);
    }

    public static LogicGone create(LogicInTheConstructor.Factory fac, String param) {
        String point = fac.createString(param);
        if ( "bad".equals(point) ) {
            return new LogicGone(false);
        } else {
            return new LogicGone(true);
        }
    } 

    final private boolean flag;
    public LogicGone (boolean flag) {

        this.flag = flag;
    }
}
```
This means that someone who needs to inject `LogicGone` no longer needs the factory nor the parameter.

# How should I inject ?

Sometimes, injection is done through a correctly defined interface:  methods take parameters, and method parameters are perfectly well suited
injection points.  Supposing the perfectly reasonable `Task` class
:
```java
public class Task {

    final private Executor serverExecutor;
    final private Date jobStartTime;

    public Task (Executor serverExecutor, Date jobStartTime) {
        this.serverExecutor = serverExecutor;
        this.jobStartTime = jobStartTime;
    }

    public void executeTask() {
        
        serverExecutor.execute(() -> {
            if ( System.currentTimeMillis() - jobStartTime.getTime() < 10000) {
                
                // we are not too late, we run....
            }
        });
    }
    
}
```
# How should I inject ?
But maybe, you could be doing this:
```java
public class AnotherTask {

    final private Executor serverExecutor;

    public AnotherTask (Executor serverExecutor) {
        this.serverExecutor = serverExecutor;
    }

    public void executeTask(Date jobStartTime) {
        
        serverExecutor.execute(() -> {
            if ( System.currentTimeMillis() - jobStartTime.getTime() < 10000) {
                
                // we are not too late, we run....
            }
        });
    }
    
}
```
If the executor is a singleton, then this task is a better fit:  you don't have to recreate `AnotherTask` for every execution.  
It also becomes a singleton.

# Spring misuse

Dependency injection frameworks were developed to help developers inject dependencies.  Unfortunately for testing they often:

* It becomes pervasive.

Across the whole project, you can wind up having references to either Spring or injection annotations, which means 
that your code can only be used with the DI Framework. 

* Slow down testing by introducing operations that are slow.

Either through introspection, classpath scanning or XML parsing (which we _all_ use).

* Introduce magic into a system.

By looking at the test, it is unclear what you are testing:  injected dependencies are not clear and often pull in surprising
dependencies.

---
# Spring misuse

Furthermore, the commonly used field injection is problematic.

* Field injection forces us to use spring in testing.

* Field injection is the easiest to misuse.

* The Spring developers recommend using constructor injection  Shouldn't we listen to them ? 

* XML configuration is not easily testable (and is not refactorable)
---
# Fixing spring

We should:

* Remove annotations on fields a promote constructor injection.

* Turn XML configuration into Java configuration.

Ideally, *none* of your production code should show the existence of a DI Framework.

---
# Eliminating annotations of fields
```java
public class BadFields {

    @Inject
    private UsedClassOne classOne;

    @Inject
    private UsedClassTwo classTwo;
}
```
```java
public class GoodFields {

    final private UsedClassOne classOne;
    final private UsedClassTwo classTwo;

    @Inject
    public GoodFields(UsedClassOne classOne, UsedClassTwo classTwo) {
        this.classOne = classOne;
        this.classTwo = classTwo;
    }
}
```
---
# Eliminating XML
```xml
<beans xmlns = "http://www.springframework.org/schema/beans"
   xmlns:xsi = "http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation = "http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

   <bean id = "one" class = "ca.eloas.presentations.spring.UsedClassOne">
      <property name = "message" value = "Hello World!"/>
   </bean>
   <bean id = "two" class = "ca.eloas.presentations.spring.UsedClassTwo">
      <property name = "message" value = "Hello World!"/>
   </bean>

   <bean id = "helloWorld" class = "ca.eloas.presentations.spring.GoodFields">
      <constructor-arg ref="one"/>
      <constructor-arg ref="two"/>
   </bean>
</beans>
```

This is hard to test: you need to load an application context (and at Intact, you may have to load a bunch of XML
files to get your test to work).  Any change to the code (even refactoring) will not be reflected in the XML. Furthermore
You don't "inject" anything by using this:  you get a prefabricated object.
---
# Eliminating XML
```java
@Configuration
public class MyConfiguration {

    @Bean
    EvenGooderFields createFields() {
        
        return new EvenGooderFields(new UsedClassOne(), new UsedClassTwo());
    }
    
    // or
    @Bean @Singleton
    EvenGooderFields createFields(UsedClassOne one, UsedClassTwo two) {
        
        return new EvenGooderFields(one, two);
    }
}
```
This can be picked up by the _same_ mechanisms as XML files, with some added benifits:  
* They survive refactoring.
* They use programming:  no extra knowledge needed.
* All of the Spring stuff in localized in one file.  Nothing to add in the bean files.
* It's testable !







