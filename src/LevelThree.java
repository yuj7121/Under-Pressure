import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;

/**
 * <h1> LevelThree </h1>
 * This class is the LevelThree class which will run the third level of the game.
 * The user will be able to play this level by clicking their mouse to adjust a slider to 
 * decide how much time to spend studying or playing in various real-world scenarios.
 * <p>
 *
 * Version 1 - 30 minutes, Andrew, May 26th, 2022
 * Created the basic methods for the level
 *
 * Version 2 - 3 hours, Andrew, May 30th, 2022
 * Added instructions and scenarios to the level
 * Animated the moving bar to select how much studying and play is done on each day
 *
 * Version 3 - 4 hours, Nicole, June 1st, 2022
 * Created a timer for the slider to ensure that the program runs in uniform regardless of which 
 * computer is running the program. Also implemented the mentalHealthScore and the gradesScore
 * variables and created many if statements to keep track of where the slidebar is and increase/
 * decrease both mental health/grade scores from there. Started working on making the health bars 
 * in the top right corner move with the scores. 
 *
 * Version 4 - 2 hours, Nicole, June 2nd, 2022
 * Perfected the health bars in the top right corner to ensure that they dont overflow from the sides
 * of the bar in the image. Changed the increments of scores to be a significant amount of pixels, and for
 * easier high score calculation.
 * 
 * @author Nicole Poroshenko, Yujin Bae, and Andrew Jamieson
 * @version 06.02.22
 * @since 2022-05-26
 * ICS4U0
 * Teacher: Ms. Krasteva
 * </p>
 */
 
public class LevelThree extends JPanel implements ActionListener {
   /** This Image variable stores the background image */
   private Image bg;
   /** This Image variable stores the instructions image */
   private Image ins;
   /** This Image variable stores the narrator image */
   private Image narrator;
   /** This Image variable stores the speech bubble image */
   private Image speech;
   /** This integer variable stores where in the level the user is */
   private int i;
   /** This integer variable stores where the slider is */
   private int sliderLocation;
   /** This integer variable stores the user's mental health score */
   private int mentalHealthScore;
   /** This integer variable stores the user's mental health score */
   private int gradesScore;
    /** This integer variable holds the same value as sliderLocation variable, and acts as a copy */
   private int sliderScreenLocation;
   /** This array of strings variable stores the all the texts to display */
   private String[] texts = {
      "This is the third level of Under Pressure and this level will simulate a week of life for a high-school student. Each day, you will be faced with different scenarios in which you will have to stop the slider where, based on your learning, you are doing the right amount of studying and playing. Be careful, if you choose to study too much your mental health will decrease. However, if you choose to play too much, your grades will drop. For each day you must use your mouse and click anywhere on the screen to start the slider and then click again to stop the slider.", 
      "Your friend asks if you want to play basketball after school and hang out at their house after, but you have a math worksheet due tonight.",
      "",
      "",
      "You have to complete your art unit project by tomorrow, but your friends invite you to go to an arcade.",
      "",
      "",
      "You have a computer science quiz tomorrow and a biology mini-assignment also due tomorrow, but your friends ask if you want to play video games.",
      "",
      "",
      "Your crush asks you if you want to go out for dinner, but you have a law test and a chemistry presentation tomorrow.",
      "",
      "",
      "Your friend invites you to a huge party, but you have a math assignment due at midnight."
      };
      /** This boolean variable stores whether or not to move the slider right */
      private boolean moveRight;
   
   /**
   * The constructor of the LevelThree class is where the base of the third level is constructed.
   * Images are imported into variables and other variables are initialized with default values.
   *
   */
   public LevelThree() {
      try{
         ins = ImageIO.read(new File("files/Lv3Instructions.png"));
         bg = ImageIO.read(new File("files/Lv3Background.png"));
         narrator = ImageIO.read(new File("files/Lv1Teacher.png"));
         speech = ImageIO.read(new File("files/Lv1SpeechBubble.png"));
         i = 0;
         mentalHealthScore=0;
         gradesScore=0;
         sliderLocation = 0;
         moveRight = true;

      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
   }
   
   /**
    * This method will be called when repaint() is called. It draws the main menu
    * depending on the input. It also calculates the users health/grades scores depending on
    * where the slider has landed as a result of their click.
    * @param g This is the graphics where the main menu will be drawn
    */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Font font = new Font("Broadway", Font.PLAIN, 25);
      g.setFont(font);
      Color basic = new Color(51, 51, 51);
      Color red = new Color(186, 17, 37);
      g.setColor(basic);
      
      if(i == 0){
         g.drawImage(ins, 0, 0, null);
         g.drawString(texts[i].substring(0,91), 65, 465);
         g.drawString(texts[i].substring(92,183), 45, 500);
         g.drawString(texts[i].substring(184,272), 55, 535);
         g.drawString(texts[i].substring(273,362), 45, 570);
         g.drawString(texts[i].substring(363,453), 35, 605);
         g.drawString(texts[i].substring(454,541), 55, 640);
         g.drawString(texts[i].substring(542), 490, 675);
      }
      
      else if(i % 3 == 1){
         g.drawImage(bg, 0, 0, null);
         g.setColor(Color.blue);
         
         if(gradesScore <=180){
            g.fillRoundRect(840,80,220+gradesScore,70,30,30);
         }
         else{
            g.fillRoundRect(840,80,400,70,30,30);
         }
         if(mentalHealthScore<=180){
            g.fillRoundRect(840,180,220+mentalHealthScore,70,30,30);  
         }
         else{
            g.fillRoundRect(840,180,400,70,30,30);
         }
         
         g.drawImage(narrator, 0, 0, null);
         g.drawImage(speech, 0, 0, null);
         g.setColor(basic);
         moveRight = true;
         sliderLocation = 0;
         
         if(i == 1) {
            g.drawString(texts[i].substring(0,53), 310, 525);
            g.drawString(texts[i].substring(54,108), 310, 555);
            g.drawString(texts[i].substring(109), 310, 585);
         }
         else if(i == 4){
            g.drawString(texts[i].substring(0,45), 310, 525);
            g.drawString(texts[i].substring(46, 95), 310, 555);
            g.drawString(texts[i].substring(96), 310, 585);
         }
         else if(i == 7){
            g.drawString(texts[i].substring(0,47), 310, 525);
            g.drawString(texts[i].substring(48, 99), 310, 555);
            g.drawString(texts[i].substring(100), 310, 585);
         }
         else if(i == 10){
            g.drawString(texts[i].substring(0,53), 310, 525);
            g.drawString(texts[i].substring(54, 106), 310, 555);
            g.drawString(texts[i].substring(107), 310, 585);
         }
         else if(i == 13) {
            g.drawString(texts[i].substring(0,53), 310, 525);
            g.drawString(texts[i].substring(54), 310, 555);
         }
      }
      
      else if(i % 3 == 0) {
         g.setColor(red);
         g.drawImage(bg, 0, 0, null);
         g.fillRect(70 + sliderScreenLocation, 585, 5, 90);
         g.setColor(Color.blue);
         
         if(gradesScore <=180){
            g.fillRoundRect(840,80,220+gradesScore,70,30,30);
         }
         else{
            g.fillRoundRect(840,80,400,70,30,30);
         }
         if(mentalHealthScore<=180){
            g.fillRoundRect(840,180,220+mentalHealthScore,70,30,30);  
         }
         else{
            g.fillRoundRect(840,180,400,70,30,30);
         }
         
         if(sliderLocation > 0 && sliderLocation <= 70){
            gradesScore+=60;
            mentalHealthScore-=40;
         }  
         else if(sliderLocation > 70 && sliderLocation <= 165){
            gradesScore+=50;
            mentalHealthScore-=30;
         } 
         else if(sliderLocation > 165 && sliderLocation <= 270){
            gradesScore+=40;
            mentalHealthScore-=20;
         } 
         else if(sliderLocation > 270 && sliderLocation <= 365){
            gradesScore+=30;
            mentalHealthScore-=10;
         } 
         else if(sliderLocation > 365 && sliderLocation <= 465){
            gradesScore+=20;
         } 
         else if(sliderLocation > 465 && sliderLocation <= 660){
            gradesScore+=10;
            mentalHealthScore+=10;
         } 
         else if(sliderLocation > 660 && sliderLocation <= 755){
            mentalHealthScore+=20;
         }
         else if(sliderLocation > 755 && sliderLocation <= 860){
            gradesScore-=10;
            mentalHealthScore+=30;
         }
         else if(sliderLocation > 860 && sliderLocation <= 960){
            gradesScore-=20;
            mentalHealthScore+=40;
         }
         else if(sliderLocation > 960 && sliderLocation <= 1060){
            gradesScore-=30;
            mentalHealthScore+=50;
         }
         else if(sliderLocation > 1060 && sliderLocation <= 1136){
            gradesScore-=40;
            mentalHealthScore+=60;
         }
         sliderLocation=0;
         
         if(gradesScore <=180){
            g.fillRoundRect(840,80,220+gradesScore,70,30,30);
         }
         else{
            g.fillRoundRect(840,80,400,70,30,30);
         }
         if(mentalHealthScore<=180){
            g.fillRoundRect(840,180,220+mentalHealthScore,70,30,30);  
         }
         else{
            g.fillRoundRect(840,180,400,70,30,30);
         }
      }
      
      else {
         g.setColor(red);
         g.drawImage(bg, 0, 0, null);
         g.setColor(Color.blue);
                  
         if(gradesScore <=180){
            g.fillRoundRect(840,80,220+gradesScore,70,30,30);
         }
         else{
            g.fillRoundRect(840,80,400,70,30,30);
         }
         if(mentalHealthScore<=180){
            g.fillRoundRect(840,180,220+mentalHealthScore,70,30,30);  
         }
         else{
            g.fillRoundRect(840,180,400,70,30,30);
         }
         
         g.fillRect(70 + sliderLocation, 585, 5, 90);
         sliderScreenLocation = sliderLocation;

         if(moveRight == true) {
            sliderLocation += 16;
            if(sliderLocation == 1136)
               moveRight = false;
         } else {
            sliderLocation -= 16;
            if(sliderLocation == 0)
               moveRight = true;
         }
      }
   }

  /**
   * This method retrieves the user's mouse input using a graphic coordinate system
   * and increases the counter variable for each click the user does
   */
   private void getClick(){
      addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            i++;
         }  
      });   
   }
   
  /**
   * This method returns the user's total grade score
   * @return This double holds the user's grades score
   */
   public double getGradesScore(){
      return gradesScore;
   }
   
  /**
   * This method returns the user's total mental health score
   * @return This double holds the user's mental health score
   */
   public double getMentalScore(){
      return mentalHealthScore;
   } 
   
   /**
    * This method implements allows the user to cycle through all of the
    * information slides in level three.
    */  
   public void run() {
      getClick();
      Timer time = new Timer (5, this);
      while(i<16){
         revalidate();
         time.start();
      }
      time.stop();
   }
   
    /**
    * This method implements the ActionListener Class.
    * It will be called when the timer parameter calls this
    * @param e instance of the ActionEvent class
    */
   public void actionPerformed (ActionEvent e) {
      repaint();
   }
}