import javax.swing.JColorChooser;
import javax.swing.JFrame;
import java.awt.Color;

public class Steganography {

  public static void main(String[] args)
  {
    /*
    Color pickedColor = ColorChooser.pickAColor();
    System.out.println(pickedColor);
    */

    /*
    Picture beach = new Picture ("beach.jpg");
    beach.explore();
    Picture copy = testClearLow(beach);
    copy.explore();
    */

    Picture beach2 = new Picture("/workspace/SteganographyLab/beach.jpg");
    beach2.explore();
    Picture copy2 = testSetLow(beach2, Color.PINK);
    copy2.explore();

    Picture copy3 = revealPicture(copy2);
    copy3.explore();
  }

  //clear lower (rightmost) two bits in a pixel
  public static void clearLow(Pixel p)
  {
    int num = 4;
    
    p.setRed(p.getRed() / num);
    p.setRed(p.getRed() * num);

    p.setGreen(p.getGreen() / num);
    p.setGreen(p.getGreen() * num);

    p.setBlue(p.getBlue() / num);
    p.setBlue(p.getBlue() * num);
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
        //setLow(pixels[i][j], col);

        pixels[i][j].setRed(64 * (col.getRed() % 4));
        pixels[i][j].setGreen(64 * (col.getGreen() % 4));
        pixels[i][j].setBlue(64 * (col.getBlue() % 4));

      }
    }
    return copy;
  }
  
  
}
