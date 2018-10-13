package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created. There, you have it.
 */
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
