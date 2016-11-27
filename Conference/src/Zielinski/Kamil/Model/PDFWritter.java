package Zielinski.Kamil.Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
		Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
		Chunk chunk = new Chunk("Conference Harmonogram ", chapterFont);
		Chapter chapter = new Chapter(new Paragraph(chunk), 1);
		chapter.setNumberDepth(0);
		p1 = new Paragraph("Conference Harmonogram2", paragraphFont);
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
	/*public void cellLayout(PdfPCell cell, Rectangle position,
            PdfContentByte[] canvases) {
        try {
            BaseFont bf = BaseFont.createFont();
            Font font = new Font(bf, 12);
            float availableWidth = position.getWidth();
            int contentLength = content.length();
            int leftChar = 0;
            int rightChar = contentLength - 1;
            availableWidth -= bf.getWidthPoint("...", 12);
            while (leftChar < contentLength && rightChar != leftChar) {
                availableWidth -= bf.getWidthPoint(content.charAt(leftChar), 12);
                if (availableWidth > 0)
                    leftChar++;
                else
                    break;
                availableWidth -= bf.getWidthPoint(content.charAt(rightChar), 12);
                if (availableWidth > 0)
                    rightChar--;
                else
                    break;
            }
            String newContent = content.substring(0, leftChar) + "..." + content.substring(rightChar);
            PdfContentByte canvas = canvases[PdfPTable.TEXTCANVAS];
            ColumnText ct = new ColumnText(canvas);
            ct.setSimpleColumn(position);
            ct.addElement(new Paragraph(newContent, font));
            ct.go();
        } catch (DocumentException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }*/
}

