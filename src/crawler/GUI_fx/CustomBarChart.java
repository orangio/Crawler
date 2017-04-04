package crawler.GUI_fx;

import crawler.Student;
import javafx.scene.layout.AnchorPane;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
/**
 * Created by Filip on 03.04.2017.
 */
public class CustomBarChart extends Tab {

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart chart = new BarChart(xAxis, yAxis);
    private final Label label = new Label("Distribution of marks");
    private ObservableList<XYChart.Data<Double, Double>> data;
    XYChart.Series series1=new XYChart.Series();

    private final int[] marks = new int[7];

    public CustomBarChart() {
        super();
        series1.setName(" ");

        for(int i=0;i<7;i++)
            marks[i]=0;

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        vBox.getChildren().add(chart);

        this.setContent(vBox);
        this.setText("BarChart");
        this.setClosable(false);

        this.xAxis.setLabel("Mark");
        this.yAxis.setLabel("Count");
        this.yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.valueOf(object.intValue());
            }

            @Override
            public Number fromString(String string) {
                return null;
            }
        });
        //marks[0]=5;
        //marks[1]=3;
        //marks[3]=2;
        //marks[5]=4;
        //marks[6]=1;
        setData();
    }

    public synchronized void setData() {

        series1.getData().add(new XYChart.Data("2,0",marks[0]));
        series1.getData().add(new XYChart.Data("2,5",marks[1]));
        series1.getData().add(new XYChart.Data("3,0",marks[2]));
        series1.getData().add(new XYChart.Data("3,5",marks[3]));
        series1.getData().add(new XYChart.Data("4,0",marks[4]));
        series1.getData().add(new XYChart.Data("4,5",marks[5]));
        series1.getData().add(new XYChart.Data("5,0",marks[6]));

        this.chart.getData().addAll(series1);
    }

    public synchronized void addData(double s) {
        s=s-2;
        double n;
        n=s/0.5;
        int i;
        i=(int)n;
        marks[i]++;
        setData();
    }
    public synchronized void delData(double s) {

        s=s-2;
        double n;
        n=s/0.5;
        int i;
        i=(int)n;
        marks[i]--;
        setData();
    }

}
