/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AULA_15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author danrlei.mucelin
 */

public class ThirdProjectTask0 {
    
    public static void main(String[] args) {
        
        // Providers List:
        // A (0.0715, 0.5984)
        // B (0.2336, 0.2094)
        // C (0.0612, 0.8530)
        // D (0.5088, 0.4992)
        // E (0.5567, 0.8742)
        // F (0.0944, 0.0894)
        // G (0.9028, 0.4606)
        
        Plot3 plot = new Plot3();
        
        Map<String, ArrayList<Client>> map = new TreeMap<String, ArrayList<Client>>();
        
        SimpleReader file = new SimpleReader("clients.txt");
        String line = file.readLine();
        String[] bestProvider;
        while (line != null){
            String[] words = line.split("\\ ");
            
            bestProvider = 
            DistEuclidProvider.menorDistancia(Double.parseDouble(words[1]), 
                                              Double.parseDouble(words[2])).split("/");
            
            
            if (!map.containsKey(bestProvider[0])){
                ArrayList<Client> client = new ArrayList<Client>();
                client.add(new Client(words[0], Double.parseDouble(words[1]), 
                                                Double.parseDouble(words[2]),
                                                Double.parseDouble(bestProvider[1])));
                plot.addComponent(Double.parseDouble(words[1]), Double.parseDouble(words[2]));
                
                map.put(bestProvider[0], client);
            } else {
                ArrayList<Client> client = map.get(bestProvider[0]);
                client.add(new Client(words[0], Double.parseDouble(words[1]), 
                                                Double.parseDouble(words[2]),
                                                Double.parseDouble(bestProvider[1])));
                plot.addComponent(Double.parseDouble(words[1]), Double.parseDouble(words[2]));
                
                map.put(bestProvider[0], client);
            }
            
           line = file.readLine();
        }
        file.close();
        
        plot.pack();
        plot.setVisible(true);
        
        for (String k: map.keySet()){
            
            ArrayList<Client> c = map.get(k);
            
            Collections.sort(c, new SortClientDecreasing());
            
            System.out.println("Fornecedor "+k);
            System.out.println("Cliente(ID): \n"+c.toString());
        }
        
        
    }
    
    public static class Client implements Comparable<Client>{
        public String id;
        public double longX;
        public double latY;
        public double distEuclid;

        public Client(String id, double longX, double latY, double distEuclid) {
            this.id = id;
            this.longX = longX;
            this.latY = latY;
            this.distEuclid = distEuclid;
        }
        
        public String toString(){
            return this.id;
        }
        
         public int compareTo(Client c){
            return (int) -((this.distEuclid) - (c.distEuclid));
        }

    }
    
    private static class SortClientDecreasing implements Comparator<Client> {
        
        public int compare(Client c1, Client c2){
            return - ((int) (c1.distEuclid * 100) - (int) (c2.distEuclid * 100));
        }
    }
    
    public static class DistEuclidProvider{
        
        public static String menorDistancia(double longX2, double latY2){
            String bestProvider = "";
            
            double a = Math.sqrt((Math.pow((longX2 - 0.0715), 2)) + (Math.pow((latY2 - 0.5984), 2)));
            double b = Math.sqrt((Math.pow((longX2 - 0.2336), 2)) + (Math.pow((latY2 - 0.2094), 2)));
            double c = Math.sqrt((Math.pow((longX2 - 0.0612), 2)) + (Math.pow((latY2 - 0.8530), 2)));
            double d = Math.sqrt((Math.pow((longX2 - 0.5088), 2)) + (Math.pow((latY2 - 0.4992), 2)));
            double e = Math.sqrt((Math.pow((longX2 - 0.5567), 2)) + (Math.pow((latY2 - 0.8742), 2)));
            double f = Math.sqrt((Math.pow((longX2 - 0.0944), 2)) + (Math.pow((latY2 - 0.0894), 2)));
            double g = Math.sqrt((Math.pow((longX2 - 0.9028), 2)) + (Math.pow((latY2 - 0.4606), 2)));
            
            double best = a;
            bestProvider = "A";
            
            if(b < best){
               best = b;
               bestProvider = "B";
            }
            if(c < best){
               best = c;
               bestProvider = "C";
            }
            if(d < best){
               best = d;
               bestProvider = "D";
            }
            if(e < best){
               best = e;
               bestProvider = "E";
            }
            if(f < best){
               best = f;
               bestProvider = "F";
            }
            if(g < best){
               best = g;
               bestProvider = "G";
            }
            
            return bestProvider+"/"+best;
        }
     }
    
    public static class Plot3 extends JFrame {
   
    PlotComponent3 pcomp = null;
    
    public Plot3() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pcomp = new PlotComponent3(1280, 720);
        add(pcomp);
    
    }
    
    public void addComponent(double x, double y){
       this.pcomp.addPoint(x * 1280, y * 720);
    
    }
    public void main(String[] args) {
        SwingUtilities.invokeLater(()-> new Plot2());
    }
}

public static class PlotComponent3 extends JComponent {
    private ArrayList<Point2D> points = new ArrayList<Point2D>();

    public PlotComponent3(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    public void addPoint(double x, double y) {
        this.points.add(new Point2D.Double(x, y));
        
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.BLUE);

        for (Point2D p : points) {
            Shape point = new Ellipse2D.Double(p.getX(), p.getY(), 10, 10);
            g2d.draw(point);
        }
    }
}
    
}
