package br.edu.ifpb.pweb2.beans;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Named(value = "pdfBean")
@RequestScoped
public class GerarPdfBean {
	
	private String texto;
	private String titulo;
	
	public String gerarPDF() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, externalContext.getResponseOutputStream());
			
			document.open();
			Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
			Font paragraghFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
			Chunk chunk = new Chunk(titulo, chapterFont);
			Chapter chapter = new Chapter(new Paragraph(chunk), 1);
			chapter.setNumberDepth(0);
			chapter.add(new Paragraph(texto, paragraghFont));
			document.add(chapter);
			document.close();
		} catch (DocumentException | IOException e) {
			facesContext.addMessage(null, new FacesMessage("Erro ao gerar PDF"));
		}		
		
		facesContext.responseComplete();
		return null;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
