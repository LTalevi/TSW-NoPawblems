package control.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.immagine.Immagine;
import model.immagine.ImmagineDAO;

@WebServlet("/admin/GestioneImmaginiServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class GestioneImmaginiAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIR = "images" + File.separator + "prodotti";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProdotto = request.getParameter("idProdotto");
        request.setAttribute("idProdotto", idProdotto);
        request.getRequestDispatcher("/admin/uploadImmagine.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProdottoParam = request.getParameter("idProdotto");
        String alt = request.getParameter("alt");
        
        if (idProdottoParam == null || idProdottoParam.trim().isEmpty()) {
            request.getSession().setAttribute("error", "ID Prodotto mancante.");
            response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet");
            return;
        }

        long idProdotto = Long.parseLong(idProdottoParam);
        ImmagineDAO immagineDAO = new ImmagineDAO();
        List<String> errorList = new ArrayList<>();
        int fileCaricati = 0;

        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + UPLOAD_DIR;
        
        File uploadDir = new File(savePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try {
            for (Part part : request.getParts()) {
                if (part.getName().equals("immagine") && part.getSize() > 0) {

                    String contentType = part.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        errorList.add("Il file '" + part.getSubmittedFileName() + "' non è un'immagine valida.");
                        continue;
                    }

                    String originalName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String fileExtension = "";
                    int dotIndex = originalName.lastIndexOf('.');
                    if (dotIndex > 0) {
                        fileExtension = originalName.substring(dotIndex);
                    }

                    String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

                    File fileTarget = new File(uploadDir, uniqueFileName);
                    try (InputStream input = part.getInputStream()) {
                        Files.copy(input, fileTarget.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }

                    String urlDatabase = "images/prodotti/" + uniqueFileName;
                    
                    Immagine nuovaImmagine = new Immagine();
                    nuovaImmagine.setProdotto(idProdotto);
                    nuovaImmagine.setUrl(urlDatabase);  
                    nuovaImmagine.setAlt(alt);

                    immagineDAO.doSave(nuovaImmagine);
                    fileCaricati++;
                }
            }

            if (fileCaricati > 0) {
                request.getSession().setAttribute("success", "Caricate con successo " + fileCaricati + " immagini per il prodotto!");
            } else if (errorList.isEmpty()) {
                request.getSession().setAttribute("error", "Nessun file selezionato per l'upload.");
            }

            if (!errorList.isEmpty()) {
                request.getSession().setAttribute("validationErrors", errorList);
            }

        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Errore critico durante il caricamento: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/GestioneProdottiAdminServlet?action=visualizza");
    }
}