package crawler;

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
    //String URL = " ";
    URL myurl = new URL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt");
    int it=0;

    Set<Student> currentSet;
    Set<Student> previousSet;
    Set<Student> removedSet;
    Set<Student> addedSet;

    List<Student> currentList;
    List<Student> previousList;
    MailLogger mail=new MailLogger();


    private final List<CrawlerListen> studentAddedListeners = new LinkedList<>();
    private final List<CrawlerListen> studentRemovedListeners = new LinkedList<>();
    private final List<CrawlerListen> studentNoChangeListeners = new LinkedList<>();
    private final List<CrawlerListen> iterationStartedListeners = new LinkedList<>();
    private final List<CrawlerListen> iterationFinishedListeners = new LinkedList<>();


    public Crawler() throws MalformedURLException {
    }

    public void setURL(URL url) {
        this.myurl = url;
    }

    public synchronized void run() throws Exception {
        if (myurl.getPath().equals(" "))
            throw new CrawlerException("Crawler Exception, Invalid/No URl");


        currentList = StudentsParser.parse(myurl);

        while (true) {
            listenersCall("start", null, it);

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


                List<Student> added = getAdded(currentList, currentList);
                List<Student> removed = getAdded(currentList, previousList);

                if (addedSet.size() == 0 && removedSet.size() == 0) {
                    for (Student s : currentSet) {
                        listenersCall("no", s, it);
                        mail.log("no",s);
                    }
                } else {
                    for (Student s : addedSet) {
                        listenersCall("add", s, it);
                        mail.log("add",s);
                    }

                    for (Student s : removedSet) {
                        listenersCall("del", s, it);
                        mail.log("del",s);
                    }
                }

            }

            Thread.sleep(1 * 1000);

            it++;
            listenersCall("stop", null, it);
            for (Student s : currentList) {
                Set<Student> tmp =new HashSet<>();
                tmp.add(s);
                currentSet=tmp;
            }
        }
    }

    private synchronized void listenersCall(String type, Student student, long iteration) {
        switch (type) {
            case "add":
                for (CrawlerListen crawlerListener : studentAddedListeners)
                    crawlerListener.actionPerformed(new CrawlerEvent(type, student, iteration));
                break;

            case "del":
                for (CrawlerListen crawlerListener : studentRemovedListeners)
                    crawlerListener.actionPerformed(new CrawlerEvent(type, student, iteration));
                break;

            case "no":
                for (CrawlerListen crawlerListener : studentNoChangeListeners)
                    crawlerListener.actionPerformed(new CrawlerEvent(type, student, iteration));
                break;

            case "start":
                for (CrawlerListen crawlerListener : iterationStartedListeners)
                    crawlerListener.actionPerformed(new CrawlerEvent(type, null, iteration));
                break;

            case "stop":
                for (CrawlerListen crawlerListener : iterationFinishedListeners)
                    crawlerListener.actionPerformed(new CrawlerEvent(type, null, iteration));
                break;
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


    public void addStudentAddedListener(CrawlerListen crawlerListener) {
        studentAddedListeners.add(crawlerListener);
    }

    public void addStudentRemovedListener(CrawlerListen crawlerListener) {
        studentRemovedListeners.add(crawlerListener);
    }

    public void addStudentNoChangeListener(CrawlerListen crawlerListener) {
        studentNoChangeListeners.add(crawlerListener);
    }

    public void addIterationStartedListener(CrawlerListen crawlerListener) {
        iterationStartedListeners.add(crawlerListener);
    }

    public void addIterationFinishedListeners(CrawlerListen crawlerListener) {
        iterationFinishedListeners.add(crawlerListener);
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
