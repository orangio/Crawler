package crawler;

/**
 * Created by Filip on 04.04.2017.
 */
public class GUILogger implements Logger, CrawlerEventListener {
    public void onStudentAdded(Student a)
    {
        log("add",a);
    }
    public void onStudentDeleted(Student a)
    {
        log("del",a);
    }
    public void onNoChange(){};
    public void log(String status, Student student)
    {

    }
}
