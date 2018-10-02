package ca.eloas.presentations.spring;

import javax.inject.Inject;

/**
 * Created. There, you have it.
 */
public class GoodFields {

    final private UsedClassOne classOne;
    final private UsedClassTwo classTwo;

    @Inject
    public GoodFields(UsedClassOne classOne, UsedClassTwo classTwo) {
        this.classOne = classOne;
        this.classTwo = classTwo;
    }
}
