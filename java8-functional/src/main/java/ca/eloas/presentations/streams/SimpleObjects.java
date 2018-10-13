package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created. There, you have it.
 */
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
