package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created. There, you have it.
 */
public class Factories {

    public static List<Person> createSimplePeople(List<Job> jobs) {

        return Arrays.asList(
                new Person("Pierre", Arrays.asList(jobs.get(0), jobs.get(1)), LocalDate.of(1968, 6, 12)),
                new Person("Jean", Arrays.asList(jobs.get(1), jobs.get(2)), LocalDate.of(1967, 2, 3)),
                new Person("Jacques", Arrays.asList(jobs.get(2), jobs.get(1)), LocalDate.of(1998, 6, 24))
                );
        }

    public static List<Job> createSimpleJobs() {

        return Arrays.asList(
                new Job("QA", "Intact"),
                new Job("Dev niv. XXVI", "Google"),
                new Job("Integrator", "Amazon")
        );
    }

}
