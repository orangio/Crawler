package crawler;


/**
 * Created by Filip on 22.03.2017.
 */
public class ConsoleLogger implements CrawlerEventListener{

    public  void log(String status, Student student) {


        switch(status)
        {
            case "add":
                System.out.println("STUDENT ADDED "+student.getFirstName()+" "+student.getLastName()+" "+student.getMark()+" "+student.getAge());
                break;

            case "del":
                System.out.println("STUDENT REMOVED "+student.getFirstName()+" "+student.getLastName()+" "+student.getMark()+" "+student.getAge());
                break;

            case "no":
                System.out.println("NO CHANGE IN ENTRIES");
                break;
        }

    }

    @Override
    public void onStudentAdded(Student a) {
        log("add",a);
    }

    @Override
    public void onStudentDeleted(Student a) {
        log("del",a);
    }

    @Override
    public void onNoChange(){
        System.out.println("NO CHANGE IN ENTRIES");
    }
}
