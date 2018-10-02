/*
 * This class is responsible for calculating the determinant of each 
 * matrix. It uses recursion to continually create matrices of one 
 * degree smaller than the current size, until it reaches matrices 
 * that are of degree 1. It uses the Laplace formula to calculate
 * the determinant.
 * 
 * inputs: order is the int which is first collected from the input file, 
 * but is altered through the call to getMatrix, which creates a submatrix
 * 
 * input: matrix is a 2D array, originally will be the array collected from
 * the input file, but will be altered by the calls to getMatrix
 * 
 * 
 */


public class Determinant {
    
    /*
     * getDeterminant is responsible for calculating the determinant.
     * 
     * It uses recursion, with a stopping case of order == 1. In that case
     * it will return the element of the 1 degree matrix. Otherwise, will call
     * itself again with a new matrix, which is the return of getMatrix.
     * inputs: order is the int which is first collected from the input file, 
     * but is altered through the call to getMatrix, which creates a submatrix
     * 
     * input: matrix is a 2D array, originally will be the array collected from
     * the input file, but will be altered by the calls to getMatrix
     * 
     * 
     * 
     */
   
    
    public int getDeterminant( int order, int[][] matrix )
    {
        int determinant = 0;
        int newDeterminant = 0;
        
        //stopping case
        if ( order == 1)
        {
            determinant = matrix[0][0];  // only element
            return determinant;  // only element
        }
        
        //continue recursion
        else if (order >1)
        {
            int col = order;  // column number
            int sign = 1;   // starts positive, but every loop through will be 
            //multiplied by -1; 
            for( col = 0; col<matrix.length; col++ )
            {
                newDeterminant = sign*(matrix[0][col]*
                        getDeterminant( order-1, getMatrix(matrix, order-1, col )) ); 
                determinant += newDeterminant;
                sign*=-1;
            }
            return determinant;
        }
        else
        {
            System.out.println("The matrix cannot have a degree less than 1.");
            return 000;
        }
    }
    
    
    /*
     * getMatrix returns the smaller submatrix requested by getDeterminant
     * It uses the Laplace method to determine which elements to include in the
     * new submatrix. 
     * input: startingMatrix is the larger matrix sent by getDeterminant
     * order; order is the order of the new matrix being created. getDeterminant
     * will always call with order-1.
     * column is the column that is currently being eliminated in the 
     * startingMatrix
     * 
     * This method will take a larger startingMatrix, and loop through all 
     * elements, adding them to newMatrix, only if their location is not in 
     * row 0 of startingMatrix, and if their location is not in the column
     * index passed as an argument.
     */
    //order is the order of the matrix; column is the column of the current M
    // column always called by getDeterminant as 0, changed within getMatrix
    public int[][] getMatrix( int [][] startingMatrix, int order, int column )
    {
        //error checking if order == 0
        int [][] newMatrix = new int[order][order]; 
        //if (order == 3)
            int row; //row number, 0 index
            int col; //current column number, 0 index
            int colNewMatrix=0; //current column number, 0 index
            for (col = 0; col< startingMatrix.length; col++ )
            {
                //loop through, add to submatrix when not in row/column 
                //of multiplier
                if (col!= column)
                {
                    for  ( row = 1; row < startingMatrix.length; row++ )
                    {
                        newMatrix[row-1][colNewMatrix] = startingMatrix[row][col];
                    }
                    colNewMatrix++;
                }
            }
            return newMatrix;
    }
}
