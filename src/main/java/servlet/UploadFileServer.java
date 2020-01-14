package servlet;

import com.mysql.cj.protocol.Security;
//import com.sun.corba.se.spi.activation.TCPPortHelper;
import entity.Poster;
import service.PosterService;
import service.impl.PosterServiceImpl;
import utils.SessionCookieManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/uploadPoster")
@MultipartConfig
public class UploadFileServer extends HttpServlet {


        PosterService posterService;

/**
 * @see javax.servlet.http.HttpServlet#HttpServlet()
 */
        public UploadFileServer() {
            this.posterService = new PosterServiceImpl();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          //  req.setAttribute("session", Sec.checkSession(req, resp));
            getServletConfig()
                    .getServletContext()
                    .getRequestDispatcher(JSPFILES.UPLOAD_FILE.getPath())
                    .forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Part filePart = req.getPart("file");
            String fileName = UUID.randomUUID().toString() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            InputStream fileStream = filePart.getInputStream();
            byte[] bytes = new byte[fileStream.available()];
            fileStream.read(bytes);

            String PathToImages = "/home/int/SoftServe/ProjectOneTomcat/target/ProjectOneTomcat-1.0-SNAPSHOT/posters/";
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(
                            new File(PathToImages + fileName)));
            bos.write(bytes);
            bos.close();

            HttpSession session = req.getSession(false);
            Long userId= SessionCookieManager.getLoginedUser(session).getId();

            Poster poster=new Poster(userId,
                    fileName);

            posterService.save(poster);
            req.setAttribute("fileName", fileName);

            resp.sendRedirect(req.getContextPath() + "/personalCabinet");

        }
    }
