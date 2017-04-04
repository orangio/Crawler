package crawler.GUI_fx;

import crawler.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;




/**
 * Created by Filip on 03.04.2017.
 */
public class CustomTabPane extends AnchorPane implements CrawlerEventListener {
    //final Histogram histogram;
    private final StudentView studentView = new StudentView();;//final StudentsTab studentsTab;
    private final LoggerView loggerView = new LoggerView();
    private final CopyOnWriteArrayList<Student> students = new CopyOnWriteArrayList<>();
    private final CustomBarChart barChart = new CustomBarChart();

    public void onStudentAdded(Student a)
    {
        loggerView.addData(a,"add");
        studentView.addData(a);
        barChart.addData(a.getMark());
    }
    public void onStudentDeleted(Student a)
    {
        loggerView.addData(a,"del");
        studentView.delData(a);
        barChart.delData(a.getMark());
    }
    public void onNoChange(){};

    public LoggerView getLoggerView() {
        return loggerView;
    }

    public void addStudents(Set<Student> s)
    {
        students.addAll(s);
    }
    public void removeStudents(Set<Student> s)
    {
        students.removeAll(s);
    }

    public CustomTabPane() {


        super();

        //Set<Student> students = new HashSet<>();
        TabPane tabPane = new TabPane();
        TableView table = new TableView();

        tabPane.getTabs().addAll(studentView,loggerView,barChart);

        TableColumn markColumn = new TableColumn("Mark");
        TableColumn firstNameColumn = new TableColumn("First Name");
        TableColumn lastNameColumn = new TableColumn("Last Name");
        TableColumn ageColumn = new TableColumn("Age");


        table.getColumns().addAll(markColumn, firstNameColumn, lastNameColumn, ageColumn);



        this.getChildren().addAll(tabPane);

        this.setLeftAnchor(tabPane, 1d);
        this.setTopAnchor(tabPane, 1d);
        this.setRightAnchor(tabPane,1d);
        this.setBottomAnchor(tabPane,1d);


    }
}
