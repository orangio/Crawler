package crawler;

/**
 * Created by Filip on 19.03.2017.
 */
public class CrawlerEvent {
    //private final CrawlerEventType type;
    private String type;
    private Crawler crawler;
    private Student student;
    private long iteration;

    public CrawlerEvent(String type, Student student, long iteration) {
        this.type = type;
        this.student = student;
        this.iteration = iteration;
    }

    public CrawlerEvent(String type) {
        this.type = type;
    }

    public CrawlerEvent(String type, Crawler crawler) {
        this.type = type;
        this.crawler = crawler;
    }

    public CrawlerEvent(String type, Crawler crawler, Student student) {
        this.type = type;
        this.crawler = crawler;
        this.student = student;
    }

    public CrawlerEvent(String type, Student student) {
        this.type = type;
        this.student = student;
    }

    public long getIteration() {
        return iteration;
    }

    public String getType() {
        return type;
    }

    public Student getStudent() {
        return student;
    }
}