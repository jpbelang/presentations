package ca.eloas.presentations.java8functionnal;

import java.util.Random;
import java.util.concurrent.Executor;

/**
 * Created. There, you have it.
 */
public class LambdaRunnable {

    public void executeFrom(Executor executor) {

        final int actionId = new Random().nextInt();

        Runnable r = () -> {
            switch (actionId) {
                /* ... */
            }
        };
        executor.execute(r);
    }
}
