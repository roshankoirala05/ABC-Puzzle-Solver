/**
   @author ROSHAN KOIRALA
   @version 1.0
   @since 04/09/2015
  
   A Java Program  that solves the ABC Path Puzzle
 */



// importing required library classes
import java.io.*;
import java.util.Scanner;


/**
   An AbcPuzzle class that implements backtracking solution to correctly place the letters B through 
   Y in a puzzle grid, subject to the constraints given by the clues around the edge of the grid 
 */
public class AbcPuzzle 
{
   
   private char[][] puzzle = new char[7][7];    // A 7 by 7 array of character to maintain the puzzle and clues


 /**
   The constructor  initializes the puzzle array with the data stored in the file given as argument
   @param filename The name of the file containing the edge clue data to be stored in the puzzle 
  */
   public AbcPuzzle (String filename) 
   {
      try
      {
         Scanner scan = new Scanner ( new File (filename));
      
         for ( int i = 0 ; i <7; i++)
         {
            String aLine= scan.nextLine();
         
            if(i==0 || i == 6)    // Intializes the first and last row of the arrays with clues
            
               for( int j = 0; j<aLine.length(); j++)
                  puzzle[i][j]= aLine.charAt(j);
                        
            else {           // Intializes the rest of the arrays with the clues
               puzzle[i][0]  = aLine.charAt(0);
               puzzle[i][6] = aLine. charAt(1);   
            }
         }
         
         
         int a = scan.nextInt();    // Places the first character in the array
         int b = scan.nextInt();
         puzzle[a][b] = 'A'; 
      
         solvePuzzle(a,b);     // A method call to solve the problem starting with the current position
      }
      
      catch (IOException e)
      {
         System.out.println(e.toString());
      }
   }
  
   
  /**
   Fills letter B through Y as per the clue around the edges of the grid using recursion
   @param a The row number of current character in the grid
   @param b The column number of current character in the grid
  */
  
   
   private void solvePuzzle(int a, int b)
   {
      for ( int x = a-1 ; x<= a+1 ; x++)
         for ( int y = b-1; y <= b+1; y++)
            if(!(x==a && y==b))             // Avoids the current position to be checked for promising
               if(promising ( x, y, a, b ))
               {
                  puzzle [x][y] = (char)((int)(puzzle[a][b]+1));   //Puts the next character in next promising position
                  solvePuzzle(x,y);                       // Recusive call to solve the puzzle from new current positon
               }
              
      if( ! puzzleContainsY())      // Checks to see if the puzzle is successfully solved
         puzzle[a][b] = '\0' ;      // Deletes the character in the current position if none of the new positions (children) are promising
   }
   
  
  /**
   Checks if the new position is empty and satisfies the clue around the edges of the grid.
   @param x The row number of the new position in the grid
   @param y The  column number of the new position in the grid
   @param a The row number of current character in the grid
   @param b The column number of current character in the grid
   @return True if the new position is promising, false otherwise
  */
   private boolean promising (int x , int y, int a ,int b)
   {
      boolean result = false;
      char nextCharacter = (char)((int)(puzzle[a][b]+1));
   
      if ( puzzle[x][y] == '\0')
      {    
          // Checks to see if the next character matches with the alphabets around the edges ( vertical, horizontal, and diagonal)     
         if( x==y  && ( nextCharacter==puzzle[0][y] || nextCharacter==puzzle[6][y]||nextCharacter==puzzle[x][0]
             || nextCharacter==puzzle[x][6]|| nextCharacter==puzzle[0][0]||nextCharacter==puzzle[6][6]) )
            
            result= true;
         
         if( x+y==6  && ( nextCharacter==puzzle[0][y] || nextCharacter==puzzle[6][y]||nextCharacter==puzzle[x][0]
              || nextCharacter==puzzle[x][6]|| nextCharacter==puzzle[6][0]||nextCharacter==puzzle[0][6]) )
            
            result= true;
            
         if( nextCharacter==puzzle[0][y] || nextCharacter==puzzle[6][y]||nextCharacter==puzzle[x][0] 
            || nextCharacter==puzzle[x][6])
            
            result = true; 
      }
      return result; 
   }
   
  /**
   Checks if the puzzle grid contains the letter Y or not 
   return True is the puzzle grid  contains the letter Y, false otherwise
  */
   private boolean puzzleContainsY()
   {
      for ( int i = 1 ; i<(puzzle.length -1); i++)
         for ( int j = 1 ; j <(puzzle[0].length-1); j++)
         {
            if (puzzle[i][j] == 'Y')
               return true;
         }
      return false;
   }
   
  /**
   Displays the solution as a completed grid 
  */

   public void display()
   {
      for(int r=1; r<(puzzle.length-1); r++) 
      {
         for(int c=1; c<(puzzle[r].length-1); c++)
            System.out.print(puzzle[r][c] + " ");
         System.out.println();
      } 
   }
  
  /**
   Returns the solution as a string representing the contents of the grid in row-major order 
   return String that contains characters of the puzzle grid
  */
 
   public String toString()
   {
      String allInOne="";
      for(int r=1; r<(puzzle.length-1); r++) 
      {
         for(int c=1; c<(puzzle[r].length-1); c++)
            allInOne += puzzle[r][c];
      } 
      return allInOne;
   }

}   // End class