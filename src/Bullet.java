import java.awt.Image;
import java.awt.Graphics;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
/**
 * <h1> Bullet </h1>
 * This class is the Bullet class that represents either a pushing away hand 
 * fired by the player or a speech bubble fired by a non player
 * A Bullet will either move up or down the screen and has x and y coordinates
 * <p>
 *
 * Version 1 - 1 hour, Yujin, May 30th, 2022
 * Set up the class with its local variables and the constructor. 
 * Wrote the get y method, move method, and the draw method
 * 
 * Version 2 - 30 minutes, Yujin, June 1st, 2022
 * Wrote the fire method that sets y coordinate to initial and set x from param
 * 
 * Version 3 - 1 hour, Yujin, June 2nd, 2022
 * Wrote the checkCollision method that will check for collision and move the bullet
 * out of the screen if true. 
 * 
 * Version 4 - 5 minutes, Yujin, June 8th, 2022
 * Made the enemy bullets move slower from the speed of 5 to 3. 
 * 
 * @author Andrew Jamieson, Nicole Poroshenko, and Yujin Bae
 * @version 06.08.222
 * @since 2022-05-30
 * ICS4U0
 * Teacher: Ms. Krasteva
 * </p>
 */
public class Bullet{
   /** This image variable will hold the image of this bullet */
   final private Image img;
   /** This int variable hold the type of the bullet. 1:pushing hand, 2:good, 3:bad */
   final private int t;
   /** This int variable will hold the x coordinate of this bullet */
   private int x;
   /** This int variable will hold the y coordinate of this bullet */
   private int y;
   /** This array of strings stores all the comments a bad bullet can say */
   private static final String[] BAD_COMMENTS = {
   "You're Bad ", "You'll fail ", "No good ", "Not enough ", "Too bad "
      };
   /** This array of strings stores all the comments a good bullet can say */
   private static final String[] GOOD_COMMENTS = {
      "You need rest ", "That's great! ", "I love you ", "You're amazing ", "Doing great " 
   };
   /** This string variable stores the current comment the bullet says */
   private String comment;
   
   /** 
    * This is the constructor class that will create a Bullet object
    * It will take in the type of the bullet
    * and initialize the variables according to it. 
    *
    * @param t This is the type of the bullet. 1: pushing hand, 2: good,  and 3: bad
    */
   public Bullet(int t){
      Image temp = null;
      try{
         switch (t){
            case 1:
               temp = ImageIO.read( new File("files/Lv2Hand.png"));
               break;
            case 2:
               temp = ImageIO.read( new File("files/Lv2SpeechBubble.png"));
               break;
            case 3:
               temp = ImageIO.read( new File("files/Lv2SpeechBubble.png"));
               break;
         }
      } catch (IOException e){
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
      this.t = t;
      comment = "";
      x = -100;
      y = -100;
      img = temp;
   }
   
   /**
    * This method will draw the bullet on a given graphic
    * 
    * @param g This is the graphics that we want to draw our bullet on. 
    */
   public void draw(Graphics g){
      g.drawImage(img, x, y, null);
      String temp = comment;
      int i = temp.indexOf(" ");
      for (int j = 1; i > 0; j++) {
         g.drawString(temp.substring(0, i), x+22, y+16+20*j);
         temp = temp.substring(i+1);
         i = temp.indexOf(" ");
      }
   }
      
   /**
    * This method will return the y coordinate of this bullet
    *
    * @return This is the y coordinate of this bullet
    */
   public int getY(){
      return y;
   }
   
   /** 
    * This method will make the bullet's coordinate either up or down depending on its type
    */
   public void move(){
      if(t == 1){
         y += 5;
      } else {
         y -= 3;
      }
   }
   
   /**
    * This method will set make the bullet's y coordinate go back to 
    * the initial location and set the x coordinate according to parameter
    * so that the bullet can be fired again from where the person is
    *
    * @param startX This is the x coordinate we want the bullet to fire from
    */
   public void fire(int startX){
      x = startX;
      if (t == 1){
         y = 100;
      } else {
         y = 380;
         if(t == 2){
            comment = (GOOD_COMMENTS[((int) (Math.random() * GOOD_COMMENTS.length))]).toString();
         } else {
            comment = (BAD_COMMENTS[(int) (Math.random() * BAD_COMMENTS.length)]).toString();
         }
      }
   }
   
   /**
    * This method will take in an x coordinate of a person and check for collision. 
    * If the bullet hit the person, the bullet is robed fr screen and method returns true.  
    * It is assumed that the y coordinates of the bullet and the person intersect. 
    * 
    * @param px This is the x coordinate of the person we wanna check
    * @return This is whether the bullet hit this person or not
    */
   public boolean checkCollision (int px){
      if (x + 85 + 10 > px && x - 10 < px + 100){
         y = -200;
         return true;
      }
      return false;
   }
}
