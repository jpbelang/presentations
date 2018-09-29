package ca.eloas.presentations.java8functionnal;

/**
 * Created. There, you have it.
 */
public class DelegatingSea {

    public void doNavigation(Ship ship) {
        Navigator navigator = (directions) ->  {

            return iWouldLikeToPickADirection(directions);
        };
        // ou bien, pour simplifier...
        navigator = (directions) ->  iWouldLikeToPickADirection(directions);
        ship.navigateWith(navigator);
    }

    public String iWouldLikeToPickADirection(String...directions) {
        /* pick one  */
        return directions[0];
    }
}
