package crawler;


/**
 * Created by Filip on 22.03.2017.
 */
public class ConsoleLogger {

    public  void log(String status, Student student) {


        switch(status)
        {
            case "add":
                System.out.println("STUDENT ADDED "+student.getFirstName()+" "+student.getLastName()+" "+student.getMark()+" "+student.getAge());

            case "del":
                System.out.println("STUDENT REMOVED "+student.getFirstName()+" "+student.getLastName()+" "+student.getMark()+" "+student.getAge());
        }

    }
}
