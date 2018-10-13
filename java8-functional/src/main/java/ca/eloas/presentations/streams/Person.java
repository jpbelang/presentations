package ca.eloas.presentations.streams;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created. There, you have it.
 */
public class Person {

    private final String name;
    private final List<Job> jobs;
    private final LocalDate birthDay;

    public Person(String name, List<Job> jobs, LocalDate birthDay) {
        this.name = name;
        this.jobs = jobs;
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }
}
