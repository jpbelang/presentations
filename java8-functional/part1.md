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
interface Navigator {

    static Navigator createDefault() {

        return NavigatorImpl();
    }

    default Location navigate(String...options) {

        navigate(new HashSet<>(Arrays.asList(options)));
    }

    Location navigate(Set<String> options);
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
public void executeFrom(Executor executor) {
    
    final int actionId;
    
    Runnable r = new Runnable () {
            public void run() {
                switch( actionId )
                /* ... */
            }
        };
    executor.submit();
}
``` 

---
# Et maintenant...

Les expressions lambda sont un nouvel élément syntaxique de Java 8: les fonctions en tant qu'objets de première classe
(les fonctions en tant qu'objets).  Cette idée ne s'applique qu'aux interfaces qui n'ont qu'une methode abstraite.  Dans
sa plus simple expression, ça donne.
```java
public void executeFrom(Executor executor) {
    
    int actionId;
    Runnable r = () ->  {
                     switch( actionId )
                     /* ... */
             };
    executor.submit(r);
}
``` 

Ce qui est a droite du `()->` deviendra l'implémentation de la méthode manquante, effectivement.

---
# L'utilisation de l'interface ne change pas.
```java
@FunctionalInterface
interface Navigator {

    Location navigate(String... directions);
}

public class Ship {
    
    public void navigateWith(Navigator n) {
        
        String result = n.navigate("east", "west", "north");
        
    }
}

class Sea {
    public void doNavigation(Ship ship) {
        
        Navigator navigator = (directions) ->  {
                         /* pick one  */
                         return one;
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
                         /* pick one  */
                         return iWouldLikeToPickADirection(directions);
        };
        // ou bien, pour simplifier...
       navigator = (directions) ->  iWouldLikeToPickADirection(directions);
       ship.navigateWith(navigator);
    }    
    public String iWouldLikeToPickADirection(String...directions) {
         /* pick one  */
         return one;        
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
# Le réferences de méthode

```java
class Sea {
    public String doNavigation(Ship ship) {
        Navigator navigator = this::iWouldLikeToPickADirection;
        return ship.navigateWith(navigator);
    }    
    public String iWouldLikeToPickADirection(String...directions) {
         /* pick one  */
         return one;        
    }
}
@FunctionalInterface
interface Navigator {
    Location navigate(String... directions);
}
public class Ship {    
    public void navigateWith(Navigator n) {
        
        String result = n.navigate("east", "west", "north");        
    }
}

```




