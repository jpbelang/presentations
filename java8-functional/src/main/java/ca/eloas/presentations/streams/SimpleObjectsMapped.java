package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created. There, you have it.
 */
public class SimpleObjectsMapped {

    public static void main(String[] args) {

        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());
        Set<String> names = people.stream().map(Person::getName).collect(Collectors.toSet());

        Set<Job> possibleJobsForEveryone = people.stream().map(SimpleObjectsMapped::ideaJob).collect(Collectors.toSet());
    }

    public static Job ideaJob(Person p) {

        return null;
    }
}
