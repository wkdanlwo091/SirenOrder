package com.example.sirenorder.biz;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.sirenorder.fileupload.FileDownloadException;
import com.example.sirenorder.fileupload.FileUploadException;
import com.example.sirenorder.fileupload.FileUploadProperties;
import com.example.sirenorder.mapper.FilesMapper;
import com.example.sirenorder.vo.FilesVO;

@Service
public class FileUploadDownloadService {
    private final Path fileLocation;
    
	@Autowired
	FilesMapper fileMapper;

    @Autowired
    public FileUploadDownloadService(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileLocation);
        }catch(Exception e) {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }
    
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            //여기서 db에다가 파일 저장 
            file.getSize();//이게 데이터를 db에다가 넣는다. 
            
            //files에 넣을 시간 구하기 
            long millis=System.currentTimeMillis();  
            java.sql.Date regDate=new java.sql.Date(millis);  

            FilesVO filesVO = new FilesVO();
            filesVO.setFiles_name(fileName);
            filesVO.setFiles_size(file.getSize());
            filesVO.setRegdate(regDate);
            
            fileMapper.insert(filesVO);
            
            return fileName;
        }catch(Exception e) {
        	e.printStackTrace();
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }
    
    
    
    //파일 가져오기 
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }
}
