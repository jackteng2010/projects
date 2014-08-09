package com.core.util;

import java.io.File;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.core.config.ConfigManager;

@Service
public class FileUtil {

	public static final String rootPath = ConfigManager.getValue("tomcat.webapp.dir");
	
	protected static final Log logger = LogFactory.getLog(FileUtil.class);
	
	public static String uploadFileURL(HttpServletRequest request, MultipartFile file, String config, String dir){
		if(file == null || file.isEmpty()) return null;
		return "http://" + request.getServerName() + (request.getServerPort()==0 ? "" : ":" + request.getServerPort()) + uploadFile(file, config, dir);
	}
	
	/**
	 * @return String ('D:/.../upload/images')
	 * 
	 * */
	public static String uploadFile(MultipartFile file, String config, String dir) {
		if(file == null || file.isEmpty()) return null;

		//begin check 
		String spContentTypes = ConfigManager.getValue(config + ".contenttypes");
		String spWidth = ConfigManager.getValue(config + ".maxwidth");
		String spHeight = ConfigManager.getValue(config + ".maxheight");
		String spmaxfsize = ConfigManager.getValue(config + ".maxfilesize");
		String dirPath = ConfigManager.getValue(config + ".filedir");
		
		if(dirPath == null){
			//TODO:>>>
		}
		if(dir != null && !dir.isEmpty()){
			dirPath += "/" + dir;
		}
		
		String name = file.getOriginalFilename();
		String type = name.substring(name.lastIndexOf("."), name.length());
		String dirPathFull = rootPath + dirPath;

		File patent = new File(dirPathFull);
		if(!patent.exists()){
			patent.mkdirs();
		}
		String fileName = UUID.randomUUID().toString() + type;
		File target = new File(dirPathFull + "/" + fileName);
		try {
			file.transferTo(target);
		} catch (Exception e) {
			//TODO:>>>
		}
		return dirPath + "/" + fileName;
	}

	public static boolean deleteFile(File file, String folder) {
		if (folder != null) {
			String newFile = folder + "/" + file.getName();
			file = new File(newFile);
		}
		return deleteFile(file);
	}

	public static boolean deleteFile(File file) {
		try {
			boolean direxist = file.exists();
			if (direxist) {
				if (file.isDirectory()) {
					String[] children = file.list();
					for (int i = 0; i < children.length; i++) {
						boolean success = deleteFile(new File(file, children[i]));
						if (!success) {
							return false;
						}
					}
				}
			}
			boolean delete = true;
			try {
				file.delete();
			} catch (Exception e) {
				logger.warn("File " + file.getName() + " does not exist");
			}
			return delete;
		} finally {
			
		}
	}

	public static boolean deleteFile(String file) {
		if(file == null || file.isEmpty()) {
			return false;
		}
		String fs = file.toLowerCase();
		if(fs.startsWith("http://")){
			fs = fs.substring(fs.indexOf("/", 7));
			fs = rootPath + fs;
		}
		File _file = new File(fs);
		return deleteFile(_file);
	}
	
}
