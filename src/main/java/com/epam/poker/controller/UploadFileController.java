package com.epam.poker.controller;

import com.epam.poker.util.constant.Attribute;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.util.tag.AccessTag;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPLOAD_FOLDER = "images/photo";
    private static final String FILE = "file";
    private static final String DEFAULT_PHOTO = "notAva.jpg";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart(FILE);
        String fileLastName = filePart.getSubmittedFileName();
        String fileExt = fileLastName.substring(fileLastName.lastIndexOf("."));
        long userId = Long.parseLong(String.valueOf(request.getSession().getAttribute(Attribute.USER_ID)));
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + fileExt;
        String applicationDir = request.getServletContext().getRealPath("");
        String uploadFileDir = applicationDir + UPLOAD_FOLDER + File.separator;
        File fileDir = new File(uploadFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        for (Part part : request.getParts()) {
            part.write(uploadFileDir + fileName);
        }
        ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
        String responseLine = "";
        try {
            String oldFileName = profilePlayerService.findProfilePlayerById(userId).getPhoto();
            File file = new File(uploadFileDir + oldFileName);
            if (!oldFileName.equals(DEFAULT_PHOTO) && file.delete()) {
                LOGGER.info("Photo deleted: " + oldFileName);
            }
            profilePlayerService.updatePhotoByUserId(userId, fileName);
            request.getSession().setAttribute(Attribute.PHOTO, fileName);
            responseLine = "{\"success\": true}";
        } catch (ServiceException e) {
            responseLine = "{\"success\": false}";
            LOGGER.error("Upload photo error. User id= " + userId + " try upload.");
        }
        response.getWriter().write(responseLine);//todo don't send response
    }
}
