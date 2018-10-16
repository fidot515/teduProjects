package com.jt.manage.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller   //首字母小写   
public class FileController implements 
				BeanNameAware,
				BeanFactoryAware,
				ApplicationContextAware{
	
	@Autowired
	private FileService fileService;
	/**
	 * 如果参数需要赋值,需要通过springMVC提供的解析器才可以.
	 * 注意事项:
	 * 	参数接收,必须和页面提交的name属性相同.否则参数不能提交.
	 *  程序报404报错.
	 * @param image
	 * @return
	 * @throws IllegalStateException 
	 * @throws IOException 
	 */
	@RequestMapping("/file")
	public String imageFile(MultipartFile image) throws IllegalStateException, IOException{
		
		//1.定义文件上传的目录  E:\jt-upload
		File imageFile = new File("E:/jt-upload");
		
		//2.判断文件是否存在
		if(!imageFile.exists()){
			//创建文件夹
			imageFile.mkdirs();
		}
		//3.获取文件名称
		String fileName  = image.getOriginalFilename();
		
		//4.实现文件上传 文件路径/文件名称
		image.transferTo(new File("E:/jt-upload/"+fileName));
		System.out.println("文件上传实现成功!!!!!");
		
		//跳转到index页面
		return "index";
	}
	
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult fileUpload(MultipartFile uploadFile){
		
		return fileService.fileUpload(uploadFile);
	}


	@Override
	public void setBeanName(String name) {
		
		System.out.println("获取当前类在Spring中的ID值:"+name);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		
		System.out.println("获取spring工厂对象:"+beanFactory);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		System.out.println("获取整个spring容器:"+applicationContext);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
