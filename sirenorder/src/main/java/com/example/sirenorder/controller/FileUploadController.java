package com.example.sirenorder.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.example.sirenorder.biz.FileUploadDownloadService;
import com.example.sirenorder.fileupload.FileUploadResponse;

@Controller
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    
    @Autowired
    private FileUploadDownloadService service;
    
    @GetMapping("/yes")
    public String controllerMain() {
        return "Hello~ File Upload Test.";
    }
    
	@RequestMapping(value = "/applications.html", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("applications", "clicked");
		model.setViewName("thymeleaf/adminmain");
		return model;// id 없다.
	}
	
	//여기서 db에 apk 파일을 업로드 한다. 
    @PostMapping("/uploadFile")
    public String  uploadFile(@RequestParam("file") MultipartFile file) {
    	
    	//파일을 디비에 저장하는 서비스  dao는 사용 안한다. 
        String fileName = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/downloadFile/")
                                .path(fileName)
                                .toUriString();
        //file이 잘 올라 갔는지에 대한 response는 object로 돌려준다. 만약 실패면 alert 한다. 
        return "redirect:/adminmain.html";
    }
    
    /*
     * 
     * 아래의 것은 멀티 파일 업로딩을 위한 것 본인은 한개만 업로드 한다. 
      @PostMapping("/uploadFile")
    public FileUploadResponse  uploadFile(@RequestParam("file") MultipartFile file) {
    	
    	
    	//파일을 디비에 저장하는 서비스  dao는 사용 안한다. 
        String fileName = service.storeFile(file);
        
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/downloadFile/")
                                .path(fileName)
                                .toUriString();
        //file이 잘 올라 갔는지에 대한 response는 object로 돌려준다. 만약 실패면 alert 한다. 
        
        
		//return "/adminmain.html";

        return new FileUploadResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

     * 
    @PostMapping("/uploadMultipleFiles")
    public List<FileUploadResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
    }
    */
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
         // Load file as Resource
        Resource resource = service.loadFileAsResource(fileName);
 
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
 
        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
 
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @GetMapping("/download.html")
    public ModelAndView downloadHtml(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		model.setViewName("thymeleaf/downloadmain");
		model.addObject("download", "yes");
		return model;// id 없다.
    }
}
 
