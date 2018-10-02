package ca.eloas.presentations.spring;

import java.util.Date;
import java.util.concurrent.Executor;

public class Task {

    final private Executor serverExecutor;
    final private Date jobStartTime;

    public Task (Executor serverExecutor, Date jobStartTime) {
        this.serverExecutor = serverExecutor;
        this.jobStartTime = jobStartTime;
    }

    public void executeTask() {

        serverExecutor.execute(() -> {
            if ( System.currentTimeMillis() - jobStartTime.getTime() < 10000) {

                // we are not too late, we run....
            }
        });
    }

}
