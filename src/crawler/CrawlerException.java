package crawler;

/**
 * Created by Filip on 14.03.2017.
 */
public class CrawlerException extends Exception {
    private String message;

    public CrawlerException(String message) {
        super(message);
    }
}
