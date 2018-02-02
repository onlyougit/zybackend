package com.rttmall.shopbackend.utils;

import java.io.File;
import java.io.FileInputStream;

public class MyTest {


	public static void main(String[] args) throws Exception{
		/*
		 * List<Product> list = new ArrayList<>(); Product p = new Product();
		 * p.setId(1); p.setStatus(0); Product p1 = new Product(); p1.setId(2);
		 * p1.setStatus(1); Product p2 = new Product(); p2.setId(3);
		 * p2.setStatus(1); Product p3 = new Product(); p3.setId(4);
		 * p3.setStatus(1); list.add(p); list.add(p1); list.add(p2);
		 * list.add(p3);
		 * System.out.println(list.stream().anyMatch(w->w.getStatus()==0));
		 */
		/*
		 * List<Integer> list = Arrays.asList(1,13,14,2,3,4); list =
		 * list.stream(
		 * ).sorted((x,y)->x.compareTo(y)).collect(Collectors.toList());
		 * list.forEach(System.out::println);
		 */
		/*String str = "hello\nworld\n\n!";
		System.out.println(str);
		str = str.replace("\n", "<br />");
		System.out.println(str);*/
        /*c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);*/
		/*String ftpHost = "47.96.18.156";  
        String ftpUserName = "ftptest";  
        String ftpPassword = "wangwb";  
        int ftpPort = 21;  
        String ftpPath = "/home/ftptest";  
        String downloadPath = "C:/download";  
        String fileName = "c.docx";  
        FtpUtil.downFile(ftpHost, ftpPort, ftpUserName, ftpPassword, ftpPath, fileName, downloadPath);*/
        //String uploadFile = "C:/upload/c.docx";  
        //FtpUtil.uploadFile(ftpHost, ftpPort, ftpUserName, ftpPassword, ftpPath, fileName, new FileInputStream(new File(uploadFile)));  
        
        
        //windows
        String ftpHost = "106.14.193.187";  
        String ftpUserName = "admin";  
        String ftpPassword = "admin";  
        int ftpPort = 21;  
        String ftpPath = "./res/home";  
        String downloadPath = "C:/download";  
        String fileName = "c.docx";  
        //FtpUtil.downFile(ftpHost, ftpPort, ftpUserName, ftpPassword, ftpPath, fileName, downloadPath);
        String uploadFile = "C:/upload/c.docx"; 
        FtpUtil.uploadFile(ftpHost, ftpPort, ftpUserName, ftpPassword, ftpPath, fileName, new FileInputStream(new File(uploadFile)));
	}

	

}
