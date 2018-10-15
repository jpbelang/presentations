package ca.eloas.presentations.streams;

/**
 * Created. There, you have it.
 */
public class Job {

    private final String title;
    private final String company;
    private final int salary;

    public Job(String title, String company, int salary) {
        this.title = title;
        this.company = company;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
