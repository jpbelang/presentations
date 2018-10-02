# Spring misuse

Dependency injection frameworks were developed to help developers inject dependencies.  Unfortunately for testing they often:

* Slow down testing by introducing operations that are slow.

Either through introspection, classpath scanning or XML parsing.

* Introduce magic into a system.

By looking at the test, it is unclear what you are testing:  injected dependencies are not clear and often pull in surprising
dependencies.
---
# Spring misuse

Furthermore, the commonly used field injection is problematic.

* Field injection forces us to use spring in testing.

* Field injection is the easiest to misuse

* The Spring developers recommend using constructor injection  Shouldn't we listen to them ? 
---
# Fixing spring

We should:

* Remove






