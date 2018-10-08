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