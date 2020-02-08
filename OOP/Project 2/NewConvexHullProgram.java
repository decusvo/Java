	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.Scanner;

import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class NewConvexHullProgram extends Application {

	public static ArrayList<Line> Edges_array = new ArrayList<Line>();
	public static double[] xVal;
	public static double[] yVal;
	public static ArrayList<Circle> circle_array = new ArrayList<Circle>();
	public static ArrayList<Line> hedgehogPath = new ArrayList<Line>();
	
	public static ArrayList<Line> axes = new ArrayList<Line>();
	

	 static int loadPoints(double[] xVal,double[] yVal,int maxPoints) {
		
		Scanner keyboard = new Scanner(System.in);

		int numPoints = 0;
		double input = 0;
		boolean negative_detected = false;
		
		while ((negative_detected == false ) & (numPoints < (maxPoints))) {
			
			for (int i =0;i < xVal.length;i++) { // both xVal and yVal will be same length.
				
				
				System.out.print("Please enter xVal for position "+(i+1)+" :");
				input = keyboard.nextDouble();
				xVal[i] = input;

				
				
				System.out.print("Please enter yVal for position "+(i+1)+" :");
				input = keyboard.nextDouble();
				yVal[i] = input;

				numPoints++;
				
				System.out.println("("+xVal[i]+";"+yVal[i]+")");
				Circle circle = new Circle((xVal[i]+5)*20,(yVal[i]+5)*20,4);
				circle_array.add(circle);
				
				if ((xVal[i] < 0 ) | (yVal[i] < 0)) {
					System.out.println("ERROR: Negative value entered.");
					System.out.println("Loading points stopped.");
					negative_detected = true;
					System.exit(0);
				}
					
				if (numPoints == (2*maxPoints)) {
					System.out.println("Max system capacity reached.");
					break;
				}
			}
		}
		keyboard.close();
		return numPoints;
		
	}
	 
	 static boolean checkDuplicates(int pointCount, double xVal[], double yVal[]) {
		 for (int i=pointCount-1; i>=1; i--) {
			 	for (int j=0; j<i; j++) {
			 		if ((xVal[i] == xVal[j]) && (yVal[i] == yVal[j])) {
			 			System.out.println("Duplicate is found!");
			 			return true;
			 			
			 		}
			 	}
		 }
		 return false;
	}
	 

	
	static void computeConvexHull(int pointCount, double xVal[],double yVal[]) {
		double m; // gradient initialized
		double c; // intercept initialized.

		for(int i = pointCount ;i>= 1; i--) { // pointCount is maxPoints - 1
			for(int j = 0;j < i; j++) {
			
			
				int above = 0;
				int below = 0;

				double m_top = (yVal[i]-yVal[j]);
				double m_bot = (xVal[i]-xVal[j]);
				 
				 m = (m_top/m_bot); // gradient (y1-y2)/(x1-x2)
				 c = yVal[i] - m*xVal[i]; // intercept
				 for (int k= 0;k<pointCount ; k++) {
				 // first case is considered.
					 if((m == Double.POSITIVE_INFINITY) || (m == Double.NEGATIVE_INFINITY)) {
						 
						 if (xVal[k] > xVal[i]) {
							 above++;
						 }
						 if (xVal[k] < xVal[i]) {
							 below++;
						 }
						 
					 } else {
					 // if m is numeric then this scenario runs.
						 
						 double val = (m*xVal[k] +c);
						 
						 if (yVal[k] > val) {
							 above++;
							 
						 } 
						 if ((yVal[k]) < val) {
							 below++;
						 }
					}
				 } 
				 
				 if ((below>0) ^ (above>0) == true) { // checks if only one of the two variables is above 0
					 System.out.println("edge ("+xVal[i]+","+yVal[i]+"),("+xVal[j]+","+yVal[j]+") on the convex hull");
					 Line line = new Line((xVal[i]+5)*20,(yVal[i]+5)*20,(xVal[j]+5)*20,(yVal[j]+5)*20);
					 Edges_array.add(line);
					 
					 
					

				 }
			}
		}
		
		
	}
    
 

	@Override public void start(Stage stage) throws Exception {
		
        stage.setTitle("Convex Hull");

        Group root = new Group();
        
        for(int i = 0; i < circle_array.size();i++) {
        	Circle circle = circle_array.get(i);
        	circle.setFill(Color.ORANGE);
        	
        	root.getChildren().add(circle);
        }
        
 
        for (int i=0; i < Edges_array.size();i++){
        	Line line = Edges_array.get(i);
        	  line.setStroke(Color.RED);
              line.setStrokeWidth(5);
        	
        	root.getChildren().add(line);
        }
            for(int i = 0; i < hedgehogPath.size();i++) {
        	Line line = hedgehogPath.get(i);
        	line.setStroke(Color.GREEN);
            line.setStrokeWidth(2);
        	
        	root.getChildren().add(line);
        }
        
        Line y_axis = new Line(15,15,15,385);
        Text y_axis_label = new Text (-12,y_axis.getEndY()/2, "Y Axis");
        y_axis_label.setRotate(-90);
        Line x_axis = new Line(15,15,385,15);
        Text x_axis_label = new Text (x_axis.getEndX()/2, 12, "X Axis");
        
        root.getChildren().add(y_axis_label);
        root.getChildren().add(x_axis_label);
        root.getChildren().add(y_axis);
        root.getChildren().add(x_axis);
        	
        
        Scene scene  = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
    }
	
	
    
    	public static void main(String args[]) {

		Scanner keyboard = new Scanner(System.in);
		System.out.print("Please enter MAX_POINTS: ");
		int MAX_POINTS = keyboard.nextInt();
		
		xVal = new double[MAX_POINTS];
		yVal = new double[MAX_POINTS];
		
		int pointCount = loadPoints(xVal,yVal,MAX_POINTS);
		
		double distance = 0;
		
		for (int i =0;i< xVal.length-1;i++){
            
            
            Line line = new Line((xVal[i]+5)*20,(yVal[i]+5)*20,(xVal[i+1]+5)*20,(yVal[i+1]+5)*20);
            distance += Math.hypot(xVal[i]-xVal[i+1],yVal[i]-yVal[i+1]);
            hedgehogPath.add(line);
		
		}
		
		System.out.println("The range of the hedgehog is:"+distance);
		
		
		if (checkDuplicates(pointCount-1, xVal, yVal)) return;
		computeConvexHull(pointCount-1,xVal, yVal);
		
		
		keyboard.close();
		launch(args);
		
		
	}
}
