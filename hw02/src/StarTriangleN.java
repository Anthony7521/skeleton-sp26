public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      // TODO: Fill in this function
      for(int row=1;row<=N;row++){
         for(int blank=0;blank<=N-row;blank++){
            IO.print(" ");
         }
         for(int star=0;star<=row;star++){
            IO.print("*");
         }
         IO.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}