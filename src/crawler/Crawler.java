package crawler;

import java.io.Console;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import java.io.File;


/**
 * Created by Filip on 14.03.2017.
 */
public class Crawler {
    String URL = " ";
    URL myurl = new URL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt");
    int it=0;

    Set<Student> currentSet;
    Set<Student> previousSet;

    private final List<CrawlerListen> studentAddedListeners = new LinkedList<>();
    private final List<CrawlerListen> studentRemovedListeners = new LinkedList<>();
    private final List<CrawlerListen> studentNoChangeListeners = new LinkedList<>();
    private final List<CrawlerListen> iterationStartedListeners = new LinkedList<>();
    private final List<CrawlerListen> iterationFinishedListeners = new LinkedList<>();


    public Crawler() throws MalformedURLException {
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public synchronized void run() throws Exception {
        if (myurl.getPath().equals(" "))
            throw new CrawlerException("Crawler Exception, Invalid/No URl");

        while (true) {
            int keepGoing = 2;
            while (keepGoing > 0) {
                listenersCall("start", null, it); // Wywołanie listenerów

                File tmpFile = new File(outputDirectory + String.valueOf(it)); // Tworzymy obiekt pliku "tmp"
                FileUtils.copyURLToFile(url, tmpFile); // Wczytujemy dane z url to pliku

                List<Student> previousData = currentData;
                currentSet = StudentsParser.parse(tmpFile); // Parsujemy dane z pliku do currentData
                currentSet.sort(
                        (a, b) -> (a.getLastName() + a.getFirstName()).compareToIgnoreCase(b.getLastName() + b.getFirstName())); // Zawsze posortowane dane w liście ułatwią późniejsze porównywanie zmian

                if (previousData != null && currentSet != null) {
                    List<Student> added = getAdded(previousData, currentSet);
                    List<Student> removed = getAdded(currentSet, previousData);

                    if (added.size() == 0 && removed.size() == 0) { // Jeśli wielkości list currentData i previousData są takie same, to mamy pewność, że żaden student nie został dodani ani usunięty
                        for (Student s : currentSet) {
                            listenersCall("no", s, it);
                        }
                    } else {
                        for (Student s : added) {
                            listenersCall("add", s, it);
                        }

                        for (Student s : removed) {
                            listenersCall("del", s, it);
                        }
                    }

                }

                Thread.sleep(1 * 1000); // Usypiamy wątek na określony czas (milisekundy)

                it++; // Inkrementacja licznika iteracji
                keepGoing--;
                listenersCall("stop", null, it);
            }
            StudentsParser.parse(myurl);
            Thread.sleep(10000);
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
        List<Student> students = new ArrayList<Student>();
        students.addAll(currentSet);

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
