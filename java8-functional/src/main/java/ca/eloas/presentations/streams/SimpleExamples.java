package ca.eloas.presentations.streams;

import java.util.stream.Stream;

/**
 * Created. There, you have it.
 */
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
