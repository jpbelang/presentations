package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created. There, you have it.
 */
public class Factories {

    public static List<Person> createSimplePeople(List<Supplier<Job>> jobs) {

        return Arrays.asList(
                new Person("Pierre", Arrays.asList(jobs.get(0).get(), jobs.get(1).get()), LocalDate.of(1968, 6, 12)),
                new Person("Jean", Arrays.asList(jobs.get(1).get(), jobs.get(2).get()), LocalDate.of(1967, 2, 3)),
                new Person("Jacques", Arrays.asList(jobs.get(2).get(), jobs.get(1).get()), LocalDate.of(1998, 6, 24))
                );
        }

    public static List<Supplier<Job>> createSimpleJobs() {

        return Arrays.asList(
                () -> new Job("QA", "Intact", 30000),
                () -> new Job("Dev niv. XXVI", "Google", 40000),
                () -> new Job("Integrator", "Amazon", 60000)
        );
    }

}
