package ca.eloas.presentations.java8functionnal;

/**
 * Created. There, you have it.
 */
public class Sea {

    public void doNavigation(Ship ship) {

        Navigator navigator = (directions) ->  {

            return directions[0];
        };
        // ou bien, pour simplifier...
        navigator = (directions) ->  directions[0];
        ship.navigateWith(navigator);
    }
}
