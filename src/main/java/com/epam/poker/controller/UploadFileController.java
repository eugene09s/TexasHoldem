package com.epam.poker.controller;

import com.epam.poker.controller.command.constant.Attribute;
import com.epam.poker.exception.DaoException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.service.user.ProfilePlayerService;
import com.epam.poker.model.service.user.ProfilePlayerServiceImpl;
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

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadFileController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String UPLOAD_FOLDER = "images/photo";
    private static final String FILE = "file";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart(FILE);
        String fileLastName = filePart.getSubmittedFileName();
        String fileExt = fileLastName.substring(fileLastName.lastIndexOf("."));
        long userId = (long) request.getSession().getAttribute(Attribute.USER_ID);
        String fileName = userId + fileExt;
        String applicationDir = request.getServletContext().getRealPath("");
        String uploadFileDir = applicationDir + UPLOAD_FOLDER + File.separator;
        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        for (Part part : request.getParts()) {
            part.write(uploadFileDir + fileName);
        }
        ProfilePlayerService profilePlayerService = ProfilePlayerServiceImpl.getInstance();
        String responseLine = "";
        try {
            profilePlayerService.updatePhotoByUserId(userId, fileName);
            responseLine = "{\"success\": true}";
        } catch (ServiceException | DaoException e) {
            responseLine = "{\"success\": false}";
            LOGGER.error("Upload photo error. User id= " + userId + " try upload.");
        }
        response.getWriter().print(responseLine);//todo don't send response
    }
}
