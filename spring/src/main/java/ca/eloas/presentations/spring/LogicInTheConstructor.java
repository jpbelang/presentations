package ca.eloas.presentations.spring;

public class LogicInTheConstructor {

    public interface Factory {
        String createString(String someParameter);
    }


    final private boolean flag;
    public LogicInTheConstructor(Factory fac, String param) {

        String point = fac.createString(param);
        if ( "bad".equals(point) ) {
            this.flag = false;
        } else {
            this.flag = true;
        }
    }
}
