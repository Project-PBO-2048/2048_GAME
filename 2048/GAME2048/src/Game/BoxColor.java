package Game;

import java.awt.Color;

public class BoxColor extends AbstractBoxColor {

    @Override
    public Color getColor(int value) {
        Color color;
        switch (value) {
            case 0:
                color = Color.decode("#d6cdc4");
                break;
            case 2:
                color = Color.decode("#eee4da");
                break;
            case 4:
                color = Color.decode("#ece0c8");
                break;
            case 8:
                color = Color.decode("#f2b179");
                break;
            case 16:
                color = Color.decode("#f59563");
                break;
            case 32:
                color = Color.decode("#f77b5e");
                break;
            case 64:
                color = Color.decode("#f65d3b");
                break;
            default:
                color = Color.decode("#edce71");
                break;
        }
        return color;
    }
}

