import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <h1> Level Two </h1>
 * This class is the LevelTwo class.
 * This will run the level 2 game by creating person objects and bullet objects. 
 * <p>
 * 
 * Version 1 - 2.5 hours, Yujin, May 27th, 2022
 * Made the basic framework and the constructor. 
 * Wrote the run, painComponent, and the actionPerformed method
 * Created the good and bad persons and made them move left and right in a loop
 *
 * Version 2 - 2 hour, Yujin, June 2nd, 2022
 * Used keybindings to take in user input to make the player obejct move
 * Also made the basic skeleton for collision check
 * 
 * Version 3 - 30 minutes, Yujin, June 3rd, 2022
 * Completed collision check and made sure the game ends when no more bad persons are left. 
 * Also made the run method return if the user won or not. 
 *
 * Version 4 - 20 mintues, Yujin, June 8th, 2022
 * Made the instructions to show up before the game starts. 
 * Fixed the error that happens if you remove all the good persons
 * Created the gameOver method that dertermines if all the bad persons are removed. 
 *
 * Version 5 - 1 hour, Yujin, June 9th, 2022
 * Fixed the index out of bound exception that happen when checking if there are bad
 * persons left on the screen by changing the for each loop to an ordinary for loop. 
 *
 * @author Andrew Jamieson, Nicole Poroshenko, and Yujin Bae
 * @version 06.09.22
 * @since 2022-05-27
 * 
 * ICS4U0
 * Teacher: Ms. Krasteva
 * </p>
 */

public class LevelTwo extends JPanel implements ActionListener{
   /** This Image variable stores the instructions image */
   private Image ins;
   /** This Image variable stores the background image */
   private Image bg;
   /** This Image variable stores the heart image */
   private Image heart;
   /** This int variable stores the number of lives the user has left */
   private int lives;
   /** This variable stores the arraylist of all the persons */
   private ArrayList<Person> ppl;
   /** This boolean variable holds if the persons are going to the left or not */
   private boolean dir;
   /** This boolean variable holds if the user is still reading the instructions screen. */
   private boolean reading;

   /**
   * This is the constructor of level two
   * It instantiates all the variables
   * An error message will be displayed if the image couldn't be loaded
   */
   public LevelTwo (){
      try{
         ins = ImageIO.read(new File("files/Lv2Instructions.png"));
         bg = ImageIO.read(new File("files/Lv2Background.png"));
         heart = ImageIO.read(new File("files/Lv2Heart.png"));
      }
      catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
      
      /* create all the persons */
      ppl = new ArrayList <Person>();
      ppl.add(new Person(1, 600));
      ppl.add(new Person(3, 100));
      ppl.add(new Person(3, 230));
      ppl.add(new Person(2, 360));
      ppl.add(new Person(3, 490));
      ppl.add(new Person(2, 620));
      ppl.add(new Person(3, 750));
      
      reading = true;
      lives = 3;
      dir = false;
      setFont(new Font("Broadway", Font.PLAIN, 18));
      getKey();
   }
   
   /**
    * This helper method will set and get the key bindings
    */
   private void getKey(){
      ActionMap actionMap = getActionMap();
      InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
      
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
      actionMap.put("Left", new MoveAction(true));

      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
      actionMap.put("Right", new MoveAction(false));
      
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "Fire");
      inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Fire");
      actionMap.put("Fire", new FireAction());
   }
   
   /**
    * This class will handle the left and right movements given by key bindings
    */
   private class MoveAction extends AbstractAction {
      /** This boolean variable holds if the direction is left or not */
      private boolean dir;
      /** 
       * This constructor takes in the direction and assigns it to the local variable dir
       * @param dir This is the direction
       */
      public MoveAction(boolean dir){
         this.dir = dir;
      }
      
      /**
       * This method will perform the action of moving the player
       */
      @Override
      public void actionPerformed(ActionEvent e) {
         if (reading){
            reading = false;
         }
         ppl.get(0).move(dir);
         ppl.get(0).move(dir);
         ppl.get(0).move(dir);
      }
   }
   
   /**
    * This class will handle the firing action
    */
   private class FireAction extends AbstractAction {
      /**
       * This method will perform the firing action of the player
       */
      @Override
      public void actionPerformed(ActionEvent e) {
         if (reading){
            reading = false;
         }
         ppl.get(0).fire();
      }
   }
   
   /** 
    * This class will do the collision checking and remove dead persons
    */
   private void collisionCheck(){
      if (ppl.get(0).bulletAtEnd()){
         for (int i = ppl.size()-1; i > 0; i--){
            if (ppl.get(0).checkCollision(ppl.get(i))){
               ppl.remove(i);
            }
         }
      }
      for (int i = 1; i < ppl.size(); i++){
         Person p = ppl.get(i);
         if(p.bulletAtEnd()){
            if (p.checkCollision(ppl.get(0))){
               if(p.getT() == 2){
                  if(lives<3){
                     lives++;
                  }
               } else {
                  lives--;
               }
            }
         }
      }
   }
   
   /**
    * This method will be called when repaint() is called 
    * It draws the level two screen with all the objects
    * @param g This is the graphics where the main menu will be drawn
    */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      if(reading){
         g.drawImage(ins, 0, 0, null);
         g.drawString("In this level, you will want to push away all the pressuring people from your life", 200, 480);
         g.drawString("while dodging their bad comments to make sure not all your hearts are broken. ", 200, 510);
         g.drawString("Bad comments can make your heart break, while good comments can restore your heart. ", 200, 540);
         g.drawString("Press the left and right arrow keys to make your character move across the screen. ", 200, 570);
         g.drawString("Press either the up or down arrow keys to push away people. ", 200, 600);
         g.drawString("Press any of the arrow keys to continue. ", 460, 650);
      } else {
         g.drawImage(bg, 0, 0, null);
         
         /* draws the lives */
         for(int i = lives; i > 0; i--){
            g.drawImage(heart, 1280-i*80, 20, null);
         }
         
         /* draws the persons */
         for (Person p : ppl){
            p.draw(g);
         }
      }
   }
   
   /**
    * This method implements the ActionListener Class.
    * It moves every person, checks for collsion, and repaints the screen
    * @param e instance of the ActionEvent class
    */
   public void actionPerformed (ActionEvent e) {
      /* if the leftmost or rightmost person goes out of boundaries, reverse dir */
      if (ppl.get(1).getX() < 40 || ppl.get(ppl.size()-1).getX() > 1100 ){
         dir = !dir;
      }
      /* move all the ppl other than the player and their bullets */
      for (int i = 1; i < ppl.size(); i++) {
         ppl.get(i).move(dir);
         ppl.get(i).moveBullet();
      }
      ppl.get(0).moveBullet();
      
      collisionCheck();
      repaint();
   }
   
   /**
    * This helper method determines if the game is over. 
    * The game is over when the user doesn't have any lives left or if there
    * are no more bad persons left on the game screen.  
    * @return This boolean variable is whether the game is over or not. 
    */
   private boolean gameOver(){
      if(lives < 1){
         return true;
      }
      if(ppl.size() <= 1 ){
         return true;
      }
      boolean bad = false;
      for (int i = 1; i < ppl.size(); i++){
         Person p = ppl.get(i);
         if(p != null && p.getT() == 3){
            bad = true;
         }
      }
      if(!bad){
         return true;
      }
      return false;
   }
  
  /**
    * This method will actually run the level two mini game and return if the user won
    * @return This boolean variable is if the user won the game or not
    */  
   public boolean run(){
      while(reading){
         revalidate();
      }
      Timer time = new Timer (15, this);  
      time.start();
      while(true){
         if(gameOver()){
            break;
         }
         revalidate();
      }
      time.stop();
      return lives > 0;
   }
}