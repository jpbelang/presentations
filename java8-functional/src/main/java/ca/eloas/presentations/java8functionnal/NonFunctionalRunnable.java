package ca.eloas.presentations.java8functionnal;

import java.util.Random;
import java.util.concurrent.Executor;

/**
 * Created. There, you have it.
 */
public class NonFunctionalRunnable {

    public void executeFrom(Executor executor) {

        final int actionId = new Random().nextInt();

        Runnable r = new Runnable () {
            public void run() {
                switch (actionId) {
                    /* ... */
                }
            }
        };
        executor.execute(r);
    }
}
