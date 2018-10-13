package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created. There, you have it.
 */
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

        Map<String, Person> map = people.stream()
                .filter(p -> p.getName().startsWith("J"))
                .collect(Collectors.toMap(Person::getName, p -> p));
        System.err.println(map);
    }
}
