import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * This class holds the main method as well as readFile()
 * 
 * main() collects command line arguments and calls readFile()
 * with the input file location as String.
 * 
 * input: arg[0] is output file location
 * input: arg[>0] is input file location(s)
 * 
 * end state: a file is written containing the results of the tests
 * to the location specified. Results are also printed to the screen
 */
public class ReadFile {

    String outFileLocation;

    /*
     * This method simply collects the command line args and passes the input file
     * locations to readFile() to begin calculations.
     * 
     * input: arg[0] is output file location input: arg[>0] is input file
     * location(s) It will loop through all input files and return results in one
     * output file at the specified location
     * 
     */
    public static void main(String[] args)
    {
        ReadFile readF = new ReadFile(); // reference to class methods
        readF.outFileLocation = args[0]; // where to write the file

        // begin program for all input files; n is number of inputs
        // n = 0 is output file, so skip here
        for (int n = 1; n < args.length; n++) {

            readF.readFile(args[n]);
        }
    }

    /*
     * This method is responsible for interpreting the input file data
     * 
     * First, it will collect one line at a time from input file and determine if
     * the input is part of a matrix, or if it is specifying the order of a matrix.
     * It will make this categorization based on whether there is a " " in the
     * input. Matrix elements contain spaces while matrix orders do not.
     * 
     * After collecting and storing the input for one matrix with its order
     * information in readMatrix, it will pass the matrix to getDeterminant in the
     * Determinant class.
     * 
     * input: next line from the input files output: none end state: method will
     * write results to the output file, retrieved from getDeterminant
     * 
     */
    public void readFile(String inputLine)
    {
        Determinant deter = new Determinant(); // reference to class
        WriteResults wr = new WriteResults(); // reference to class
        int order = 0;
        try {

            BufferedReader readFile = new BufferedReader(
                            new FileReader(new File(inputLine)));
            String nextInput = readFile.readLine();

            // for each matrix in one input file
            while ((nextInput != null)) {
                // order entries do not contain spaces in input files
                if (!nextInput.contains(" ")) {
                    order = Integer.parseInt(nextInput);
                    System.out.println("The order of this matrix is " + order
                                    + ".");
                    wr.writeFile(outFileLocation,
                                    "The order of this matrix is " + order
                                                    + ".");
                    nextInput = readFile.readLine();
                }

                // matrix elements are separated by spaces
                if (nextInput.contains(" ")) {
                    int readMatrix[][] = new int[order][order];

                    // set tempMatrix == nextInput, then loop through
                    // tempMatrix elements and add to readMatrix at the
                    // appropriate row level and print
                    for (int row = 0; nextInput != null
                                    && nextInput.contains(" "); row++) {
                        String tempMatrix[] = nextInput.split(" ");

                        // Error checking:
                        // check that the # columns in matrix is same as
                        // stated in input
                        if (tempMatrix.length != order) {
                            System.out.println("The order of this matrix "
                                            + "stated in the input does not match.\n");
                            while (nextInput != null
                                            && nextInput.contains(" ")) {
                                nextInput = readFile.readLine();
                            }
                            break;
                        }
                        wr.writeFile(outFileLocation, nextInput);
                        try {
                            for (int col = 0; col < tempMatrix.length; col++) {
                                readMatrix[row][col] = Integer
                                                .parseInt(tempMatrix[col]);
                                System.out.print(readMatrix[row][col] + "  ");
                            }
                        } catch (NumberFormatException nfex) {
                            System.out.println("At least one element in "
                                            + "the matrix is not a number");
                            while (nextInput.contains(" ")) {
                                nextInput = readFile.readLine();
                            }
                            break;

                        } catch (ArrayIndexOutOfBoundsException aioobex) {
                            System.out.println(
                                            "The order of this matrix has too many rows for its stated size.\n");
                            while (nextInput.contains(" ")) {
                                nextInput = readFile.readLine();
                            }
                            break;
                        }
                        System.out.println();
                        wr.writeFile(outFileLocation, "\n");

                        // only calls getDeterminant when matrix rows reach
                        // expected level
                        if ((nextInput = readFile.readLine()) != null) {
                            if (!nextInput.contains(" ")
                                            && row != order - 1) {
                                System.out.println("There are not enough rows"
                                                + " in this matrix.\n");
                                wr.writeFile(outFileLocation,
                                                "There are not enough rows"
                                                                + " in this matrix.\n");
                            }
                        }
                        else if (row == (order - 1)) {
                            System.out.println("The determinant is: "
                                            + deter.getDeterminant(order,
                                                            readMatrix));
                            String resultToWrite = ("The determinant is: "
                                            + deter.getDeterminant(order,
                                                            readMatrix));
                            wr.writeFile(outFileLocation,
                                            (resultToWrite + "\n"));
                            wr.writeFile(outFileLocation, ("\n\n"));
                            System.out.println();
                            nextInput = readFile.readLine();
                        }
                    }
                }
            }

            readFile.close();
        } catch (ArrayIndexOutOfBoundsException aioobex) {
            aioobex.printStackTrace();
        } catch (NumberFormatException nfex) {
            System.out.println(
                            "At least one element in this matrix is not a number.\n");
            nfex.printStackTrace();
        } catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
