package ca.eloas.presentations.streams;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created. There, you have it.
 */
public class Exceptions {

    public static void main(String[] args) {

        List<Person> people = Factories.createSimplePeople(Factories.createSimpleJobs());
        Set<Job> possibleJobsForEveryoneSimple = people.stream().map(Exceptions::ideaJobHandlesOwnExeption).collect(Collectors.toSet());
        Set<Job> possibleJobsForEveryone = people.stream().map(unexceptional(Exceptions::ideaJob)).collect(Collectors.toSet());
    }
    public static Job ideaJobHandlesOwnExeption(Person p) {

        try {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Job ideaJob(Person p) throws Exception {

        return null;
    }
    @FunctionalInterface
    public interface ExceptionalFunction<T,R> {

        R apply(T t) throws Exception;
    }
    public static <R,T> Function<T,R> unexceptional(ExceptionalFunction<T,R> target) {

        return (t) -> {
            try {
                return target.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }


}
