package crawler; /**
 * Created by Filip on 14.03.2017.
 */
import crawler.Crawler.*;
public class Main{
    public static void main( String[] args ) throws Exception
    {
        Student b = new Student();
        Crawler c1=new Crawler();
        //c1.setURL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt");
        MailLogger mailLog= new MailLogger();
        ConsoleLogger conLog=new ConsoleLogger();
        //mailLog.log("add",b);
        //mailLog.log("del",b);
        //conLog.log("add",b);
        try {
            c1.run();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}

