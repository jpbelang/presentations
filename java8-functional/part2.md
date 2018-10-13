# STREAMS!

## Jean-Philippe Bélanger 

---
# Pourquoi ?

Y'a plusieurs constructions en programmation procédurale que l'on prend pour acquis qui:

* sont en fait du code répété.

* augmentent la complexité cyclométrique.

* sont des foires à bug 

---
# Ouch
```java
public class ComplexLoop {
    public void complicated(TypeRegistry typeRegistry, Collection<RamlResourceMethod> methods, TypeSelector selector,
                            MethodBuilder methodBuilder) throws IOException {
        ResponseBuilder responseBuilder = null;
        for (RamlResourceMethod method : methods) {
            if (!method.getProducedType().isPresent()) {
                continue;
            }
            for (RamlMediaType producedMediaType : method.getProducedMediaTypes()) {
                if (responseBuilder == null) {
                    responseBuilder = ResponseBuilder.response(200);
                }
                BodyBuilder body = BodyBuilder.body(producedMediaType.toStringRepresentation());
                responseBuilder.withBodies(body);
                TypeHandler typeHandler = selector.pickTypeWriter(method, producedMediaType);
                TypeBuilder type = typeHandler.writeType(typeRegistry, method.getProducedType().get());
                body.ofType(type);
            }
        }
        if (responseBuilder != null) {
            methodBuilder.withResponses(responseBuilder);
        }
    }
}
```
---
# Les streams !

Les streams sont un nouveau concept en Java 8 qui permettent d'encapsuler certaines idées que l'on retrouve souvent 
en programmation: la sélection d'objets, les transformation, et le calcul sur les objets (filter/map/reduce).  
Ces idées sont encadrées dans un modèle:

* Où les données ne sont traversées qu'une seule fois.
* Techniquement infinies.
* Paresseux (lazy). 
* Séquentiel ou parallèle.

---
# Les streams !

Ils sont divisés en trois types d'opération.

* Les opération de création de stream.

Plusieurs structures permettent la creation de streams.

* Les opérations intermédiaires.

Ce sont des opérations qui continuent le stream, où l'on pourra leur appliquer d'autres operations intermédiaires 
ou une opération terminale.

* Les opérations terminales.

Ces opération terminent effectivement le stream.

---

# On créé un stream...

Il existe plusieurs sources pour les streams

- `Collection.stream()`, d'une collection. 
- `Arrays.stream(...)`, d'un tableau.
- `Streams.concat(Stream...)`, d'autres streams.
- `Random.ints()`, de générateurs d'entiers aléatoires.
- `Streams.zip()`, de Guava > 20
---
# Mais qu'est-ce que donc un stream ?

C'est une grande interface, de laquelle on peut tirer un itérateur (via `.iterator()`) qui implémente toutes les méthodes
de la grande famille des streams.  Toutes ces operations filtrent, transforment, ou réduisent le contenu du stream. 

Il est possible mais 
## très peu désirable
d'étendre cette interface.  Ce qui s'y trouve devrait être largement suffisant pour nos besoins. Sérieusement.
 
---
# Les opérations intermédiaires.

Les opérations intermédiaires sont des opérations qui retournent un stream.  Les principales sont:

* `.filter(Predicate)`
* `.map(Function)`
* `.flatMap(Funtion)`, la fonction retourne un stream.
* `.peek(Consumer)`
* `.distinct()`, avec `.equals()`
* `.sorted(Comparator)`
* `.limit(longueur)`
* `.skip(nombre)`

Certaines de ces opérations *doivent* maintenir un état et sont souvent plus coûteuses (`sort()`, `distinct()`).  C'est du devoir du programmeur de s'assurer
que l'ordre de l'utilisation est sage.

---
# Les opérations terminales.

Les opérations terminales sont des opérations qui terminent le stream (!).  Elles sont en fait le calculateur de résultat du stream. Les grandes opérations sont:

* `.collect(Collector)` et `.toArray()`
* `.forEach(Consumer)`
* `.findAny()` et `.findFirst()`
* `.min(Comparator)`, `.max(Comparator)`, `.count()`
* `.anyMatch(Predicate)`, `.allMatch(Predicate)`, `.noneMatch(Predicate)`

Toutes ces opérations sont en fait des helpers pour les opérations `.reduce()` de l'interface.

---
# Ok, on va tout coller ça ensemble.

```java
public class SimpleExamples {

    public static void main(String[] args) {

        // Reduction simple
        System.err.println(Stream.of(1,9,6,4,3,5).anyMatch((x) -> x > 6));
        System.err.println(Stream.of("one", "three", "two").noneMatch((s) -> s.length() > 3));

        // Transformation simple
        Stream.of("one", "three", "four").onClose(System.err::println)
                .map(String::length)
                .map( s -> s  + " " )
                .forEach(System.err::print);
    }
}
```
---
# Mais avec des objets, genre le DOM
```java
public class SimpleObjects {

    public static void main(String[] args) {

        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());
        people.stream().filter(p -> p.getBirthDay().isBefore(LocalDate.ofYearDay(1970, 1))).forEach( p -> System.err.println(p.getName()));
        // ou bien
        people.stream().filter(p -> isBirthdayBefore(p, LocalDate.ofYearDay(1970, 1))).forEach( p -> System.err.println(p.getName()));
        // ou mieux
        people.stream().filter(isBirthdayBefore(LocalDate.ofYearDay(1970, 1))).forEach( p -> System.err.println(p.getName()));
    }
    
    public static boolean isBirthdayBefore(Person p, LocalDate localDate) {
        
        return p.getBirthDay().isBefore(localDate);
    }

    public static Predicate<Person> isBirthdayBefore(LocalDate localDate) {

        return (p) -> p.getBirthDay().isBefore(localDate);
    }

}
```
---
# On peut "imbriquer" ces boucles...
```java
public class SimpleObjectsEmbedded {

    public static void main(String[] args) {

        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());
        people.stream()
                .filter(SimpleObjectsEmbedded::wasEverAmazonEmployee)
                .forEach( p -> System.err.println(p.getName()));
    }

    private static boolean wasEverAmazonEmployee(Person p) {
        return p.getJobs().stream().anyMatch(j -> j.getCompany().equals("Amazon"));
    }
}
```
---
# Les Collectors (les résultats organisés)

On peut collecter un stream dans plusieurs types de contenants.

```java
public class SimpleCollectors {

    public static void main(String[] args) {
        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());

        List<Person> peopleWithJ = people.stream().filter(p -> p.getName().startsWith("J")).collect(Collectors.toList());
        System.err.println(peopleWithJ);
        // ou
        List<Person> existingCollection = new ArrayList<>();
        existingCollection.add(people.get(2));
        List<Person> addToPeopleWithJ = people.stream().filter(p -> p.getName().startsWith("J")).collect(Collectors.toCollection(() -> existingCollection));
        System.err.println(addToPeopleWithJ);

        // Map
        Map<String, Person> map = people.stream()
                .filter(p -> p.getName().startsWith("J"))
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.err.println(map);
    }
}
```
---
# Mapping de données (la transformation)

Il est possible de transformer les données dans un stream.

* `.map(Function<? super T, ? extends R> mapper)`:  cette méthode transforme un objet en un autre objet en passant par la fonction.
* `.flatMap(Function<? super T, ? extends Stream<? extends R,T>> mapper)`:  cette méthode transforme une liste d'objets en relation avec les objets
du stream en un autre stream d'objets en passant par la fonction.
---
# Map

