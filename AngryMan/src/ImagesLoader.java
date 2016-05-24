import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;





































public class ImagesLoader
{
  private static final String IMAGE_DIR = "Images/";
  private HashMap imagesMap;
  private HashMap gNamesMap;
  private GraphicsConfiguration gc;
  
  public ImagesLoader(String fnm)
  {
    initLoader();
    loadImagesFile(fnm);
  }
  
  public ImagesLoader() {
    initLoader();
  }
  
  private void initLoader()
  {
    imagesMap = new HashMap();
    gNamesMap = new HashMap();
    
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
  }
  









  private void loadImagesFile(String fnm)
  {
    String imsFNm = "Images/" + fnm;
    System.out.println("Reading file: " + imsFNm);
    try {
      InputStream in = getClass().getResourceAsStream(imsFNm);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      
      String line;
      
      while ((line = br.readLine()) != null) {
        if (line.length() != 0)
        {
          if (!line.startsWith("//"))
          {
            char ch = Character.toLowerCase(line.charAt(0));
            if (ch == 'o') {
              getFileNameImage(line);
            } else if (ch == 'n') {
              getNumberedImages(line);
            } else if (ch == 's') {
              getStripImages(line);
            } else if (ch == 'g') {
              getGroupImages(line);
            } else
              System.out.println("Do not recognize line: " + line);
          } } }
      br.close();
    }
    catch (IOException e) {
      System.out.println("Error reading file: " + imsFNm);
      System.exit(1);
    }
  }
  





  private void getFileNameImage(String line)
  {
    StringTokenizer tokens = new StringTokenizer(line);
    
    if (tokens.countTokens() != 2) {
      System.out.println("Wrong no. of arguments for " + line);
    } else {
      tokens.nextToken();
      System.out.print("o Line: ");
      loadSingleImage(tokens.nextToken());
    }
  }
  


  public boolean loadSingleImage(String fnm)
  {
    String name = getPrefix(fnm);
    
    if (imagesMap.containsKey(name)) {
      System.out.println("Error: " + name + "already used");
      return false;
    }
    
    BufferedImage bi = loadImage(fnm);
    if (bi != null) {
      ArrayList imsList = new ArrayList();
      imsList.add(bi);
      imagesMap.put(name, imsList);
      System.out.println("  Stored " + name + "/" + fnm);
      return true;
    }
    
    return false;
  }
  

  private String getPrefix(String fnm)
  {
    int posn;
    
    if ((posn = fnm.lastIndexOf(".")) == -1) {
      System.out.println("No prefix found for filename: " + fnm);
      return fnm;
    }
    
    return fnm.substring(0, posn);
  }
  







  private void getNumberedImages(String line)
  {
    StringTokenizer tokens = new StringTokenizer(line);
    
    if (tokens.countTokens() != 3) {
      System.out.println("Wrong no. of arguments for " + line);
    } else {
      tokens.nextToken();
      System.out.print("n Line: ");
      
      String fnm = tokens.nextToken();
      int number = -1;
      try {
        number = Integer.parseInt(tokens.nextToken());
      }
      catch (Exception e) {
        System.out.println("Number is incorrect for " + line);
      }
      loadNumImages(fnm, number);
    }
  }
  






  public int loadNumImages(String fnm, int number)
  {
    String prefix = null;
    String postfix = null;
    int starPosn = fnm.lastIndexOf("*");
    if (starPosn == -1) {
      System.out.println("No '*' in filename: " + fnm);
      prefix = getPrefix(fnm);
    }
    else {
      prefix = fnm.substring(0, starPosn);
      postfix = fnm.substring(starPosn + 1);
    }
    
    if (imagesMap.containsKey(prefix)) {
      System.out.println("Error: " + prefix + "already used");
      return 0;
    }
    
    return loadNumImages(prefix, postfix, number);
  }
  








  private int loadNumImages(String prefix, String postfix, int number)
  {
    ArrayList imsList = new ArrayList();
    int loadCount = 0;
    
    if (number <= 0) {
      System.out.println("Error: Number <= 0: " + number);
      String imFnm = prefix + postfix;
      BufferedImage bi; if ((bi = loadImage(imFnm)) != null) {
        loadCount++;
        imsList.add(bi);
        System.out.println("  Stored " + prefix + "/" + imFnm);
      }
    }
    else {
      System.out.print("  Adding " + prefix + "/" + 
        prefix + "*" + postfix + "... ");
      for (int i = 0; i < number; i++) {
        String imFnm = prefix + i + postfix;
        BufferedImage bi; if ((bi = loadImage(imFnm)) != null) {
          loadCount++;
          imsList.add(bi);
          System.out.print(i + " ");
        }
      }
      System.out.println();
    }
    
    if (loadCount == 0) {
      System.out.println("No images loaded for " + prefix);
    } else {
      imagesMap.put(prefix, imsList);
    }
    return loadCount;
  }
  







  private void getStripImages(String line)
  {
    StringTokenizer tokens = new StringTokenizer(line);
    
    if (tokens.countTokens() != 3) {
      System.out.println("Wrong no. of arguments for " + line);
    } else {
      tokens.nextToken();
      System.out.print("s Line: ");
      
      String fnm = tokens.nextToken();
      int number = -1;
      try {
        number = Integer.parseInt(tokens.nextToken());
      }
      catch (Exception e) {
        System.out.println("Number is incorrect for " + line);
      }
      loadStripImages(fnm, number);
    }
  }
  





  public int loadStripImages(String fnm, int number)
  {
    String name = getPrefix(fnm);
    if (imagesMap.containsKey(name)) {
      System.out.println("Error: " + name + "already used");
      return 0;
    }
    
    BufferedImage[] strip = loadStripImageArray(fnm, number);
    if (strip == null) {
      return 0;
    }
    ArrayList imsList = new ArrayList();
    int loadCount = 0;
    System.out.print("  Adding " + name + "/" + fnm + "... ");
    for (int i = 0; i < strip.length; i++) {
      loadCount++;
      imsList.add(strip[i]);
      System.out.print(i + " ");
    }
    System.out.println();
    
    if (loadCount == 0) {
      System.out.println("No images loaded for " + name);
    } else {
      imagesMap.put(name, imsList);
    }
    return loadCount;
  }
  







  private void getGroupImages(String line)
  {
    StringTokenizer tokens = new StringTokenizer(line);
    
    if (tokens.countTokens() < 3) {
      System.out.println("Wrong no. of arguments for " + line);
    } else {
      tokens.nextToken();
      System.out.print("g Line: ");
      
      String name = tokens.nextToken();
      
      ArrayList fnms = new ArrayList();
      fnms.add(tokens.nextToken());
      while (tokens.hasMoreTokens()) {
        fnms.add(tokens.nextToken());
      }
      loadGroupImages(name, fnms);
    }
  }
  






  public int loadGroupImages(String name, ArrayList fnms)
  {
    if (imagesMap.containsKey(name)) {
      System.out.println("Error: " + name + "already used");
      return 0;
    }
    
    if (fnms.size() == 0) {
      System.out.println("List of filenames is empty");
      return 0;
    }
    

    ArrayList nms = new ArrayList();
    ArrayList imsList = new ArrayList();
    
    int loadCount = 0;
    
    System.out.println("  Adding to " + name + "...");
    System.out.print("  ");
    for (int i = 0; i < fnms.size(); i++) {
      String fnm = (String)fnms.get(i);
      String nm = getPrefix(fnm);
      BufferedImage bi; if ((bi = loadImage(fnm)) != null) {
        loadCount++;
        imsList.add(bi);
        nms.add(nm);
        System.out.print(nm + "/" + fnm + " ");
      }
    }
    System.out.println();
    
    if (loadCount == 0) {
      System.out.println("No images loaded for " + name);
    } else {
      imagesMap.put(name, imsList);
      gNamesMap.put(name, nms);
    }
    
    return loadCount;
  }
  


  public int loadGroupImages(String name, String[] fnms)
  {
    ArrayList al = new ArrayList(Arrays.asList(fnms));
    return loadGroupImages(name, al);
  }
  








  public BufferedImage getImage(String name)
  {
    ArrayList imsList = (ArrayList)imagesMap.get(name);
    if (imsList == null) {
      System.out.println("No image(s) stored under " + name);
      return null;
    }
    

    return (BufferedImage)imsList.get(0);
  }
  







  public BufferedImage getImage(String name, int posn)
  {
    ArrayList imsList = (ArrayList)imagesMap.get(name);
    if (imsList == null) {
      System.out.println("No image(s) stored under " + name);
      return null;
    }
    
    int size = imsList.size();
    if (posn < 0)
    {

      return (BufferedImage)imsList.get(0);
    }
    if (posn >= size)
    {
      int newPosn = posn % size;
      
      return (BufferedImage)imsList.get(newPosn);
    }
    

    return (BufferedImage)imsList.get(posn);
  }
  





  public BufferedImage getImage(String name, String fnmPrefix)
  {
    ArrayList imsList = (ArrayList)imagesMap.get(name);
    if (imsList == null) {
      System.out.println("No image(s) stored under " + name);
      return null;
    }
    
    int posn = getGroupPosition(name, fnmPrefix);
    if (posn < 0)
    {
      return (BufferedImage)imsList.get(0);
    }
    


    return (BufferedImage)imsList.get(posn);
  }
  





  private int getGroupPosition(String name, String fnmPrefix)
  {
    ArrayList groupNames = (ArrayList)gNamesMap.get(name);
    if (groupNames == null) {
      System.out.println("No group names for " + name);
      return -1;
    }
    

    for (int i = 0; i < groupNames.size(); i++) {
      String nm = (String)groupNames.get(i);
      if (nm.equals(fnmPrefix)) {
        return i;
      }
    }
    System.out.println("No " + fnmPrefix + 
      " group name found for " + name);
    return -1;
  }
  



  public ArrayList getImages(String name)
  {
    ArrayList imsList = (ArrayList)imagesMap.get(name);
    if (imsList == null) {
      System.out.println("No image(s) stored under " + name);
      return null;
    }
    
    System.out.println("Returning all images stored under " + name);
    return imsList;
  }
  


  public boolean isLoaded(String name)
  {
    ArrayList imsList = (ArrayList)imagesMap.get(name);
    if (imsList == null)
      return false;
    return true;
  }
  


  public int numImages(String name)
  {
    ArrayList imsList = (ArrayList)imagesMap.get(name);
    if (imsList == null) {
      System.out.println("No image(s) stored under " + name);
      return 0;
    }
    return imsList.size();
  }
  














  public BufferedImage loadImage(String fnm)
  {
    try
    {
      BufferedImage im = ImageIO.read(
        getClass().getResource("Images/" + fnm));
      


      int transparency = im.getColorModel().getTransparency();
      BufferedImage copy = gc.createCompatibleImage(
        im.getWidth(), im.getHeight(), 
        transparency);
      
      Graphics2D g2d = copy.createGraphics();
      




      g2d.drawImage(im, 0, 0, null);
      g2d.dispose();
      return copy;
    }
    catch (IOException e) {
      System.out.println("Load Image error for Images//" + 
        fnm + ":\n" + e); }
    return null;
  }
  


  private void reportTransparency(String fnm, int transparency)
  {
    System.out.print(fnm + " transparency: ");
    switch (transparency) {
    case 1: 
      System.out.println("opaque");
      break;
    case 2: 
      System.out.println("bitmask");
      break;
    case 3: 
      System.out.println("translucent");
      break;
    default: 
      System.out.println("unknown");
    }
    
  }
  




  private BufferedImage loadImage2(String fnm)
  {
    ImageIcon imIcon = new ImageIcon(
      getClass().getResource("Images/" + fnm));
    if (imIcon == null) {
      return null;
    }
    int width = imIcon.getIconWidth();
    int height = imIcon.getIconHeight();
    Image im = imIcon.getImage();
    
    return makeBIM(im, width, height);
  }
  


  private BufferedImage makeBIM(Image im, int width, int height)
  {
    BufferedImage copy = new BufferedImage(width, height, 
      2);
    
    Graphics2D g2d = copy.createGraphics();
    


    g2d.drawImage(im, 0, 0, null);
    g2d.dispose();
    return copy;
  }
  




  public BufferedImage loadImage3(String fnm)
  {
    Image im = readImage(fnm);
    if (im == null) {
      return null;
    }
    int width = im.getWidth(null);
    int height = im.getHeight(null);
    
    return makeBIM(im, width, height);
  }
  


  private Image readImage(String fnm)
  {
    Image image = Toolkit.getDefaultToolkit().getImage(
      getClass().getResource("Images/" + fnm));
    MediaTracker imageTracker = new MediaTracker(new JPanel());
    
    imageTracker.addImage(image, 0);
    try {
      imageTracker.waitForID(0);
    }
    catch (InterruptedException e) {
      return null;
    }
    if (imageTracker.isErrorID(0))
      return null;
    return image;
  }
  







  public BufferedImage[] loadStripImageArray(String fnm, int number)
  {
    if (number <= 0) {
      System.out.println("number <= 0; returning null");
      return null;
    }
    
    BufferedImage stripIm;
    if ((stripIm = loadImage(fnm)) == null) {
      System.out.println("Returning null");
      return null;
    }
    
    int imWidth = stripIm.getWidth() / number;
    int height = stripIm.getHeight();
    int transparency = stripIm.getColorModel().getTransparency();
    
    BufferedImage[] strip = new BufferedImage[number];
    


    for (int i = 0; i < number; i++) {
      strip[i] = gc.createCompatibleImage(imWidth, height, transparency);
      

      Graphics2D stripGC = strip[i].createGraphics();
      


      stripGC.drawImage(stripIm, 
        0, 0, imWidth, height, 
        i * imWidth, 0, i * imWidth + imWidth, height, 
        null);
      stripGC.dispose();
    }
    return strip;
  }
}


/* Location:              /home/ethan/Desktop/AngryMan.jar!/ImagesLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */