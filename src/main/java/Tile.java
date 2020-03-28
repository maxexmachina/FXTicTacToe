import javafx.scene.control.Button;

public class Tile extends Button {
    private boolean isMarked = false;
    private Integer tileMark = -1;

    public Integer getTileMark() {
        return tileMark;
    }

    public void setTileMark(Integer tileMark) {
        this.tileMark = tileMark;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }
}
