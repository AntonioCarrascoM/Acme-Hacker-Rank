
package controllers.actor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import security.Authority;
import services.ActorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import domain.Actor;
import domain.Administrator;
import domain.Company;
import domain.Hacker;

@Controller
@RequestMapping("/download")
public class DownloadController {

	@Autowired
	ActorService				actorService;

	private static final String	APPLICATION_PDF	= "application/pdf";


	@RequestMapping(value = "/myPersonalData", method = RequestMethod.GET, produces = DownloadController.APPLICATION_PDF)
	public @ResponseBody
	void downloadMyPersonalData(final HttpServletResponse response) throws IOException, DocumentException {

		final File file = new File("MyPersonalData.pdf");
		final FileOutputStream fileout = new FileOutputStream(file);
		final Document document = new Document();
		PdfWriter.getInstance(document, fileout);
		document.addAuthor("Actor");
		document.addTitle("My personal data");
		document.open();

		final Actor principal = this.actorService.findByPrincipal();

		final Company company = new Company();
		final Authority authCompany = new Authority();
		authCompany.setAuthority(Authority.COMPANY);

		final Hacker hacker = new Hacker();
		final Authority authHacker = new Authority();
		authHacker.setAuthority(Authority.HACKER);

		final Administrator admin = new Administrator();
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		final ObjectMapper mapper = new ObjectMapper();

		final Paragraph paragraph = new Paragraph();
		paragraph.add(principal.toString());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		if (principal != null)
			if (principal.getUserAccount().getAuthorities().contains(authCompany)) {
				final Company companyPrincipal = (Company) this.actorService.findByPrincipal();
				company.setName(companyPrincipal.getName());
				company.setSurnames(companyPrincipal.getSurnames());
				company.setVatNumber(companyPrincipal.getVatNumber());
				company.setCreditCard(companyPrincipal.getCreditCard());
				company.setPhoto(companyPrincipal.getPhoto());
				company.setEmail(companyPrincipal.getEmail());
				company.setPhone(companyPrincipal.getPhone());
				company.setAddress(companyPrincipal.getAddress());
				company.setCommercialName(companyPrincipal.getCommercialName());
				final String json = mapper.writeValueAsString(companyPrincipal);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authHacker)) {
				final Hacker hackerPrincipal = (Hacker) this.actorService.findByPrincipal();
				hacker.setName(hackerPrincipal.getName());
				hacker.setSurnames(hackerPrincipal.getSurnames());
				hacker.setVatNumber(hackerPrincipal.getVatNumber());
				hacker.setCreditCard(hackerPrincipal.getCreditCard());
				hacker.setPhoto(hackerPrincipal.getPhoto());
				hacker.setEmail(hackerPrincipal.getEmail());
				hacker.setPhone(hackerPrincipal.getPhone());
				hacker.setAddress(hackerPrincipal.getAddress());
				final String json = mapper.writeValueAsString(hacker);
				paragraph.add(json);
			} else if (principal.getUserAccount().getAuthorities().contains(authAdmin)) {
				final Administrator adminPrincipal = (Administrator) this.actorService.findByPrincipal();
				admin.setName(adminPrincipal.getName());
				admin.setSurnames(adminPrincipal.getSurnames());
				admin.setVatNumber(adminPrincipal.getVatNumber());
				admin.setCreditCard(adminPrincipal.getCreditCard());
				admin.setPhoto(adminPrincipal.getPhoto());
				admin.setEmail(adminPrincipal.getEmail());
				admin.setPhone(adminPrincipal.getPhone());
				admin.setAddress(adminPrincipal.getAddress());
				final String json = mapper.writeValueAsString(admin);
				paragraph.add(json);
			}

		document.add(paragraph);
		document.close();

		final InputStream in = new FileInputStream(file);

		response.setContentType(DownloadController.APPLICATION_PDF);
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		response.setHeader("Content-Length", String.valueOf(file.length()));

		FileCopyUtils.copy(in, response.getOutputStream());

	}

}
