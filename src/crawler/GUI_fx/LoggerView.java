package crawler.GUI_fx;

import crawler.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

/**
 * Created by Filip on 03.04.2017.
 */
public class LoggerView  extends Tab implements  CrawlerEventListener {

    private final ListView<String> listView;
    private final ObservableList<String> data;
    public void onStudentAdded(Student a)
    {

    }
    public void onStudentDeleted(Student a)
    {

    }
    public void onNoChange(){};

    public LoggerView(){
        this.setText("Log");
        this.setClosable(false);
        this.listView = new ListView<>();
        this.data = FXCollections.observableArrayList();
        this.listView.setItems(data);
        this.setContent(this.listView);

        //test działania
        Student a=new Student();
        //log("add",a);
        //log("del",a);
        //log("add",a);
        //log("del",a);
        //log("add",a);

    }


    public void log(String status, Student student)
    {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");

        switch (status){
            case "add":
                data.add(simpleDateFormat.format(date) + "        [ADDED]          " + "name:"+student.getFirstName() + " surname:"+ student.getLastName()+ " mark:"+ student.getMark());
                break;

            case "del":
                data.add(simpleDateFormat.format(date) + "        [REMOVED]          " + "name:"+student.getFirstName() + " surname:"+ student.getLastName()+ " mark:"+ student.getMark());
                break;
        }
    }

    public void addData(Student student, String status)
    {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        switch (status){
            case "add":
                data.add(simpleDateFormat.format(date) + "        [ADDED]          " + "name:"+student.getFirstName() + " surname:"+ student.getLastName()+ " mark:"+ student.getMark());
                break;

            case "del":
                data.add(simpleDateFormat.format(date) + "        [REMOVED]          " + "name:"+student.getFirstName() + " surname:"+ student.getLastName()+ " mark:"+ student.getMark());
                break;
        }
    }

}
