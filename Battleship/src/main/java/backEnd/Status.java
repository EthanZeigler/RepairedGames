package backEnd;

import java.awt.*;

/**
 * Created by Ethan on 6/1/2015.
 */
public enum Status {
    BLANK (new Color(50, 100, 255)),
    MISS (new Color(0, 0, 0)),
    HIT (new Color(255, 118, 12)),
    SUNK (new Color(200, 0 , 0)),
    //PLACED (new Color(100, 200, 50));
    PLACED (new Color(48, 255, 44));

    private final Color color; // Doesn't need to be private; it's final (can't be changed)
    Status(Color c)
    {
        this.color = c;
    }
    public Color getColor() { return color; }
}
