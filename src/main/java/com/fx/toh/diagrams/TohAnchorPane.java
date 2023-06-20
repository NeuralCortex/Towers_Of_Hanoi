package com.fx.toh.diagrams;

import com.fx.toh.pojo.DiskPOJO;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author pscha
 */
public class TohAnchorPane extends AnchorPane {

    private final int offsetY = 50;
    private List<List<DiskPOJO>> stateList;
    private List<Color> colorList;
    private int stackSize = 3;
    private final ResourceBundle bundle;
    private final Font font = new Font("Arial", 20.0);

    public TohAnchorPane(ResourceBundle bundle) {
        setMinSize(0, 0);
        widthProperty().addListener(e -> redraw());
        heightProperty().addListener(e -> redraw());
        this.bundle = bundle;
    }

    public void redraw() {
        clear();
        drawTowers();
        if (stateList != null) {
            drawDisks();
        }
    }

    private void clear() {
        Rectangle rectangle = new Rectangle(getWidth(), getHeight());
        rectangle.setFill(Color.WHITE);
        getChildren().clear();
        getChildren().add(rectangle);
    }

    private void drawTowers() {
        double startX = 0;
        double startY = getHeight() - offsetY;
        double endX = getWidth();
        double endY = startY;

        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2.0f);

        getChildren().add(line);

        for (int i = 0; i < 4; i++) {
            startX = getWidth() * (i / 4.0f);
            startY = getHeight() - offsetY;
            endX = startX;
            endY = 0;

            line = new Line(startX, startY, endX, endY);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2.0f);

            Text text = new Text(bundle.getString("tower") + ": " + (i - 1));
            text.setTextOrigin(VPos.CENTER);
            text.setFont(font);

            Bounds bounds = text.getLayoutBounds();

            text.setX(startX - bounds.getWidth() / 2.0f);
            text.setY(getHeight() - offsetY + bounds.getHeight());

            if (i > 0) {
                getChildren().addAll(line, text);
            }
        }
    }

    private void drawDisks() {
        for (int tower = 0; tower < stateList.size(); tower++) {
            for (int disk = 0; disk < stateList.get(tower).size(); disk++) {
                DiskPOJO diskPOJO = stateList.get(tower).get(disk);
                int desc = diskPOJO.getDesc();
                if (desc - 1 > colorList.size()) {
                    initColorList(stackSize);
                }
                Color color = colorList.get(desc - 1);
                double w = getWidth() * (1.0f / 4.0f) - (stackSize - desc) * 20;
                double h = (getHeight() - offsetY) / 10.0f;
                stackRect(tower + 1, disk, desc, w, h, color);
            }
        }
    }

    public void initColorList(int size) {
        colorList = new ArrayList<>();
        for (int i = 0; i < size + 1; i++) {
            double r = Math.random() * 255;
            double g = Math.random() * 255;
            double b = Math.random() * 255;
            colorList.add(Color.rgb((int) r, (int) g, (int) b));
        }
    }

    public void stackRect(int tower, int pos, int desc, double width, double height, Color color) {
        double middle = getWidth() * tower / 4.0f;
        double x = middle - width / 2.0f;
        double y = getHeight() - offsetY - (pos * height) - height;

        Rectangle rectangle = new Rectangle(x, y, width, height);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2.0f);
        getChildren().add(rectangle);

        Text text = new Text(desc + "");
        text.setFont(font);
        text.setFill(Color.WHITE);
        text.setTextOrigin(VPos.CENTER);

        Bounds bounds = text.getLayoutBounds();

        x = middle - bounds.getWidth() / 2.0f;
        y = getHeight() - offsetY - (pos * height + height / 2.0f);

        text.setX(x);
        text.setY(y);

        getChildren().add(text);
    }

    public void setStateList(List<List<DiskPOJO>> stateList, int stackSize) {
        this.stateList = stateList;
        this.stackSize = stackSize;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }
}
