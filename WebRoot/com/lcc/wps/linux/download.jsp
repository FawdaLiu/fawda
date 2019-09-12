<%@ page language="java" import="java.io.*"%>
<%@ page language="java" import="java.lang.*"%>
<%
	
	String userid = (String)(session.getAttribute("userid"));
	System.out.println("*****************************************************");
	if(userid==null){ 
	//if(false){ 
		out.print("No login!");
		System.out.println("NO login!");
		response.sendError(407, "Need authentication!!!" );
	}else{
		System.out.println("login! current user is : " + userid);
	

		String filename = request.getParameter("name"); 
		if(filename==null || filename.isEmpty()){
			out.print("please set file name ");
		}else{
			String realFileName = request.getSession().getServletContext().getRealPath("/") + filename;
			System.out.println(realFileName);
			  //得到想客服端输出的输出流  
			 OutputStream outputStream = response.getOutputStream();  
				//输出文件用的字节数组，每次向输出流发送600个字节  
			   byte b[] = new byte[600];  
			  //要下载的文件  
			   File fileload = new File(realFileName);       
			   //客服端使用保存文件的对话框  
			   response.setHeader("Content-disposition", "attachment; filename*=UTF-8''"+filename);  
			   //通知客服文件的MIME类型  
				response.setContentType("application/msword");  
			   //通知客服文件的长度  
			   long fileLength = fileload.length();  
				String length = String.valueOf(fileLength);  
			   response.setHeader("Content-length", length);  
				//读取文件，并发送给客服端下载  
				FileInputStream inputStream = new FileInputStream(fileload);  
			   int n = 0;  
			   while((n=inputStream.read(b))!=-1){  
				   outputStream.write(b,0,n);  
			   }
			   inputStream.close();
			   outputStream.close();
		}
	}

%>
