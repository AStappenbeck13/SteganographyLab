import javax.swing.JColorChooser;
import javax.swing.JFrame;

import javafx.scene.effect.Light.Point;

import java.awt.Color;
import java.util.ArrayList;

public class Steganography {

  public static void main(String[] args)
  {
    //Uncomment each activity separately to run it
    //Only check activity 1, 2, & 3

    //start of activity 1
    /*
    Color pickedColor = ColorChooser.pickAColor();
    System.out.println(pickedColor);

    Picture beach = new Picture ("beach.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore();

    Picture beach2 = new Picture("beach.jpg");
    beach2.explore();
    Picture copy2 = testSetLow(beach2, Color.PINK);
    copy2.explore();

    Picture copy3 = revealPicture(copy2);
    copy3.explore();
    */
    //end of activity 1

    //start of activity 2
    /*
    Picture picture1 = new Picture("seagull.jpg");
    Picture picture2 = new Picture("whiteFlower.jpg");

    System.out.println(canHide(picture1, picture2));

    picture1.explore();

    
    Picture hidden = picture2;
    Picture combined = hidePicture(picture1, picture2);
    combined.explore();

    Picture revealed = revealPicture(combined);
    revealed.explore();
    */
    //end of activity 2

    //start of activity 3
    
    Picture beach = new Picture("beach.jpg");
    Picture robot = new Picture("robot.jpg");
    Picture flower1 = new Picture("flower1.jpg");
    //beach.explore();

    Picture hidden1 = hidePicture(beach, robot, 65, 208);
    Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
    //hidden2.explore();

    Picture unhidden = revealPicture(hidden2);
    unhidden.explore();

    Picture swan = new Picture("swan.jpg");
    Picture swan2 = new Picture("swan.jpg");
    System.out.println("Swan and swan2 are the same:  " + isSame(swan, swan2));
    
    swan = testClearLow(swan);
    System.out.println("Swan and swan2 are the same (after clearLow run on swan): " + isSame(swan, swan2));
    
    Picture arch = new Picture("arch.jpg");
    Picture arch2 = new Picture("arch.jpg");
    Picture koala = new Picture("koala.jpg");
    Picture robot1 = new Picture("robot.jpg");

    ArrayList<Point> pointList = findDifferences(arch, arch2);
    System.out.println(("PointList after comparing two identical pictures has a size of ") + pointList.size());

    pointList = findDifferences(arch, koala);
    System.out.println(("PointList after comparing two different sized pictures has a size of ") + pointList.size());
    
    arch2 = hidePicture(arch, robot1, 65, 102);
    pointList = findDifferences(arch, arch2);
    System.out.println("PointList after hiding a picture has a size of " + pointList.size());

    Picture hall = new Picture("femaleLionAndHall.jpg");
    Picture robot2 = new Picture("robot.jpg");
    Picture flower2 = new Picture("flower1.jpg");

    Picture hall2 = hidePicture(hall, robot2, 50, 300);
    Picture hall3 = hidePicture(hall2, flower2, 115, 275);
    hall3.explore();
    if(!isSame(hall, hall3))
    {
      Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
      hall4.show();
      Picture unhiddenHall3 = revealPicture(hall3);
      unhiddenHall3.show();
    }
    
    //end of activity 3

    //start of activity 4 - Ignore this part
    /*
    Picture beach = new Picture("beach.jpg");
    //beach.explore();
    String s = "Hello World";
    Picture copy = hideText(beach, s);
    //copy.show();
    //System.out.println(isSame(beach, copy));
    //System.out.println(revealText(copy, s.length()));
    int num = 25;
    int[] bits = getBitPairs(num);
    System.out.println("\nnum: " + num);
    System.out.println("bits[0] =  " + bits[0]);
    System.out.println("bits[1] =  " + bits[1]);
    System.out.println("bits[2] =  " + bits[2]);
    System.out.println(num == (bits[0] * 16 + bits[1] * 4 + bits[2]));

    ArrayList<Integer> nums = new ArrayList<Integer>();
    nums.add(19); //S
    nums.add(17); //Q
    nums.add(27); //Space
    nums.add(3); //C
    nums.add(32); //Nothing
    System.out.println(decodeString(nums));
    */
    //end of activity 4
  }

  public static void clearLow(Pixel p)
  {
    final int NUM = 4;
    
    p.setRed(p.getRed() / NUM);
    p.setRed(p.getRed() * NUM);

    p.setGreen(p.getGreen() / NUM);
    p.setGreen(p.getGreen() * NUM);

    p.setBlue(p.getBlue() / NUM);
    p.setBlue(p.getBlue() * NUM);
  }

  public static Picture testClearLow(Picture p)
  {
    Pixel[][] pixels = p.getPixels2D();
    for (Pixel[] b : pixels)
    {
      for (Pixel a : b)
      {
        clearLow(a);
      }
    }
    return p;
  }


  public static void setLow(Pixel p, Color c)
  {
    clearLow(p);

    int red = c.getRed();
    red /= 64;
    p.setRed(p.getRed() + red);

    int green = c.getGreen();
    green /= 64;
    p.setGreen(p.getGreen() + green);

    int blue = c.getBlue();
    blue /= 64;
    p.setBlue(p.getBlue() + blue);
  }

  public static Picture testSetLow(Picture p, Color c)
  {
    Pixel[][] pixels = p.getPixels2D();
    for (Pixel[] b : pixels)
    {
      for (Pixel a : b)
      {
        setLow(a, c);
      }
    }
    return p;
  }

  public static Picture revealPicture(Picture hidden)
  {
    Picture copy = new Picture(hidden);
    Pixel[][] pixels = copy.getPixels2D();
    Pixel[][] source = hidden.getPixels2D();

    for (int i = 0; i < pixels.length; i++)
    {
      for (int j = 0; j < pixels[i].length; j++)
      {
        Color col = source[i][j].getColor();

        pixels[i][j].setRed((col.getRed() % 4) * 64);
        pixels[i][j].setGreen((col.getGreen() % 4) * 64);
        pixels[i][j].setBlue((col.getBlue() % 4) * 64);

      }
    }
    return copy;
  }

  public static boolean canHide(Picture source, Picture secret)
  {
    if (source.getHeight() >= secret.getHeight() && source.getWidth() >= secret.getWidth())
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public static Picture hidePicture(Picture source, Picture secret)
  {
    Picture combined = new Picture(source);

    Pixel[][] sourcePixels = combined.getPixels2D();
    Pixel[][] secretPixels = secret.getPixels2D();

    for (int i = 0; i < sourcePixels.length; i++)
    {
      for (int j = 0; j < sourcePixels[i].length; j++)
      {
        Color col = secretPixels[i][j].getColor();
        setLow(sourcePixels[i][j], col);
      }
    }

    return combined;
  }

  public static Picture hidePicture(Picture source, Picture secret, int startRow, int startColumn)
  {
    Picture combined = new Picture(source);

    Pixel[][] sourcePixels = combined.getPixels2D();
    Pixel[][] secretPixels = secret.getPixels2D();
    for (int i = startRow; i - startRow < secretPixels.length; i++)
    {
      for (int j = startColumn; j - startColumn < secretPixels[i - startRow].length; j++)
      {
        Color col = secretPixels[i - startRow][j - startColumn].getColor();
        setLow(sourcePixels[i][j], col);
      }
    }


    return combined;
  }

  public static boolean isSame(Picture picture1, Picture picture2)
  {
    if (picture1.getHeight() != picture2.getHeight() || picture1.getWidth() != picture2.getWidth())
    {
      return false;
    }
    Pixel[][] picture1Pixels = picture1.getPixels2D();
    Pixel[][] picture2Pixels = picture2.getPixels2D();

    for (int i = 0; i < picture1Pixels.length; i++)
    {
      for (int j = 0; j < picture1Pixels[i].length; j++)
      {
        Color col1 = picture1Pixels[i][j].getColor();
        Color col2 = picture2Pixels[i][j].getColor();

        if (col1.getRed() != col2.getRed() || col1.getGreen() != col2.getGreen() || col1.getBlue() != col2.getBlue())
        {
          return false;
        }
      }
    }

    return true;
  }

  public static ArrayList<Point> findDifferences(Picture picture1, Picture picture2)
  {
    ArrayList<Point> differences = new ArrayList<Point>();

    Pixel[][] picture1Pixels = picture1.getPixels2D();
    Pixel[][] picture2Pixels = picture2.getPixels2D();
    if (picture1.getHeight() == picture2.getHeight() && picture1.getWidth() == picture2.getWidth())
    {
      for (int i = 0; i < picture1Pixels.length; i++)
      {
        for (int j = 0; j < picture1Pixels[i].length; j++)
        {
          Color col1 = picture1Pixels[i][j].getColor();
          Color col2 = picture2Pixels[i][j].getColor();
  
          if (col1.getRed() != col2.getRed() || col1.getGreen() != col2.getGreen() || col1.getBlue() != col2.getBlue())
          {
            Point p = new Point(i, j, 0, null);
            differences.add(p);
          }
        }
      }
    }


    return differences;
  }

  public static Picture showDifferentArea(Picture picture, ArrayList<Point> pointList)
  {
    Picture copy = new Picture(picture);
    Pixel[][] pixels = copy.getPixels2D();

    int minRow = (int) pointList.get(0).getX();
    int maxRow = (int) pointList.get(0).getX();
    int minCol = (int) pointList.get(0).getY();
    int maxCol = (int) pointList.get(0).getY();

    for (Point a : pointList)
    {
      if ((int) a.getX() < minRow)
      {
        minRow = (int) a.getX();
      }
      if ((int) a.getX() > maxRow)
      {
        maxRow = (int) a.getX();
      }
      if ((int) a.getY() < minCol)
      {
        minCol = (int) a.getY();
      }
      if ((int) a.getY() > maxCol)
      {
        maxCol = (int) a.getY();
      }
    }

    for (int i = minRow; i <= maxRow; i++)
    {
      for (int j = minCol; j <= maxCol; j++)
      {
        if ((i == minRow || i == maxRow) || (j == minCol || j == maxCol))
        {
          pixels[i][j].setRed(128);
          pixels[i][j].setGreen(0);
          pixels[i][j].setBlue(128);
        }
      }
    }
    return copy;
  }

  public static ArrayList<Integer> encodeString(String s)
  {
    s = s.toUpperCase();
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    ArrayList<Integer> result = new ArrayList<Integer>();

    for (int i = 0; i < s.length(); i++)
    {
      if (s.substring(i, i + 1).equals(" "))
      {
        result.add(27);
      }
      else
      {
        result.add(alpha.indexOf(s.substring(i, i + 1)) + 1);
      }
    }

    result.add(0);
    return result;
  }

  public static String decodeString(ArrayList<Integer> codes)
  {
    String result = "";
    String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    for (int i = 0; i < codes.size(); i++)
    {
      if (codes.get(i) == 27)
      {
        result += " ";
      }
      else if (codes.get(i) < 27 && codes.get(i) >= 0)
      {
        result += alpha.substring(codes.get(i) - 1, codes.get(i));
      }
    }
    return result;
  }

  public static int[] getBitPairs(int num)
  {
    int[] bits = new int[3];
    int code = num;
    for (int i = 0; i < 3; i++)
    {
      bits[i] = code % 4;
      code /= 4;
    }
    return bits;
  }

  public static Picture hideText(Picture source, String s)
  {
    Picture copy = new Picture(source);  
    Pixel[][] sourcePixels = copy.getPixels2D();
    
    ArrayList<Integer> codes = encodeString(s);

    for (int i = 0; i < codes.size(); i++)
    {
      int[] bits = getBitPairs(codes.get(i));

      clearLow(sourcePixels[i][0]); //not technically right but can theoretically work
      sourcePixels[i][0].setRed(sourcePixels[i][0].getRed() + (bits[0] / 64));
      sourcePixels[i][0].setGreen(sourcePixels[i][0].getGreen() + (bits[1] / 64));
      sourcePixels[i][0].setBlue(sourcePixels[i][0].getBlue() + (bits[2] / 64));
    }
    return copy;
  }

  public static String revealText(Picture source, int len)
  {
    Picture copy = new Picture(source);
    Pixel[][] pixels = copy.getPixels2D();

    ArrayList<Integer> codes = new ArrayList<Integer>();

    for (int i = 0; i < len; i++)
    {
      int[] bits = new int[3];
      bits[0] = ((pixels[i][0].getRed() % 4) * 16);
      bits[1] = ((pixels[i][0].getGreen() % 4) * 4);
      bits[2] = (pixels[i][0].getBlue() % 4);
      codes.add(bits[0] + bits[1] + bits[2]);
    }

    for (int x : codes)
    {
      System.out.println(x);
    }

    return (decodeString(codes));
  }
}
