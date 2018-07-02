import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class TestPercolation {
    public static final ResourceBundle rb = ResourceBundle.getBundle("coursera");

    @Test
    public void mustBeInitiatedWithNxNCells() throws IOException, URISyntaxException {
        int n = Integer.valueOf(rb.getString("test1.n"));
        Percolation p = new Percolation(n);
        int ind = 1;
        while (rb.containsKey("test1.check" + ind + ".cell")) {
            String [] cells = rb.getString("test1.check" + ind + ".cell").split(",");
            int i = Integer.valueOf(cells[0]);
            int j = Integer.valueOf(cells[1]);
            boolean isOpen = Boolean.valueOf(rb.getString("test1.check" + ind + ".isOpen"));
            Assert.assertEquals(p.isOpen(i, j), isOpen);
            ind++;
        }
        System.out.println("Test ok!");
    }

    /*@Test
    public void isTheTopCellConnectedToTheFirstRowCells() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int n = Integer.valueOf(rb.getString("test2.n"));
        Percolation p = new Percolation(n);

        Method method = p.getClass().getDeclaredMethod("isConnected", int.class, int.class);
        method.setAccessible(true);
        for (int i = 1; i <= n; i++) {
            Boolean r = (Boolean) method.invoke(p, Integer.valueOf(0), Integer.valueOf(i));
            Assert.assertEquals(i + " row is not connected!", r, true);
        }
        System.out.println("Test ok!");
    }*/

    /*@Test
    public void isTheBottomCellConnectedToTheFirstRowCells() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int n = Integer.valueOf(rb.getString("test2.n"));
        Percolation p = new Percolation(n);

        Method method = p.getClass().getDeclaredMethod("isConnected", int.class, int.class);
        method.setAccessible(true);
        for (int i = 1; i <= n; i++) {
            Boolean r = (Boolean) method.invoke(p, Integer.valueOf(n*n+1), Integer.valueOf(n*n+1 - i));
            Assert.assertEquals(r, true);
        }
        System.out.println("Test ok!");
    }*/

    @Test
    public void isCellWetAccordingToTestFile() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, URISyntaxException {

        int testInd = 1;
        while (rb.containsKey("test-wet" + testInd + ".file")) {
            int operationsAmount = Integer.valueOf(
                    rb.getString("test-wet" + testInd + ".check1.operation-amount"));
            Percolation p = executeCellOpensAccordingToTestFileInstructions("test-wet", testInd, operationsAmount);
            int checkInd = 1;
            while (rb.containsKey("test-wet" + testInd + ".check" + checkInd + ".cell")) {
                String [] checkCoords = rb.getString("test-wet" + testInd + ".check" + checkInd + ".cell").split(",");
                int i = Integer.valueOf(checkCoords[0]);
                int j = Integer.valueOf(checkCoords[1]);
                boolean isOpen = Boolean.valueOf(rb.getString("test-wet" + testInd + ".check" + checkInd + ".isWet"));
                Assert.assertEquals(i + ":" + j + " cell does not correspond requirements!",
                        p.isFull(i, j), isOpen);
                checkInd++;
            }
            System.out.println(rb.getString("test-wet" + testInd + ".file") + " ok!");
            testInd++;
        }
        System.out.println("Test ok!");
    }

    @Test
    public void doesSystemPercolate() throws InvocationTargetException, NoSuchMethodException, URISyntaxException, IllegalAccessException, IOException {
        int testInd = 1;
        while (rb.containsKey("test-percolates" + testInd + ".file")) {
            Percolation p = executeCellOpensAccordingToTestFileInstructions("test-percolates", testInd, 0);
            int checkInd = 1;
            while (rb.containsKey("test-percolates" + testInd + ".check" + checkInd + ".percolates")) {
                boolean isPercolate = Boolean.valueOf(
                        rb.getString("test-percolates" + testInd + ".check" + checkInd + ".percolates"));
                Assert.assertEquals(testInd + " percolation is wrong!", p.percolates(), isPercolate);
                checkInd++;
            }
            System.out.println("    " + rb.getString("test-percolates" + testInd + ".file") + " - OK!");
            testInd++;
        }
        System.out.println("Test ok!");
    }

    private Percolation executeCellOpensAccordingToTestFileInstructions(String testkind, int testInd, int operationsAmount) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, URISyntaxException {
        List<String> lines = Files.readAllLines(
                Paths.get(this.getClass().getResource(rb.getString(testkind + testInd + ".file")).toURI()), Charset.defaultCharset());
        int n = Integer.valueOf(lines.get(0));
        Percolation p = new Percolation(n);
        for (int openInd = 1; openInd <= ((operationsAmount == 0) ? lines.size() - 1 : operationsAmount); openInd++) {
            String coordString = lines.get(openInd).replaceAll("    ", " ").replaceAll("   ", " ").
                    replaceAll("  ", " ").trim();
            if (coordString.isEmpty()) {
                continue;
            }
            String[] cellCoords = coordString.split(" ");
            int i = Integer.valueOf(cellCoords[0]);
            int j = Integer.valueOf(cellCoords[1]);
            p.open(i, j);
        }
        return p;
    }
}

