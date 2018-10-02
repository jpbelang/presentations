package ca.eloas.presentations.spring;

import java.util.Date;
import java.util.concurrent.Executor;

public class AnotherTask {

    final private Executor serverExecutor;

    public AnotherTask (Executor serverExecutor) {
        this.serverExecutor = serverExecutor;
    }

    public void executeTask(Date jobStartTime) {

        serverExecutor.execute(() -> {
            if ( System.currentTimeMillis() - jobStartTime.getTime() < 10000) {

                // we are not too late, we run....
            }
        });
    }

}
