package com.huang.spring;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/helloworld")
public class Helloworld {
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String hellword(Model model, HttpServletRequest request, HttpServletResponse response){
		model.addAttribute("myname", "黄德罡");
		return "index";
	}
	
	@RequestMapping(value="/administrativeDistrictMain", method=RequestMethod.GET)
	public String administrativeDistrictMain(Model model, HttpServletRequest request, HttpServletResponse response){
		return "administrativeDistrict";
	}
	
	@RequestMapping(value="/proxy/image", method=RequestMethod.GET)
	public void proxyImage(HttpServletRequest request, HttpServletResponse response){
		String url = request.getParameter("url");
        String callback = request.getParameter("callback");
        if(url != "" && callback != ""){
	            try {
	                URL urlInfo = new URL(url);
	                if(urlInfo.getProtocol().equals("http") || urlInfo.getProtocol().equals("https")){
	                    HttpURLConnection conn = (HttpURLConnection) urlInfo.openConnection();
	                    String contentType = conn.getContentType();
	                    if(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg") || contentType.equals("image/gif") || contentType.equals("text/html") || contentType.equals("application/xhtml")){
	                    	response.setHeader("Access-Control-Allow-Origin", "*");
                            response.setContentType(contentType);
                            DataInputStream input = new DataInputStream(conn.getInputStream());
                            BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
                            try {
                              byte b[] = new byte[1024];
                              int len = input.read(b);
                              while (len > 0) {
                                bout.write(b, 0, len);
                                len = input.read(b);
                              }
                            } catch (Exception e) {
                              e.printStackTrace();
                            } finally {
                              bout.close();
                              input.close();
                            }
	                    	/*if(request.getParameter("xhr2") != null){
	                            response.setHeader("Access-Control-Allow-Origin", "*");
	                            response.setContentType(contentType);
	                            DataInputStream input = new DataInputStream(conn.getInputStream());
	                            BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
	                            try {
	                              byte b[] = new byte[1024];
	                              int len = input.read(b);
	                              while (len > 0) {
	                                bout.write(b, 0, len);
	                                len = input.read(b);
	                              }
	                            } catch (Exception e) {
	                              e.printStackTrace();
	                            } finally {
	                              bout.close();
	                              input.close();
	                            }
	                        }else{
	                            response.setContentType("application/javascript");
	                            if(contentType.equals("text/html") || contentType.equals("application/xhtml")){
	                            }else{
	                                DataInputStream input = new DataInputStream(conn.getInputStream());
	                                input.toString();
	                                byte[] buffer = new byte[1024 * 8]; 
	                                 
	                                ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
	                                byte[] b = new byte[1024 * 8];
	                                int n; 
	                                while ((n = input.read(b)) != -1) { 
	                                    bos.write(b, 0, n); 
	                                } 
	                                input.close(); 
	                                bos.close();
	                                buffer = bos.toByteArray(); 
	                                Base64 encode = new Base64();
	                                String base64data = encode.encodeBase64String(buffer);
	                                base64data = base64data.replaceAll("\r\n","");
	                                response.getWriter().write(callback+"('"+ "data:" + contentType + ";base64," + base64data +"')");
	                            }
	                        }
	 */
	  
	                    }
	                }
	            } catch (MalformedURLException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
           }
	}
	
	@RequestMapping(value="/proxy/image2", method=RequestMethod.GET)
	public @ResponseBody String proxyImage2(HttpServletRequest request, HttpServletResponse response, String url){
        URL imageurl;
        InputStream is = null;
		try {
			imageurl = new URL(url);
	        URLConnection con = imageurl.openConnection();  
	        con.setConnectTimeout(5*1000);  
	        is = con.getInputStream(); 
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
      
        byte[] bs = new byte[1024];  
        int len;  
        String savePath = "D:\\workspace\\Secret\\src\\main\\webapp\\images";
       File sf=new File(savePath);  
       if(!sf.exists()){  
           sf.mkdirs();  
       }  
       OutputStream os;
       String filename = new Date().getTime()+".jpg";
	try {
		os = new FileOutputStream(sf.getPath() + "\\" + filename);
       
			while ((len = is.read(bs)) != -1) {  
			  os.write(bs, 0, len);  
			}
	        os.close();  
	        is.close(); 
		 
         
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       
		return "../images/" + filename;
	}
	
	@RequestMapping(value="/map/recordAdministrativeDistrict", method=RequestMethod.POST)
	public void recordAdministrativeDistrict(HttpServletRequest request, HttpServletResponse response, String distictName, String boundary){
		String prefix = "D:/mapfiles/";
		String fullPath = prefix + distictName + ".txt";
		String[] boundaryArr = boundary.split(";");
		FileWriter file = null;
		try {
			file = new FileWriter(fullPath, true);
			for( int i = 0; i < boundaryArr.length; i++){
				file.write(boundaryArr[i] + "\r\n");
			}
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
