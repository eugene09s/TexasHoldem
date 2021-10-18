package com.epam.poker.controller;

import com.epam.poker.exception.ServiceException;
import com.epam.poker.service.database.ProfilePlayerService;
import com.epam.poker.service.database.impl.ProfilePlayerServiceImpl;
import com.epam.poker.util.constant.Attribute;
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
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 11)
public class UploadFileController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
    private static final String UPLOAD_FOLDER = "images/photo";
    private static final List<String> NAME_EXTENSIONS = List.of(".jpeg", ".jpg", ".png", ".gif");
    private static final String FILE = "file";
    private static final String SUCCESS_RESPONSE = "{\"success\": true}";
    private static final String WRONG_RESPONSE = "{\"success\": false}";
    private static final String DEFAULT_PHOTO = "notAva.jpg";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart(FILE);
        String nameOfSendFile = filePart.getSubmittedFileName().toLowerCase(Locale.ROOT);
        boolean isValidFileName = false;
        for (String ext : NAME_EXTENSIONS) {
            if (nameOfSendFile.contains(ext)) {
                isValidFileName = true;
                break;
            }
        }
        String responseLine;
        if (isValidFileName) {
            String fileExt = nameOfSendFile.substring(nameOfSendFile.lastIndexOf("."));
            long userId = Long.parseLong(String.valueOf(request.getSession().getAttribute(Attribute.USER_ID)));
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + String.valueOf(userId) + fileExt;
            String uploadFileDir = request.getServletContext().getRealPath("") + UPLOAD_FOLDER + File.separator;
            for (Part part : request.getParts()) {
                part.write(uploadFileDir + fileName);
            }
            try {
                String oldFileName = profilePlayerService.findProfilePlayerById(userId).getPhoto();
                File file = new File(uploadFileDir + oldFileName);
                if (!oldFileName.equals(DEFAULT_PHOTO)) {
                    file.delete();
                }
                profilePlayerService.updatePhotoByUserId(userId, fileName);
                request.getSession().setAttribute(Attribute.PHOTO, fileName);
                responseLine = SUCCESS_RESPONSE;
            } catch (ServiceException e) {
                responseLine = WRONG_RESPONSE;
                LOGGER.warn("Upload photo error. User id= " + userId + " try upload.");
            }
        } else {
            responseLine = WRONG_RESPONSE;
            LOGGER.warn("Upload photo wrong extension!");
        }
        response.getWriter().write(responseLine);
    }
}
