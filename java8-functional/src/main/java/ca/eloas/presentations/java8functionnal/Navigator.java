package ca.eloas.presentations.java8functionnal;

import java.util.Set;

/**
 * Created. There, you have it.
 */
@FunctionalInterface
public interface Navigator {

    static Navigator createDefault() {

        return new NavigatorImpl();
    }

    String navigate(String...options);
    default String navigate(Set<String> options) {

        return navigate(options.toArray(new String[0]));
    }

}
