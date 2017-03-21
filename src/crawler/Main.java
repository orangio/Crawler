package crawler; /**
 * Created by Filip on 14.03.2017.
 */
import crawler.Crawler.*;
public class Main{
    static void main( String[] args ) throws Exception
    {
        Crawler c1=new Crawler();
        c1.setURL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt");
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

