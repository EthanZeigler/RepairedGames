import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.StringTokenizer;

























public class ClipsLoader
{
  private static final String SOUND_DIR = "Sounds/";
  private HashMap clipsMap;
  
  public ClipsLoader(String soundsFnm)
  {
    clipsMap = new HashMap();
    loadSoundsFile(soundsFnm);
  }
  
  public ClipsLoader() {
    clipsMap = new HashMap();
  }
  





  private void loadSoundsFile(String soundsFnm)
  {
    String sndsFNm = "Sounds/" + soundsFnm;
    System.out.println("Reading file: " + sndsFNm);
    try {
      InputStream in = getClass().getResourceAsStream(sndsFNm);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      
      String line;

      while ((line = br.readLine()) != null) {
        if (line.length() != 0)
        {
          if (!line.startsWith("//"))
          {

            StringTokenizer tokens = new StringTokenizer(line);
            if (tokens.countTokens() != 2) {
              System.out.println("Wrong no. of arguments for " + line);
            } else {
              String name = tokens.nextToken();
              String fnm = tokens.nextToken();
              load(name, fnm);
            }
          } } }
      br.close();
    }
    catch (IOException e) {
      System.out.println("Error reading file: " + sndsFNm);
      System.exit(1);
    }
  }
  






  public void load(String name, String fnm)
  {
    if (clipsMap.containsKey(name)) {
      System.out.println("Error: " + name + "already stored");
    } else {
      clipsMap.put(name, new ClipInfo(name, fnm));
      System.out.println("-- " + name + "/" + fnm);
    }
  }
  

  public void close(String name)
  {
    ClipInfo ci = (ClipInfo)clipsMap.get(name);
    if (ci == null) {
      System.out.println("Error: " + name + "not stored");
    } else {
      ci.close();
    }
  }
  

  public void play(String name, boolean toLoop)
  {
    ClipInfo ci = (ClipInfo)clipsMap.get(name);
    if (ci == null) {
      System.out.println("Error: " + name + "not stored");
    } else {
      ci.play(toLoop);
    }
  }
  
  public void stop(String name)
  {
    ClipInfo ci = (ClipInfo)clipsMap.get(name);
    if (ci == null) {
      System.out.println("Error: " + name + "not stored");
    } else {
      ci.stop();
    }
  }
  
  public void pause(String name) {
    ClipInfo ci = (ClipInfo)clipsMap.get(name);
    if (ci == null) {
      System.out.println("Error: " + name + "not stored");
    } else {
      ci.pause();
    }
  }
  
  public void resume(String name) {
    ClipInfo ci = (ClipInfo)clipsMap.get(name);
    if (ci == null) {
      System.out.println("Error: " + name + "not stored");
    } else {
      ci.resume();
    }
  }
  




  public void setWatcher(String name, SoundsWatcher sw)
  {
    ClipInfo ci = (ClipInfo)clipsMap.get(name);
    if (ci == null) {
      System.out.println("Error: " + name + "not stored");
    } else {
      ci.setWatcher(sw);
    }
  }
}


/* Location:              /home/ethan/Desktop/AngryMan.jar!/ClipsLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */