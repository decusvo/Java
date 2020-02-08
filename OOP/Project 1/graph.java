import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.IOException;


public class graph extends Application {

    
 
    @Override public void start(Stage stage) throws IOException {
    String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    
    FrequencyClass getCounterArray = new FrequencyClass();
    int counters[] = getCounterArray.counterCalculator();
    System.out.println(counters);
    
        stage.setTitle("Frequency Analysis");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Frequency analysis");
        xAxis.setLabel("Characters");       
        yAxis.setLabel("Frequency");
        
        
 
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Frequency Analysis of jc.txt");       
        //series1.getData().add(new XYChart.Data(austria, 25601.34));
        
        for (int i=0;i< alphabet.length;i++) {
        series1.getData().add(new XYChart.Data(alphabet[i],counters[i]));
        }
        series1.getData().add(new XYChart.Data("Other",counters[26]));

        Scene scene  = new Scene(bc,900,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
 
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
