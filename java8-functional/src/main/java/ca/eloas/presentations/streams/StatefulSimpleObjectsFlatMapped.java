package ca.eloas.presentations.streams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created. There, you have it.
 */
public class StatefulSimpleObjectsFlatMapped {

    public static void main(String[] args) {

        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());
        List<String> employers = people.stream()
                .flatMap((p) -> p.getJobs().stream()).filter(findAllJobsByEmployers())
                .map(Job::getCompany)
                .collect(Collectors.toList());

        System.err.println(employers);
    }

    private static Predicate<? super Job> findAllJobsByEmployers() {
        Set<String> seenEmployers = new HashSet<>();
        return (j) -> seenEmployers.add(j.getTitle());
    }
}
