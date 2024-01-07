import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * <h1> MainMenu </h1>
 * This class is the MainMenu class that will be displayed after the splashscreen.
 * This will take in the user's mouse input and return the driver class whatever that input was. 
 * 
 * <p>
 * Version 1 - 1 hour, Nicole Poroshenko, May 15th, 2022
 * Created the MainMenu display using image imports and error messaging
 * Created the paintComponent class to draw the image
 *
 * Version 2 - 3 hours, Nicole Poroshenko + Yujin Bae, May 18th, 2022
 * Created the getClick method by implemented a Mouse Listener
 * Allowed for clicking of buttons, modifying chosen menu option at certain image coordinates
 * 
 * Version 3 - 1 hour, Nicole Poroshenko, May 19 2022
 * Created a loop to allow for mouse input to run continuously
 * Determined and fixed problem where menu option was not changing despite being at certain coordinates
 *
 * Version 4 - 20 minutes, Yujin, May 20th, 2022
 * Made it come back to the main menu at a mouseclick after seeing instructions or highscore
 * Also changed the int choice to be a boolean for better efficiency
 *
 * Version 5 - 2 hours, Andrew, June 7th 2022
 * Created the highscores and instructions display screen with text.
 * 
 * @author Nicole Poroshenko, Yujin Bae, and Andrew Jamieson
 * @version 06.09.22
 * @since 2022-05-15
 * ICS4U0
 
 * Teacher: Ms. Krasteva
 * </p>
 */
 
public class MainMenu extends JPanel {
   /** This Image variable named image stores the current image of the main menu screen */
   private Image image;
   /** This Image variable stores the background image of the main menu screen */
   final private Image bg;
   /** This Image variable stores the instructions screen image of the main menu screen */
   final private Image ins;
   /** This Image variable stores the high scores screen image of the main menu screen */
   final private Image hs;
   /** This boolean variable named choice stores if the user wants to move on to next level or not*/
   private Boolean choice;
   /** This ArrayList variable named names stores all of the players names */
   private ArrayList <String> names;
   /** This ArrayList variable named scores stores all of the players scores */
   private ArrayList <Double> scores;
      
  /**
   * The constructor of the MainMenu class where the base of the Menu is constructed,
   * initializing variables using parameters
   */
   public MainMenu(){
      choice = null;
      scores = new ArrayList<Double>();
      names = new ArrayList<String>();
      Image b = null;
      Image i = null;
      Image h = null;
      try{
         b = ImageIO.read(new File("files/MainMenuButtons.PNG"));
         i = ImageIO.read(new File("files/Instructions.PNG"));
         h = ImageIO.read(new File("files/HighScores.PNG"));
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
      bg = b;
      ins = i;
      hs = h;
   }
   
  /**
   * This method retrieves the user's mouse input using a graphic coordinate system
   * and changes variables based on the selection. Variables changed include
   * the background image and the boolean choice option
   */
   private void getClick(){
      addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            if(x>0 && y>0 && image!=bg){
               image = bg;
            }
            else if(x>30 && x<320 && y>540 && y<660){
               choice = true;
            }
            else if (x>340 && x<630 && y>540 && y<660){
               image = ins;
            }
            else if (x>650 && x<940 && y>540 && y<660){
               image  = hs;
            }
            else if (x>960 && x<1250 && y>540 && y<660){
               choice = false;
            }
         }  
      });   
   }
   
  /**
   * This method retrieves the user takes input from a file of previous high scores
   * and then adds them to an arrraylist so they can be printed when the user selects
   * the high score option in the menu
   */ 
   private void highScoreInput() {
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
                     JOptionPane.showMessageDialog(null, "Error reading in file", "Under Pressure", JOptionPane.WARNING_MESSAGE); 
                  }
                  scores.add(score);
                  names.add(nextScore.substring(0,j-1));
                  j = 0;
               }
            }  
         }
      } catch (IOException e){
         JOptionPane.showMessageDialog(null, "Error reading in file", "Under Pressure", JOptionPane.WARNING_MESSAGE); 
      } 
   }
   
   /**
    * This method will be called when repaint() is called 
    * It draws the main menu depending on the input
    * It also writes the instructions for the game and displays the 
    * high scores of the game.
    * @param g This is the graphics where the main menu will be drawn
    */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, null);
      if(image == ins) {
         Font font = new Font("Broadway", Font.PLAIN, 25);
         g.setFont(font);
         g.setColor(Color.white);
         g.drawString("Welcome to Under Pressure by Water Pump Games!", 40, 360);
         Font smallFont = new Font("Broadway", Font.PLAIN, 20);
         g.setFont(smallFont);
         g.drawString("Press “PLAY” to play Under Pressure! Levels will be unlocked as you proceed through the game.", 40, 395);
         g.drawString("Level 1: Use your mouse to click through the level and learn a valuable lesson about the causes and effects of", 40, 430);
         g.drawString("academic pressure in high-school students. You will then unlock level 2.", 40, 455);
         g.drawString("Level 2: Move your character from side to side using your arrow keys to escape negative messages from your peers", 40, 490);
         g.drawString("and catch positive ones instead! Use your top & bottom arrow keys to produce a hand that pushes away the people", 40, 515);
         g.drawString("who are giving you negative comments. You die and must restart if you are hit by 3 negative comments. You win", 40, 540);
         g.drawString("by pushing away all the negative people.", 40, 565);
         g.drawString("Level 3: You will be faced with various scenarios that occur regularly in the life of an average high-school student.", 40, 600);
         g.drawString("Using knowledge from previous levels, use your mouse to land the slider between “STUDY” and “PLAY” for each scenario.", 40, 625);
         g.drawString("If you appropriately balance your needs to study and play during the 5 scenarios, you win and will be directed to", 40, 650);
         g.drawString("enter your name for the high scores leaderboard!", 40, 675);
         g.drawString("Instructions / Highscore: Press anywhere to exit and press “EXIT” in the main menu to exit the game.", 40, 710);
      }
      if(image == hs) {
         Font font = new Font("Broadway", Font.PLAIN, 25);
         g.setFont(font);
         g.setColor(Color.white);
         Graphics2D g2 = (Graphics2D) g;
         g2.setStroke(new BasicStroke(10));
         if(scores.size() == 0) {
            g.drawString("No scores available yet, check back later", 365, 505);
         }
         if(scores.size() >= 1) {
            g.drawRect(428, 345, 430, 60);
            g.drawRect(428, 405, 430, 60);
            g.drawLine(568, 345, 568, 465);
            g.drawLine(750, 345, 750, 465);
            g.drawString("Place", 460, 385);
            g.drawString("Player", 615, 385);
            g.drawString("Score", 768, 385);
            g.drawString("1", 488, 445);
            g.drawString(names.get(0), 582, 445);
            g.drawString(scores.get(0)+"", 765, 445);
         }
         if(scores.size() >= 2) {
            g.drawRect(428, 465, 430, 60);
            g.drawLine(568, 465, 568, 525);
            g.drawLine(750, 465, 750, 525);
            g.drawString("2", 488, 505);
            g.drawString(names.get(1), 582, 505);
            g.drawString(scores.get(1)+"", 765, 505);
         }
         if(scores.size() >= 3) {
            g.drawRect(428, 525, 430, 60);
            g.drawLine(568, 525, 568, 585);
            g.drawLine(750, 525, 750, 585);
            g.drawString("3", 488, 565);
            g.drawString(names.get(2), 582, 565);
            g.drawString(scores.get(2)+"", 765, 565);
         }
         if(scores.size() >= 4) {
            g.drawRect(428, 585, 430, 60);
            g.drawLine(568, 585, 568, 645);
            g.drawLine(750, 585, 750, 645);
            g.drawString("4", 488, 625);
            g.drawString(names.get(3), 582, 625);
            g.drawString(scores.get(3)+"", 765, 625);
         }
         if(scores.size() >= 5) {
            g.drawRect(428, 645, 430, 60);
            g.drawLine(568, 645, 568, 705);
            g.drawLine(750, 645, 750, 705);
            g.drawString("5", 488, 685);
            g.drawString(names.get(4), 582, 685);
            g.drawString(scores.get(4)+"", 765, 685);
         }
      }
    }
   
   /**
    * This method implements the main menu graphics while the user has not selected another option
    */ 
   public void run() {
      image = bg;
      getClick();
      highScoreInput();
      while(choice == null){
         revalidate();
         repaint();
      }
   }
      
   /**
    * This method is a getter method which returns the current menu option that the user has clicked
    * @return This boolean variable holds the choice the user has made
    */ 
    public Boolean getChoice(){
      return choice;
    }
}