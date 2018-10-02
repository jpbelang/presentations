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

   <bean id = "helloWorld" class = "ca.eloas.presentations.spring.GoodFields">
      <property name = "message" value = "Hello World!"/>
   </bean>

</beans>
```







