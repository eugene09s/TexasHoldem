package com.epam.poker.controller.command.impl.user;

import com.epam.poker.controller.command.Command;
import com.epam.poker.controller.command.CommandResult;
import com.epam.poker.controller.request.RequestContext;
import com.epam.poker.exception.InvalidParametersException;
import com.epam.poker.exception.ServiceException;
import com.epam.poker.model.logic.service.user.ProfilePlayerService;
import jakarta.servlet.http.Part;

import java.io.File;

public class UploadPhotoCommand implements Command {
    private final ProfilePlayerService profilePlayerService;

    public UploadPhotoCommand(ProfilePlayerService profilePlayerService) {
        this.profilePlayerService = profilePlayerService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        File uploadDir = new File("P:\\epam\\finalproject\\data");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        //        Part filePart = request.getPart("file");
//        String fileName = filePart.getSubmittedFileName();
//        for (Part part : request.getParts()) {
//            part.write("C:\\upload\\" + fileName);
//        }
//        response.getWriter().print("The file uploaded sucessfully.");
        return null;
    }

//    private String getFileName(Part part) {
//        for (String content : part.getHeader("content-disposition").split(";")) {
//            if (content.trim().startsWith("filename"))
//                return content.substring(content.indexOf("=") + 2, content.length() - 1);
//        }
//        return Constants.DEFAULT_FILENAME;
//    }
}
