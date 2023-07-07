package todo.advance.blindsight.entity.custom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadFile {
    private final Cloudinary cloudinary;

    // upload file to cloudinary
    public String uploadCloudinary(File file, String folder) throws IOException {
        Map<String, Object> options = new HashMap<>();
        options.put("folder", folder); // set the folder parameter
        options.put("resource_type", "auto"); // set the resource type to auto-detect the file type
    
        Map uploadResult = cloudinary.uploader().upload(file, options); // upload the file
        String publicId = (String) uploadResult.get("public_id"); // extract the public ID of the uploaded file
        String url = cloudinary.url().generate(publicId); // generate the URL of the uploaded file
    
        return url;
    }

    // convert multipartfile to file(cause we will use mulpartfile to upload file in controller)
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // combine 2 method above and get the link
    public String uploadFile(MultipartFile multipartFile, String folder) {
        try {
            File convertedFile = convertMultipartFileToFile(multipartFile);
            String url = uploadCloudinary(convertedFile, folder);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }
}
