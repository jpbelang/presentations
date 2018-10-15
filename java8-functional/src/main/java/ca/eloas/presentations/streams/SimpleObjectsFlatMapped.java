package ca.eloas.presentations.streams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created. There, you have it.
 */
public class SimpleObjectsFlatMapped {

    public static void main(String[] args) {

        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());
        List<Job> jobs = people.stream().flatMap((p) -> p.getJobs().stream()).filter((j) -> j.getSalary() >= 40000).collect(Collectors.toList());

        System.err.println(jobs);
    }
}
