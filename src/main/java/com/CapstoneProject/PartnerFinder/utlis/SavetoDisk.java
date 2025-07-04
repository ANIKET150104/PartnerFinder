package com.CapstoneProject.PartnerFinder.utlis;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public class SavetoDisk {

	public static void saveFile(MultipartFile fileToSave, Path fullPath) throws IOException {

        fileToSave.transferTo(fullPath);

    }
}
