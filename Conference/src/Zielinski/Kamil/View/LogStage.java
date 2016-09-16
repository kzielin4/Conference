package Zielinski.Kamil.View;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogStage extends Stage
{
	// private Location bottomRight;
	private double X;
	private double Y;

	public LogStage(Pane ap, StageStyle style)
	{
		initStyle(style);

		setSize(ap.getPrefWidth(), ap.getPrefHeight());

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		double x = screenBounds.getMinX() + screenBounds.getWidth() - ap.getPrefWidth() - 2;
		double y = screenBounds.getMinY() + screenBounds.getHeight() - ap.getPrefHeight() - 2;

		// bottomRight = Location.at(x,y);
		X = x;
		Y = y;
	}

	public void setSize(double width, double height)
	{
		setWidth(width);
		setHeight(height);
	}
	public void setLocation(double x, double y)
	{
		setX(x);
		setY(y);
	}

}
