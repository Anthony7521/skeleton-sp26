public class DoubleUp {
   /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
   public static String doubleUp(String s) {
      // TODO: Fill in this function
      int length = s.length();
      String s0 = "";
      for (int i = 0; i < length; i++) {
         char letter = s.charAt(i);
         s0 += letter;
         s0 += letter;
      }
      // the  better command shows as follows:
      // s0 += "" + letter+ letter
      // the way    "" + char(or string)   will make it more brief.
      return s0;
   }
   public static void main(String[] args) {
      String s = doubleUp("hello");
      System.out.println(s);
      
      System.out.println(doubleUp("cat"));
   }
}