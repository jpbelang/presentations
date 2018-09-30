# Programmation fonctionelle

## Jean-Philippe Bélanger 

---
# Pourquoi ?

* De bonnes idées suivent la programmation fonctionelle.

Les fonctions pures, la transparence référentielle, les fonctions d'ordre supérieur.

L'idée des fonctions pures et la transparence référentielle.

* Concision.

Une des métriques importantes pour prédire le nombre de bugs dans du code est 
le nombre de lignes de code. 

* Pression linguistique.

Plusieurs langages supportent ces concepts:  Scala,  C#, Groovy....

---
# Fonction pure

C'est une fonction dont le résultat qui ne dépend que de ses entrées.

```java
public int addOne(int x) {
    return x + 1;
}
```
---
# Transparence référentielle

```java
class  UneClasse {
 int globalValue = 0;

 int addOpaque(int x) {
   globalValue++;
   return x + globalValue;
 }

 int addTransparent(int x) {
   return x + 1;
 }
}

```
---
#  Les fonctions d'ordre supérieur

Ce sont des fonctions qui opèrent sur des fonctions.
```javascript
function repeat(n, action) {
  for (let i = 0; i < n; i++) {
    action(i);
  }
}

repeat(3, console.log);
// → 0
// → 1
// → 2
```
---
# La concision
On passe de:
```java
for(User u: list) {
    if ( User.isRealUser(u) ) {
        return true;
    }
}

return false;
```
à:

```java
boolean isReal = list.stream().anyMatch(u -> User.isRealUser(u)); 
```

ou, encore plus:
```java
boolean isReal = list.stream().anyMatch(User::isRealUser); 
```
---
# x = x + 1

```java
class Person {

    private Date birthDay;

    public Date getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
    public static void someFunction(Person p) {
        
        assertTrue (p.getBirthDay() == p.getBirthDay());
    }
}
```
 
---
#  Donc comment ça marche en Java ?

* On définit un "nouveau" type d'interface java:  l'interface fonctionelle (`@FunctionalInterface`).

* On définit une nouvelle syntaxe pour faire ce que l'on appelle des expressions lambda.

* On définit un nouveau concept, les `streams`.
  
---
# Les interfaces en Java 8, une parenthèse

Plusieurs améliorations ont été ajoutées aux interfaces.

* Les interfaces java supportent maintenant des méthodes statiques.

* Les interfaces java supportent maintenant des méthodes avec une implementation (`default`).

* Les interfaces qui n'ont qu'une méthode sont propices à êtres annotées avec `@FunctionalInterface`  
---
# Petit exemple
```java
@FunctionalInterface
public interface Navigator {

    static Navigator createDefault() {

        return new NavigatorImpl();
    }

    String navigate(String...options);
    default String navigate(Set<String> options) {
        
        return navigate(options.toArray(new String[0]));
    }
}
```
---
# Évitons les classes statiques!
Au lieu d'écrire:
```java
public ContractUtils {
    public static void cleanContract(IContract contract) { /* ... */}
}
```
Écrire plutôt:
```java
public interface ContractUtilsTrait {
    
    default void cleanContract(IContract contract) { /* ... */}
}
```

On utilise ensuite ainsi:
```java
public class ContractService implements ContractUtils {
    
    public void useIt() {
        cleanContract(this.contract);
    }
}
``` 
---
# Fin de la parenthèse

Pour les gens qui souffrent d'un TOC.

---
# Avant les expressions lambda

Avant, Java avait plusieurs interfaces très simples qui permettaient de faire des choses simples. Par exemple:

* `Runnable` et `Callable`:  fait quelque chose.
* `Predicate`: de Guava:  est-ce que quelque chose est vrai ?

Malheureusement, leur utilisation était un peu trop verbose.
```java
public class NonFunctionalRunnable {
    public void executeFrom(Executor executor) {

        final int actionId = new Random().nextInt();
        Runnable r = new Runnable () {
            public void run() {
                switch (actionId) {
                    /* ... */
                }
            }
        };
        executor.execute(r);
    }
}
``` 

---
# Et maintenant...

Les expressions lambda sont un nouvel élément syntaxique de Java 8: les fonctions en tant qu'objets de première classe
(les fonctions en tant qu'objets).  Cette idée ne s'applique qu'aux interfaces qui n'ont qu'une méthode abstraite.  Dans
sa plus simple expression, ça donne.
```java
public class LambdaRunnable {
    
    public void executeFrom(Executor executor) {

        int actionId = new Random().nextInt();

        Runnable r = () -> {
            switch (actionId) {
                /* ... */
            }
        };
        executor.execute(r);
    }
}
``` 

Ce qui est a droite du `()->` deviendra l'implémentation de la méthode manquante, effectivement.

---
# L'utilisation de l'interface ne change pas.
```java
@FunctionalInterface
interface Navigator {

    String navigate(String... directions);
}

public class Ship {
    
    public void navigateWith(Navigator n) {
        
        String result = n.navigate("east", "west", "north");        
    }
}

class Sea {
    public void doNavigation(Ship ship) {
        
        Navigator navigator = (directions) ->  {

            return directions[0];
        };
        ship.navigateWith(navigator);
    }
}
```
---
# Les références de méthodes

Supposons l'existance d'une méthode, on pourrait platement lui déléguer l'appel.
 
```java
class Sea {
    public void doNavigation(Ship ship) {
        Navigator navigator = (directions) ->  {

            return iWouldLikeToPickADirection(directions);
        };
        // ou bien, pour simplifier...
        navigator = (directions) ->  iWouldLikeToPickADirection(directions);
        ship.navigateWith(navigator);
    }

    public String iWouldLikeToPickADirection(String...directions) {
        return directions[0];
    }
}
@FunctionalInterface
interface Navigator {
    String navigate(String... directions);
}
public class Ship {    
    public void navigateWith(Navigator n) {
        
        String result = n.navigate("east", "west", "north");        
    }
}
```
---
# Les réferences de méthode

```java
class Sea {
    public void doNavigation(Ship ship) {
        Navigator navigator = this::iWouldLikeToPickADirection;
        ship.navigateWith(navigator);
    }
    
    public String iWouldLikeToPickADirection(String...directions) {
        /* pick one  */
        return directions[0];
    }
}
@FunctionalInterface
interface Navigator {
    String navigate(String... directions);
}
public class Ship {    
    public void navigateWith(Navigator n) {
        
        String result = n.navigate("east", "west", "north");        
    }
}
```
---
# On a maintenant plein d'interfaces fonctionnelles pré-définies en java

* `Predicate<T>`
* `Supplier<T>`
* `Consumer<T>`
* `Function<T>`
* `Callable<T>`

Et leur variantes Bi....

---

# Une application rapide des idées:  Optional

`Optional`, une nouvelle classe de Java 8, aide la gestion des pointeurs nuls.  On l'utilise dans les signatures des 
méthodes ou une valeur inexistante peut être retournée.

```java
public Optional<String> getLanguage() { /* ... */}

Optional<String> optLang = x.getLanguage();
if ( optLang.isPresent() ) {}
```

C'est assez banal.

---
# Allons plus loin...
```java
public Optional<String> getLanguage() { /* ... */}

String lang = x.getLanguage().orElse("FR"); // ou bien
String lang = x.getLanguage().orElseGet(() -> fetchDefaultLanguage());

```
`Optional` pose la question au développeur-utilisateur: "Et si la variable n'était pas là ?", et ça
laisse le développeur de librairie retourner des "nulls' avec `Optional.empty()` ou bien `Optional.ofNullable()`.
---
# Dernier exemple

On veut copier les champs non-nulls d'un bean à un autre:
```java
bean.getMiddleName().ifPresent((nonnull) -> secondBean.setMiddleName(nonnull)); // ou bien
bean.getMiddleName().ifPresent(secondBean::setMiddleName);
```



