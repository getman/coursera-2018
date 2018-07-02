/**
 * Created by ArtemParfenov on 25.06.2018.
 */

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

/**
 * Created by ArtemParfenov on 03.02.2016.
 */
public class Percolation2Test {

    //    @Test
    public void testPercolation() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ResourceBundle properties = ResourceBundle.getBundle("percolation-data");
        int k = 1;
        try{
            while (true) {
                int n = Integer.valueOf(properties.getString("n."+k));
                String[] sites = properties.getString("sites." + k).split(" ");
                String[] iArray = new String[sites.length];
                String[] jArray = new String[sites.length];
                for (int i = 0; i < sites.length; i++) {
                    String[] coords = sites[i].replaceAll("[()]","").split(",");
                    iArray[i] = coords[0];
                    jArray[i] = coords[1];
                }
//                System.out.println((callPercolation(n, iArray, jArray, sites.length, null) ? "Noo" : "percolates") + " for test " + k);
                k++;
            }
        } catch (Exception e) {
            System.out.println("Finished");
        }
    }

    @Test
    public void testCourseraData() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ResourceBundle properties = ResourceBundle.getBundle("coursera");
        int testIndex = 1;
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            BufferedReader br = new BufferedReader(new FileReader(properties.getString("test." + testIndex + ".path")));
            try {
                int iterationsIndex = 1;
                while (true) {
                    int iterations = Integer.valueOf(properties.getString("test." + testIndex + ".iterations." + iterationsIndex));
                    boolean isFull = Boolean.valueOf(properties.getString("test." + testIndex + ".isFull." + iterationsIndex));
                    String[] controlSite = properties.getString("test." + testIndex + ".controlSite." + iterationsIndex).trim().split(",");
                    String nSize = br.readLine();
                    int n = Integer.valueOf(nSize);//1st digit is N
                    List<String> iArray = new ArrayList<String>();
                    List<String> jArray = new ArrayList<String>();
                    String nextIJPair;
                    for (int i = 0; (nextIJPair = br.readLine()) != null; i++) {
                        if (nextIJPair.trim().equals("")) {
                            continue;
                        }
                        String[] coords = nextIJPair.trim().replaceAll("(\\s)+", " ").split(" ");
                        iArray.add(coords[0]);
                        jArray.add(coords[1]);
                    }
                    callPercolation(n, iterations, isFull, Integer.valueOf(controlSite[0]), Integer.valueOf(controlSite[1]),
                            iArray.toArray(new String[iArray.size()]), jArray.toArray(new String[iArray.size()]), iArray.size(),
                            drawCells(frame, n));
                    System.out.println("test" + testIndex + " ok");
                    iterationsIndex++;
                }
            } catch (MissingResourceException e) {/**when tests will finish*/}
            testIndex++;
        }
    }

    private void callPercolation(int n, int iterations, boolean isFull, int controlI, int controlJ, String [] iArray, String [] jArray, int testSitesLenght, JTable tbl) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class aclass = Class.forName("Percolation2");
        Class c = Class.forName("Percolation2");
        Constructor[] con = c.getConstructors();
        Object o = con[0].newInstance(n);
        Method mPercolates = c.getMethod("percolates");
        Method mOpen = c.getMethod("open", Integer.TYPE, Integer.TYPE);
        Method mIsFull = c.getMethod("isFull", Integer.TYPE, Integer.TYPE);

        StdRandom.setSeed(System.currentTimeMillis());
        int i = 0;
        boolean result = false;
        while (i < testSitesLenght) {
            int iCoord = new Integer(iArray[i].trim());
            int jCoord = new Integer(jArray[i].trim());
            mOpen.invoke(o, iCoord, jCoord);
            if (tbl != null) {
                tbl.getModel().setValueAt("X", iCoord - 1, jCoord - 1);
            }
            //stopper
            if (i == iterations - 1) {
                boolean isSiteFull = (Boolean) mIsFull.invoke(o, controlI, controlJ);
                if (isSiteFull != isFull) {
//                    while(i == iterations - 1) {}
                    throw new RuntimeException("test is broken");
                } else {
                    break;
                }
//                boolean percolates = (Boolean) mPercolates.invoke(o);
//                while(true) {}
            }
            i++;
        }
    }

    private JTable drawCells(JFrame frame, int n) {

        Object rowData[][] = new Object[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rowData[i][j] = "";
            }
        }
        Object columnNames[] = new Object[n];
        for (int i = 0; i < n; i++) {
            columnNames[i] = i;
        }
        JTable table = new JTable(rowData, columnNames);
        for (int i = 0; i < n; i++) {
            table.getColumnModel().getColumn(i).setMaxWidth(20);
            table.getColumnModel().getColumn(i).setMinWidth(20);
        }

        frame.repaint();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(500, 550);
        frame.setVisible(true);
        return table;
    }

    public class MainWindow extends JFrame {
        //---------Data--------

        //---------Methods----------
        /**Default constructor*/
        public MainWindow(){
            buildWidgets();
        }

        /**Constructs widgets*/
        private void buildWidgets(){
            //Building main layout manager
            GridBagLayout mainGBL = new GridBagLayout();
//            this.setLayout(mainGBL);

            /**Building main window*/
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setTitle("Family Tree");
            this.setSize(600, 400);
            this.setVisible(true);
//            this.setBounds(200, 200, this.getWidth(), this.getHeight() );

            //Building list of person
            /*GridBagConstraints gbcPersonListWidget = new GridBagConstraints();
            gbcPersonListWidget.gridx = 1;//2nd column
            gbcPersonListWidget.gridx = 0;//1st row
            gbcPersonListWidget.fill = GridBagConstraints.HORIZONTAL;*/

            Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
                    { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
            Object columnNames[] = { "Column One", "Column Two", "Column Three" };
            JTable table = new JTable(rowData, columnNames);

            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane, BorderLayout.CENTER);
            this.repaint();
        }
    }
}

