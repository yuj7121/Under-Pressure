import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.GraphicsEnvironment;

/**
 * <h1> UnderPressure </h1>
 * This class is the main driver class where everything comes together.
 * This is where we will call all the other classes and actually run the game. 
 * <p>
 * 
 * Version 1 - 1.5 hours, Nicole, May 13th, 2022
 * Made the basic framework and the code to run the splashscreens. 
 * Created the JFrame with default values
 *
 * Version 2 - 1 hour, Yujin Bae, May 17th, 2022
 * Made it to correctly call the new imagefades with the helper method. 
 * 
 * Version 3 - 1 hour, Nicole, May 18th, 2022 
 * Implemented the calls to the MainMenu class and its methods
 *
 * Version 4 - 1.5 hours, Nicole, May 26th 2022
 * Implemented the calls to the TransitionScreens class in order to
 * have transition images between various levels. Also made it so that
 * the user returns to the level option menu after having completed
 * level 1.
 *
 * Version 5 - 30 mins, Nicole, May 27th 2022
 * Implemented the correct calls to the transitionScreens class
 * according to level completion by user
 *
 * Version 6 - 20 mins, Yujin, May 27th 2022
 * Made helper methods for each levels so that levels and splashScreen 
 * objects doesn't just sit there even after being run. 
 * Also wrote in the codes to create and run levelTwo
 *
 * Version 7 - 10 mins, Andrew, May 30th 2022
 * Created Lv3run method to simplify level 3 process
 *
 * Version 8 - 15 mins, Nicole, June 3rd 2022
 * Made a reference to the HighScore class after level 3 to test
 * and see how highScore class is running as a result of running
 * level 3.
 * 
 * Version 9 - 20 minutes, Yujin, June 3rd, 2022
 * Made the runLv2 method return if the user won the level or not
 * so that the done2 variable can be set accordingly. 
 * Also added the gameOver method that will just display a game over screen for now. 
 * 
 * Version 10 - 2 hours, Nicole and Yujin, June 9th, 2022
 * Fixed the main menu and the level picker screens to continue running when the user comes back to it. 
 * Made the helper methods for running the menu and the level picker screen. 
 * Made the gameover method take in a string parameter for the reason why the game is over. 
 *
 * @author Andrew Jamieson, Nicole Poroshenko, and Yujin Bae
 * @version 06.09.22
 * @since 2022-05-13
 * ICS4U0
 * Teacher: Ms. Krasteva
 * </p>
 */
 
public class UnderPressure{
   /** This is the main Jframe used for this game */
   private JFrame frame;
   
   public UnderPressure(){
      frame = new JFrame("Under Pressure");
   }

   /**
    * Initialize the JFrame to be used throughout the entirety of the game
    */
   private void setFrame(){
      frame.setSize(1296, 759);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
      frame.setResizable(false);
      frame.setLocationRelativeTo(null);
   }
   
   /**
    * This helper method will run the splashscreens
    */
   private void runSplash(){
      ImageFades intro = new ImageFades("CompanyLogo.png");
      frame.add(intro);
      intro.run();
      frame.remove(intro);
      ImageFades title = new ImageFades("TitleScreen.png");
      frame.add(title);
      title.run();
      frame.remove(title);
   }
   
   /**
    * This helper method will run the first level for us
    */
   private void runLv1(){
      ImageFades level1Floater = new ImageFades("Lv1Instructions.png");
      frame.add(level1Floater);
      level1Floater.run();
      frame.remove(level1Floater);
      
      LevelOne level1 = new LevelOne();
      frame.add(level1);
      level1.run();
      frame.remove(level1);
   }
   
   /**
    * This helper method will run the second level and return if user won or not
    * @return This boolean variable holds whether the user won the level or not. 
    */
   private boolean runLv2(){
      boolean done;
      ImageFades level2Floater = new ImageFades("Lv2Instructions.png");
      frame.add(level2Floater);
      level2Floater.run();
      frame.remove(level2Floater);
      
      LevelTwo level2 = new LevelTwo();
      frame.add(level2);
      done = level2.run();
      frame.remove(level2);
      if(!done){
         gameOver(" Oh, no! You failed to successfully push away all the bad people from your life. They put so much pressure on you that all your hearts broke. Try again next time. ");
      }
      return done;
   }
   
   /**
    * This helper method will run the third level and the high scores
    */
   private void runLv3(){
      ImageFades level3Floater = new ImageFades("Lv3Instructions.png");
      frame.add(level3Floater);
      level3Floater.run();
      frame.remove(level3Floater);
      
      LevelThree level3 = new LevelThree();
      frame.add(level3);
      level3.run();
      frame.remove(level3);
      
      HighScore highScore = new HighScore(level3.getGradesScore(), level3.getMentalScore());
      frame.add(highScore);
      if (!highScore.run()) {
         frame.remove(highScore);
         gameOver(" Uh oh, you couldn't maintain the healthy balance between academics and your mental health. Now you are too depressed to continue looking for the balance. Try again next time. ");
      } else {
         frame.remove(highScore);
      }
   }
   
   /**
    * This helper method will run the game over screen 
    * @param text This String variable holds the reason why the game is over
    */
   private void gameOver(String text){
      ImageFades gameOver = new ImageFades("GameOver.png", text);
      frame.add(gameOver);
      gameOver.run();
      frame.remove(gameOver);
   }
   
   /**
    * This helper method will run the main menu and return what the choice the user made. 
    * @return This boolean variable holds whether the user chose to play the game or not. 
    */
   private Boolean runMenu(){
      MainMenu menu = new MainMenu();
      frame.add(menu);
      menu.run();
      frame.remove(menu);
      return menu.getChoice();
   }
   
   /**
    * This helper method will run the level picker and return the user's choice. 
    * @param d1 This boolean variable holds if the user completed the first level
    * @param d2 This boolean variable holds if the user completed the second level
    * @return This integer variable holds which choice the user made
    */
   private int runLevelPicker(boolean d1, boolean d2){
      TransitionScreens levelPicker = new TransitionScreens();
      frame.add(levelPicker);
      levelPicker.run(d1, d2);
      frame.remove(levelPicker);
      return levelPicker.getLevelChoice();
   }
   
   /**
    * The run method that implements all the helper methods
    * and acts as the main game runner
    */
   public void run(){
      boolean done1 = false;
      boolean done2 = false;
      
      setFrame();
      runSplash();

      while (runMenu() != false) {
         boolean picking = true;
         while (picking){
            switch (runLevelPicker(done1, done2)){
               case 1:
                  runLv1();
                  done1 = true;
                  break;
               case 2:
                  if (done2){
                     runLv2();
                  } else {
                     done2 = runLv2();
                  }
                  break;
               case 3:
                  runLv3();
                  picking = false;
                  break;
               case 4: 
                  picking = false;
                  break;
            }
         }
      }
      
      runSplash();
      frame.setVisible(false);
      frame.dispose();
   }
   
   /**
    * This is the main method that instantiates the game and runs it. 
    * @param args This is the command line argument
    */
   public static void main(String args[]){
      UnderPressure game = new UnderPressure();
      game.run();
      
   }
}