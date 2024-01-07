import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * <h1> HighScore </h1>
 * This class is the HighScore class which will take user input for user's name
 * once they successfully complete level 3. It will then add their name and score
 * to an array list and to a database for further reading and writing.
 * <p>
 *
 * Version 1 - 15 minutes, Nicole, June 2nd 2022
 * Created the very basic framework of the class and initalized
 * some variables.
 * 
 * Version 2 - 45 minutes, Nicole, June 3rd 2022
 * Calculated user's score from driver class parameter passing and 
 * determined how this related to their final high score.
 *
 * Version 3 - 15 minutes, Andrew Jamieson, June 3rd 2022
 * Created final score calculation system.
 *
 * Version 4 - 2 hours, Nicole Poroshenko, June 6th 2022
 * Implemented the textbox on the JPanel to receive user names via the
 * JFrame in the driver class. Made the constructTextBox() method,
 * sortArray() method, paintComponent(), and run().
 *
 * Version 5 - 1.5 hours, Andrew Jamieson, June 6th 2022
 * Created a functional file database to hold all scores
 * Updated sorting algorithm to work with the file database
 *
 * Version 6 - 30 minutes, Andrew Jamieson, June 9th 2022
 * Fixed the win screen display
 *
 * @author Nicole Poroshenko, Yujin Bae, and Andrew Jamieson
 * @version 06.09.22
 * @since 2022-06-02
 * ICS4U0
 * Teacher: Ms. Krasteva
 * </p>
 */
 
public class HighScore extends JPanel implements ActionListener{
   /** This Image variable stores the "congratulations" image */
   private Image con;
   /** This Image variable stores the "sorry you lost" image */
   private Image sor;
   /** This double variable stores the user's grade score from level 3 */
   private double gradeScore;
   /** This double variable stores the user's mental health score from level 3 */
   private double mentalHealthScore;
   /** This double variable stores the user's average grade score from all 5 scenarios */
   private double gradeAvgScore;
   /** This double variable stores the user's average mental health score from all 5 scenarios */
   private double mentalAvgScore;
   /** This double variable stores the user's final total score */
   private double finalScore;
   /** This boolean variable stores whether or not the user won level 3 */
   private boolean win;
   /** This String variable stores the user's name */
   private String name;
   /** This JTextField variable holds the text field that will be added to the JPanel */
   public static JTextField textField;
   /** This ArrayList variable stores the names of all the people who won the game */
   private ArrayList <String> names;
   /** This ArrayList variable stores the scores of all the people who won the game */
   private ArrayList <Double> scores;

   /**
    * This is the HighScore constructor that initializes
    * many variables including images and various scores.
    * @param gs This double holds the user's grades
    * @param mhs This double holds the user's mental health score
    */
   public HighScore(double gs, double mhs) {
      try{
         con = ImageIO.read(new File("files/Congratulations.png"));
         sor = ImageIO.read(new File("files/GameOver.png"));
         gradeScore = gs;
         mentalHealthScore = mhs;
         gradeAvgScore = 0.0;
         mentalAvgScore = 0.0;
         finalScore = 0.0;
         win = false;
         name = null;
         textField = new JTextField ("Enter name (max 10 characters)", 15);
         names = new ArrayList<String>(1);
         scores = new ArrayList<Double>(1);
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
   }
   
   /**
    * This is the constructTextBox method where the text 
    * field's bounds, font, and size are initialized
    */
   public void constructTextBox(){
      this.setLayout(null);
      textField.setBounds(820, 312, 360, 80);
      textField.setEditable(true);
      textField.setFont(new Font("Broadway", Font.PLAIN, 20));
      this.add(textField);
      textField.addActionListener(this);
   }
   
   /**
    * This is the calculateScore method that calculates the users
    * average scores and sets a boolean variable win to true
    * if their scores are between 0 and 20
    */
   private void calculateScore(){
      gradeAvgScore = gradeScore / 5.0;
      mentalAvgScore = mentalHealthScore / 5.0;
      if (gradeAvgScore >= 0 && gradeAvgScore <= 20 && mentalAvgScore >= 0 && mentalAvgScore <= 20){
         finalScore = 100.0 - (Math.abs(10.0 - gradeAvgScore) + Math.abs(10.0 - mentalAvgScore)) * 5.0;
         win = true;
      }
   }
   
   /**
    * This is the sortArray method that sorts the arrayList
    * that holds all of the names and scores of all the players
    * and then reads them to a seperate file.
    */
   public void sortArray(){
      double score = 0;
      try {
         Scanner file = new Scanner(new File("files/highScoreDatabase.txt"));
         while (file.hasNext())
         {
            String nextScore = file.nextLine();
            for (int j = nextScore.length()-1; j>0; j--){
               if (nextScore.substring(j-1, j).equals(" ")){
                  String temp = nextScore.substring(j);
                  try{
                     score = Double.parseDouble(temp);
                  } catch(Exception e){
                  }
                  scores.add(score);
                  names.add(nextScore.substring(0,j-1));
                  j=0;
               }
            }
         }
      } catch (Exception e){
      }
      for(int i = 0; i < scores.size(); i++){
         if(finalScore >= scores.get(i)){
            scores.add(i, finalScore);
            names.add(i, name);
            break;
         } else if(finalScore < scores.get(scores.size() - 1)) {
            scores.add(finalScore);
            names.add(name);
            break;
         }
      }
      if(scores.size() == 0) {
         scores.add(finalScore);
         names.add(name);
      }
      try {
         FileWriter store = new FileWriter (new File("files/highScoreDatabase.txt"));
         int size = scores.size();
         for(int i = 0; i < size; i++) {
            store.write(names.get(0) + " " + scores.get(0) + "\n");
            names.remove(0);
            scores.remove(0);
         }
         store.close();
      } catch (Exception e){
      }
   }

      
   /**
    * This method will be called when repaint() is called 
    * It draws the main menu depending on the input
    * @param g This is the graphics where the main menu will be drawn
    */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if(win){
         g.drawImage(con, 0, 0, null);
         g.setFont(new Font("Broadway", Font.PLAIN, 40));
         g.setColor(Color.white);
         g.drawString("Your Score is: ", 135, 335);
         if(finalScore == 100.0) {
            g.drawString(finalScore+"", 220, 385);
         } else if(finalScore == 0.0) {
            g.drawString(finalScore+"", 240, 385);
         } else {
            g.drawString(finalScore+"", 230, 385);
         }
      }
      else{
         g.drawImage(sor, 0, 0, null);
      }
   }
   
   /**
    * This is the run method which runs the rest of the class.
    * @return This Boolean variable holds if the user won the third level or not. 
    */
   public boolean run(){
      calculateScore();
      if(!win){
         return false;
      }
      constructTextBox();
      while (name == null){
         revalidate();
         repaint();
      }
      if(name != null)
      sortArray();
      return true;
   }
   
   /**
    * This method implements the ActionListener Class.
    * @param e instance of the ActionEvent class
    */
   public void actionPerformed (ActionEvent e) {
      name = textField.getText();
      if(name.length() > 10) {
         name = name.substring(0,10);
      }
   }
}