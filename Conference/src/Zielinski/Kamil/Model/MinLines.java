package Zielinski.Kamil.Model;

import java.util.ArrayList;
import java.util.List;

public class MinLines
{
	enum LineType
	{
		NONE, HORIZONTAL, VERTICAL
	}

	public static boolean isZero(int[] array)
	{
		for (int value : array)
		{
			if (value != 0)
			{
				return false;
			}
		}
		return true;
	}

	public static List<Line> getMinLines(int[][] matrix)
	{
		if (matrix.length != matrix[0].length)
		{
			throw new IllegalArgumentException("Matrix should be square!");
		}

		final int SIZE = matrix.length;
		int[] zeroHorizontal = new int[SIZE];
		int[] zeroVertical = new int[SIZE];

		// Count the number of 0's per row and the number of 0's per column
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
			{
				if (matrix[i][j] == 0)
				{
					zeroHorizontal[i]++;
					zeroVertical[j]++;
				}
			}
		}

		// There should be at must SIZE lines,
		// initialize the list with an initial capacity of SIZE
		List<Line> lines = new ArrayList<Line>(SIZE);

		LineType lastInsertedLineType = LineType.NONE;

		// While there are 0's to count in either rows or colums...
		while (!isZero(zeroHorizontal) && !isZero(zeroVertical))
		{
			// Search the largest count of 0's in both arrays
			int max = -1;
			Line lineWithMostZeros = null;
			for (int i = 0; i < SIZE; i++)
			{
				// If exists another count of 0's equal to "max" but in this one
				// has
				// the same direction as the last added line, then replace it
				// with this
				//
				// The heuristic "fixes" the problem reported by
				// @JustinWyss-Gallifent and @hkrish
				if (zeroHorizontal[i] > max || (zeroHorizontal[i] == max && lastInsertedLineType == LineType.HORIZONTAL))
				{
					lineWithMostZeros = new Line(i, LineType.HORIZONTAL , zeroHorizontal[i]);
					max = zeroHorizontal[i];
				}
			}

			for (int i = 0; i < SIZE; i++)
			{
				// Same as above
				if (zeroVertical[i] > max || (zeroVertical[i] == max && lastInsertedLineType == LineType.VERTICAL))
				{
					lineWithMostZeros = new Line(i, LineType.VERTICAL,zeroVertical[i]);
					max = zeroVertical[i];
				}
			}

			// Delete the 0 count from the line
			if (lineWithMostZeros.isHorizontal())
			{
				zeroHorizontal[lineWithMostZeros.getLineIndex()] = 0;
			}
			else
			{
				zeroVertical[lineWithMostZeros.getLineIndex()] = 0;
			}

			// Once you've found the line (either horizontal or vertical) with
			// the greater 0's count
			// iterate over it's elements and substract the 0's from the other
			// lines
			// Example:
			// 0's x col:
			// [ 0 1 2 3 ] -> 1
			// [ 0 2 0 1 ] -> 2
			// [ 0 4 3 5 ] -> 1
			// [ 0 0 0 7 ] -> 3
			// | | | |
			// v v v v
			// 0's x row: {4} 1 2 0

			// [ X 1 2 3 ] -> 0
			// [ X 2 0 1 ] -> 1
			// [ X 4 3 5 ] -> 0
			// [ X 0 0 7 ] -> 2
			// | | | |
			// v v v v
			// {0} 1 2 0

			int index = lineWithMostZeros.getLineIndex();
			if (lineWithMostZeros.isHorizontal())
			{
				for (int j = 0; j < SIZE; j++)
				{
					if (matrix[index][j] == 0)
					{
						zeroVertical[j]--;
					}
				}
			}
			else
			{
				for (int j = 0; j < SIZE; j++)
				{
					if (matrix[j][index] == 0)
					{
						zeroHorizontal[j]--;
					}
				}
			}

			// Add the line to the list of lines
			lines.add(lineWithMostZeros);
			lastInsertedLineType = lineWithMostZeros.getLineType();
		}
		return lines;
	}
   
	public static void printResult(int[][] matrix, List<Line> lines)
	{
		if (matrix.length != matrix[0].length)
		{
			throw new IllegalArgumentException("Matrix should be square!");
		}

		final int SIZE = matrix.length;
		System.out.println("Before:");
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
			{
				System.out.printf("%d ", matrix[i][j]);
			}
			System.out.println();
		}

		for (Line line : lines)
		{
			for (int i = 0; i < SIZE; i++)
			{
				int index = line.getLineIndex();
				if (line.isHorizontal())
				{
					matrix[index][i] = matrix[index][i] < 0 ? -3 : -1;
				}
				else
				{
					matrix[i][index] = matrix[i][index] < 0 ? -3 : -2;
				}
			}
		}

		System.out.println("\nAfter:");
		for (int i = 0; i < SIZE; i++)
		{
			for (int j = 0; j < SIZE; j++)
			{
				System.out.printf("%s ", matrix[i][j] == -1 ? "-"
						: (matrix[i][j] == -2 ? "|" : (matrix[i][j] == -3 ? "+" : Integer.toString(matrix[i][j]))));
			}
			System.out.println();
		}
	}
}