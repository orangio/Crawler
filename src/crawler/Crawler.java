package crawler;
import crawler.*;

import java.io.Console;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;



/**
 * Created by Filip on 14.03.2017.
 */
public class Crawler {
    URL myurl = new URL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt");
    int it=0;

    Set<Student> currentSet;
    Set<Student> previousSet;
    Set<Student> removedSet;
    Set<Student> addedSet;

    List<Student> currentList=new LinkedList<>();
    List<Student> previousList=new LinkedList<>();

    private final List<CrawlerEventListener> listeners = new LinkedList<>();

    public Crawler() throws MalformedURLException {
    }
    public void addListener(CrawlerEventListener listener) {
        this.listeners.add(listener);
    }

    public void setURL(URL url) {
        this.myurl = url;
    }

    public synchronized void run() throws Exception {
        if (myurl.getPath().equals(" "))
            throw new CrawlerException("Crawler Exception, Invalid/No URl");


        while (true) {

            previousList = currentList;
            currentList = StudentsParser.parse(myurl);

            currentSet=new HashSet<>(currentList);
            previousSet=new HashSet<>(previousList);

            removedSet=new HashSet<>(previousList);
            addedSet=new HashSet<>(currentList);



            removedSet.removeAll(currentSet);
            addedSet.removeAll(previousSet);

            currentList.sort(
                    (a, b) -> (a.getLastName() + a.getFirstName()).compareToIgnoreCase(b.getLastName() + b.getFirstName()));
            if (previousList != null && currentSet != null) {


                if (addedSet.size() == 0 && removedSet.size() == 0) {
                    for (Student s : currentSet) {
                        for(CrawlerEventListener l : listeners)
                        {
                            l.onNoChange();
                        }
                    }
                } else {
                    for (Student s : addedSet) {
                        for(CrawlerEventListener l : listeners)
                        {
                            l.onStudentAdded(s);
                        }
                    }

                    for (Student s : removedSet) {
                        for(CrawlerEventListener l : listeners)
                        {
                            l.onStudentDeleted(s);
                        }
                    }
                }

            }

            Thread.sleep(1000);

            it++;
        }
    }


    private List<Student> getAdded(List<Student> a, List<Student> b) {
        List<Student> result = new LinkedList<>();

            for (Student s : b) {
                result.add(s.clone());
            }

            result.removeAll(a);

        return result;
    }

    List<Student> extractStudents(OrderMode mode) {
        List<Student> students = new ArrayList<Student>(currentSet);

        switch (mode) {
            case age:
                students.sort(Comparator.comparingInt(Student::getAge));
                break;

            case mark:
                students.sort(Comparator.comparingDouble(Student::getMark));
                break;

            case name:
                students.sort(Comparator.comparing(Student::getFirstName));
                break;

            case surname:
                students.sort(Comparator.comparing(Student::getLastName));
                break;
        }
        return students;
    }


    double extractMark(ExtremumMode mode) {
        double mark = 0;
        for (Student s : currentSet) {
            if (s.getMark() > mark && mode == ExtremumMode.max)
                mark = s.getMark();
            if (s.getMark() < mark && mode == ExtremumMode.min)
                mark = s.getMark();
        }
        return mark;
    }

    int extractAge(ExtremumMode mode) {
        int age = 0;
        for (Student s : currentSet) {
            if (s.getAge() > age && mode == ExtremumMode.max)
                age = s.getAge();
            if (s.getAge() < age && mode == ExtremumMode.min)
                age = s.getAge();
        }
        return age;
    }
}
