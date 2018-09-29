package ca.eloas.presentations.java8functionnal;

/**
 * Created. There, you have it.
 */
public class ReferredSea {

    public void doNavigation(Ship ship) {
        Navigator navigator = this::iWouldLikeToPickADirection;
        ship.navigateWith(navigator);
    }

    public String iWouldLikeToPickADirection(String...directions) {
        /* pick one  */
        return directions[0];
    }

}
