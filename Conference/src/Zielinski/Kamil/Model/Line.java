package Zielinski.Kamil.Model;

import Zielinski.Kamil.Model.MinLines.LineType;

public class Line
{
	int lineIndex;
	LineType lineType;
	int zeroCounter;

	public Line(int lineIndex, LineType lineType , int maxZero)
	{
		this.lineIndex = lineIndex;
		this.lineType = lineType;
		this.zeroCounter = maxZero;
	}

	public LineType getLineType()
	{
		return lineType;
	}

	public int getLineIndex()
	{
		return lineIndex;
	}

	public boolean isHorizontal()
	{
		return lineType == LineType.HORIZONTAL;
	}

	public int getZeroCounter()
	{
		return zeroCounter;
	}
}
