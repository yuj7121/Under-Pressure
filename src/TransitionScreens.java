import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.*;

/**
 * <h1> TransitionScreens </h1>
 * This class is the TransitionScreens class that will keep track of the levels that the user has
 * unlocked and what levels the user wishes to play. The class will take the user's mouse input
 * and will return data to the driver class in order to continue running the game. 
 * 
 * <p>
 * Version 1 - 1.5 hours, Nicole Poroshenko, May 26th, 2022
 * Created the constructor where I initalized variables and imported the image with level options,
 * and made methods such as the mouse tracker, paint component to draw the image, getter method of level 
 * choice variable, and the run method.
 * Must still implement various images and lock them according to what levels the user has already completed.
 * 
 * Version 2 - 1 hour, Nicole Poroshenko, May 27th, 2022
 * Imported the lock and unlock images and altered mouse click setting to only allow the user to click 
 * on unlocked levels, or in other words, levels that they have completed.
 *
 * Version 3 - 30 minutes, Nicole Poroshenko, June 9th 2022
 * Imported the exit image and drew the image on the Jpanel for when the user is on the
 * level picker screen. Also implemented the coordinate tracker for when the user
 * has pressed this exit button.
 *
 * @author Nicole Poroshenko, Yujin Bae, and Andrew Jamieson
 * @version 06.09.22
 * @since 2022-05-26
 * ICS4U0
 
 * Teacher: Ms. Krasteva
 * </p>
 */
 
public class TransitionScreens extends JPanel{
   
   /** This Image variable named levelOptions stores the image of the level options */
   private Image levelOptions;
   /** This Image variable named unlocked stores the image of the unlocked lock */
   private Image unlocked;
   /** This Image variable named locked stores the image of the locked lock */
   private Image locked;
   /** This Image variable named exit stores the image of the exit button */
   private Image exit;
   /** This integer variable named levelChoice stores the choice of the level the user has selected */
   private int levelChoice;
   /** This boolean variable named done1 keeps track of whether or not the user completed level 1 */
   private boolean done1;
   /** This boolean variable named done2 keeps track of whether or not the user completed level 2 */
   private boolean done2;
   
  /**
   * The constructor of the TransitionScreens class where the level screen png is read and
   * other variables are initialized using parameters
   *
   */
   public TransitionScreens(){
      levelChoice = 0;
      try{
         levelOptions = ImageIO.read(new File("files/LevelsScreen.PNG"));
         unlocked = ImageIO.read(new File("files/Unlocked.png"));
         locked = ImageIO.read(new File("files/Locked.png"));
         exit = ImageIO.read(new File("files/Exit.png"));
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
   }
   
  /**
   * This method retrieves the user's mouse input using a graphic coordinate system
   * and changes variables based on the selection. The variable changed is the
   * levelChoice variable option
   */
   private void getClick(){
      addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            int x = e.getX();
            int y = e.getY();
            
            if(x>60 && x<408 && y>400 && y<540){
               levelChoice = 1;
            }
            if(x>465 && x<810 && y>400 && y<540 && done1){
               levelChoice = 2;
            }  
            if(x>870 && x<1220 && y>400 && y<540 && done2){
               levelChoice = 3;
            }
            if(x>930 && x<1220 && y>50 && y<165){
               levelChoice = 4;
            }
         }   
      });
   }
   
   /**
    * This method will be called when repaint() is called 
    * It draws the main menu depending on the input
    * @param g This is the graphics where the main menu will be drawn
    */
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(levelOptions, 0, 0, null);
      
      //for the first level
      g.drawImage(unlocked, 60, 340, null);
      
      //for the second level
      if (done1){
         g.drawImage(unlocked, 440, 340, null);
      } else {
         g.drawImage(locked, 440, 400, null);
      }
      
      //for the second level
      if (done2) {
         g.drawImage(unlocked, 850, 340, null);
      } else {
         g.drawImage(locked, 850, 400, null);
      }
      
      //the exit button
      g.drawImage(exit, 930, 50, null);
    }
      
   /**
    * This method is a getter method which returns the current level choice that the user has selected
    * @return This int variable holds the choice the user made
    */ 
    public int getLevelChoice(){
      return levelChoice;
    }
    
   /**
    * This method implements the main menu graphics while the user has not selected another option
    * @param d1 This is the passed in boolean value of whether the user has finished level 1
    * @param d2 This is the passed in boolean value of whether the user has finished level 2
    */ 
   public void run(boolean d1, boolean d2) {
      getClick();
      done1 = d1;
      done2 = d2;
      while(levelChoice == 0){
         revalidate();
         repaint();
      }
   }
 }