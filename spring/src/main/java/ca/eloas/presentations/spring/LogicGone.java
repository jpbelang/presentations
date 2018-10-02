package ca.eloas.presentations.spring;

public class LogicGone {


    public interface Factory {
        String createString(String someParameter);
    }

    public static LogicGone create(LogicInTheConstructor.Factory fac, String param) {
        String point = fac.createString(param);
        if ( "bad".equals(point) ) {
            return new LogicGone(false);
        } else {
            return new LogicGone(true);
        }
    }

    final private boolean flag;
    public LogicGone (boolean flag) {

        this.flag = flag;
    }
}
