package crawler; /**
 * Created by Filip on 14.03.2017.
 */

import crawler.GUI_fx.*;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



public class Main extends Application{
    private static String[] arguments;
    public static void main( String[] args ) throws Exception
    {
        arguments = args;
        launch(args);
        /*Student b = new Student();
        Crawler c1=new Crawler();
        MailLogger mailLog= new MailLogger();
        ConsoleLogger conLog=new ConsoleLogger();

        c1.addListener(mailLog);

        try {
            c1.run();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error");
        }*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Crawler c1=new Crawler();
        MailLogger mailLog= new MailLogger();
        ConsoleLogger conLog=new ConsoleLogger();


        Scene scene = new Scene(new VBox(), 400, 400);

        CustomMenuBar customBar = new CustomMenuBar();

        CustomTabPane tabPane =new CustomTabPane();

        //c1.addListener(mailLog);
        c1.addListener(conLog);
        c1.addListener(tabPane);

        ((VBox) scene.getRoot()).setVgrow(tabPane,Priority.ALWAYS);
        ((VBox)scene.getRoot()).getChildren().addAll(customBar,tabPane);

        scene.setFill(Color.BLUE);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(550);
        primaryStage.show();

        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                c1.run();
                return null;
            }
        };
        try {
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}

