package Zielinski.Kamil.Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PDFWritter
{
	private Document document;
	protected String content;

	public void TruncateContent(String content)
	{
		this.content = content;
	}

	public PDFWritter()
	{
		super();
		this.document = new Document(PageSize.A4.rotate());
	}

	public void addEmptyLine(int number) throws DocumentException
	{
		for (int i = 0; i < number; i++)
		{
			document.add(new Paragraph(" "));
		}
	}

	public void createPdf(String dest) throws IOException, DocumentException
	{
		PdfWriter.getInstance(document, new FileOutputStream(dest));
		document.open();
	}

	public void write() throws DocumentException
	{
		Paragraph p1;
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
		Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 17, Font.BOLDITALIC);
		Chunk chunk = new Chunk("", chapterFont);
		Chapter chapter = new Chapter(new Paragraph(chunk), 1);
		chapter.setNumberDepth(0);
		p1 = new Paragraph("Conference Harmonogram", paragraphFont);
		p1.setAlignment(Element.ALIGN_CENTER);
		chapter.add(p1);
		document.add(chapter);
	}

	public PdfPTable addCelltoTable(PdfPTable table, ArrayList<String> line)
	{
		for (String string : line)
		{
			PdfPCell cell;
			cell = new PdfPCell();
			cell.addElement(new Paragraph(string));
			table.addCell(cell);
		}
		return table;

	}

	public void addTable(PdfPTable table) throws DocumentException
	{
		document.add(table);
	}

	public void close()
	{
		document.close();
	}

	public void addExtractTitle() throws DocumentException
	{
		Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 17, Font.BOLDITALIC);
		Paragraph p1 = new Paragraph("List of Lectures", paragraphFont);
		p1.setAlignment(Element.ALIGN_CENTER);
		document.add(p1);
	}

	public void addAbstractParagraph(ArrayList<String> fields, String values) throws DocumentException
	{
		Font filedsFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLDITALIC);
		Font valuesFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
		// for (int i = 0; i < fields.size(); ++i)
		// {
		ArrayList<String> line2 = new ArrayList<String>(Arrays.asList(values.split(";")));
		int j = 0;
		for (String string : fields)
		{
			// Chunk c = new Chunk(line1.get(j), filedsFont);
			// Chunk c2 = new Chunk(string, filedsFont);
			Paragraph p2 = new Paragraph("" + fields.get(j) + line2.get(j));
			document.add(p2);
			// System.out.println(""+fields.get(j)+line2.get(j)+" "+j);
			++j;
		}
		addSeparator();
	}
	public void addSeparator() throws DocumentException
	{
		Paragraph p2 = new Paragraph("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		p2.setAlignment(Element.ALIGN_CENTER);
		document.add(p2);
	}
}
