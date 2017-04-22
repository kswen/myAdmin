/**
 * 
 */
package com.jeecmstheme.jautopost;

import java.util.HashMap;
import java.util.Map;

/**
 * MIME Type and file extension mapping
 * 
 * @author jeecmstheme.com
 *
 */
public final class MIMEType {

	public static Map<String, String> typeMap = new HashMap<String, String>();
	public static Map<String, String> extMap = new HashMap<String, String>();

	static{
		extMap.put("image/png", ".x-png");
		extMap.put("image/x-xwd", ".xwd");
		extMap.put("image/x-xwindowdump", ".xwd");
		extMap.put("image/x-xpixmap", ".xpm");
		extMap.put("image/png", ".png");
		extMap.put("video/x-motion-jpeg", ".mjpg");
		extMap.put("image/jpeg", ".jpg");
		extMap.put("image/pjpeg", ".jpg");
		extMap.put("image/gif", ".gif");
		extMap.put("image/bmp", ".bmp");
		extMap.put("image/x-windows-bmp", ".bmp");
		typeMap.put(".x-png","image/png");
		typeMap.put(".xwd","image/x-xwd");
		typeMap.put(".xwd","image/x-xwindowdump");
		typeMap.put(".xpm","image/x-xpixmap");
		typeMap.put(".png","image/png");
		typeMap.put(".mjpg","video/x-motion-jpeg");
		typeMap.put(".jpg","image/jpeg");
		typeMap.put(".jpg","image/pjpeg");
		typeMap.put(".gif","image/gif");
		typeMap.put(".bmp","image/bmp");
		typeMap.put(".bmp","image/x-windows-bmp");
	}
	
	/**
	 * return extension
	 */
	public static final String getExt(String mime){
		return extMap.get(mime);
	}
	/**
	 * return mime type
	 */
	public static final String getType(String ext){
		return typeMap.get(ext);
	}
}
