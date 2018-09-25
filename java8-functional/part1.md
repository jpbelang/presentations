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

*  On définit un "nouveau" type d'interface java:  l'interface fonctionelle (`@FunctionalInterface`)