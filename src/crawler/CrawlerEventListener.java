package crawler;

/**
 * Created by Filip on 03.04.2017.
 */
public interface CrawlerEventListener {
    void onStudentAdded(Student a);
    void onStudentDeleted(Student a);
    void onNoChange();
}
