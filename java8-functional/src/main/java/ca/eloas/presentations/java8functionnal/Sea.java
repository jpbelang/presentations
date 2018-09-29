package ca.eloas.presentations.java8functionnal;

/**
 * Created. There, you have it.
 */
public class Sea {

    public void doNavigation(Ship ship) {

        Navigator navigator = (directions) ->  {
            /* pick one  */
            return directions[0];
        };

        ship.navigateWith(navigator);
    }

}
