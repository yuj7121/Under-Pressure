import java.awt.Image;
import java.awt.Graphics;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * <h1> Person </h1>
 * This is the Person class.
 * A person can move left or right and paint itself on a graphic. 
 * It can also fire bullets and check if its bullet hit someone. 
 * <p>
 * 
 * Version 1 - 30 mintues, Yujin, May 26th, 2022
 * Wrote the basic framework including the constrcutor, getX, move, and draw methods. 
 *
 * Version 2 - 1 hour, Yujin, May 31st, 2022
 * Made the person check if its bullet hit somebody
 * 
 * Version 3 - 1 hour Yujin, June 3rd, 2022
 * Made the moveBullet method that will move the bullet up or down depending on the type
 * And added the bullet end method that pre-determines if further collision check is necessary
 *
 * @author Andrew Jamieson, Nicole Poroshenko, and Yujin Bae
 * @version 06.03.22
 * @since 2022-05-26
 * ICS4U0
 * Teacher: Ms. Krasteva
 * </p>
 */

public class Person{
   /** This int variable hold the x coordinate of the person */
   private int x;
   /** This int variable hold the y coordinate of the person */
   final private int y;
   /** This int variable stores the type of the person 1: player, 2: good, 3: bad */
   final private int t;
   /** This image variable stores the image of the person */
   final private Image img;
   /** This Bullet variable stores the current bullet fired by the person */
   final private Bullet bullet;
   
    
   /**
   * The constructor of the Person class where the base of the person constructed,
   * This method initializes variables using parameters taken in
   * It also takes in the type of the person and initializes img according to it. 
   *
   * @param t This is if the person is good or bad
   * @param x, this is the starting x coordinate of the person
   */
   public Person(int t, int x){
      this.t = t;
      this.x = x;
      if(t == 1){
         y = 100;
      } else {
         y = 500;
      }
      bullet = new Bullet(t);
      Image temp = null;
      try {
         switch(t){
            case 1:
               temp = ImageIO.read(new File("files/Lv2Character.png"));
               break;
            case 2:
               temp = ImageIO.read(new File("files/Lv2GoodPerson.png"));
               break;
            case 3:
               temp = ImageIO.read(new File("files/Lv2BadPerson.png"));
               break;
         }
      } catch(IOException e){
         JOptionPane.showMessageDialog(null, "Error loading in image", "Under Pressure", JOptionPane.WARNING_MESSAGE);
      }
      img = temp;
   }
   
   /**
   * This method will take in a graphic and then draw the person on that graphic
   * 
   * @param g This is the graphics we want to draw the person on
   */
   public void draw (Graphics g){
      bullet.draw(g);
      g.drawImage(img, x, y, null);
   }
   
   /**
   *This method will return the x coordinate of the person
   *
   * @return This is the x coordinate
   */
   public int getX(){
      return x;
   }
   
   /**
   *This method will return the type of the person
   *
   * @return This is the type of the person
   */
   public int getT(){
      return t;
   }
   
   /**
    * This method will make sure that this person's bullet's y coordinate 
    * is at either end of the screen where it can hit someone. 
    * In other words, it checks if the bullet and the target (s)' y coordinates intersect. 
    * The target is all non players if this person is a player, 
    * Or the target is the player if this person is not a player. 
    *
    * @return This is whether the bullet and target's y coordinate intersect or not
    */
    public boolean bulletAtEnd(){
      int b = bullet.getY();
      if(t == 1){
         return b + 107 - 10 > 500 && b < 500 + 120 - 10;
      } else {
         return b + 107 - 10 > 100 && b < 100 + 120 - 10;
      }
    }
    
   /**
    * This method will check if the person's bullet hit someone. 
    * It is assumed that the y coordinates of the person and the bullet intersect. 
    *
    * @param p This is the array of all the target persons we want to check for collision
    * @return This is the person the bullet hit
    */
   public boolean checkCollision(Person p){
      return bullet.checkCollision(p.x);
   }
   
   /**
    * This method will make the person fire their bullet
    */
   public void fire(){
      bullet.fire(x);
   }
   
   /**
   * This method will move the x coordinate of the person to the left or right
   * @param left This is the boolean variable indicating if it should move left. If false, go to right
   */
   public void move(boolean left){
      if (left){
         x -= 3;
      } else {
         x += 3;
      }
   }
   
   /**
    * This method will move the bullet unless it is out of the screen. 
    * If it's out of screen, it will fire a bullet at a random time
    */
   public void moveBullet(){
      if(bullet.getY() > -100 && bullet.getY() < 900){
         bullet.move();
      } else {
         if(Math.random() < 0.01 && t != 1){
            fire();
         }
      }
   }
}
