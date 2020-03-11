<html>
<head>
<%@ page language="java" import="java.io.*"%>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>This page for response</title>
</head>
<body>
    <%    try {      
	out.print(request.getContentLength());
	if (request.getContentLength() > 0) 
	{           
		
		InputStream in = request.getInputStream();
		String localfileName = request.getHeader("filename");
		String realFileName = "D:\\temp\\" + localfileName;
	           File f = new File(realFileName);
	           FileOutputStream o = new FileOutputStream(f);
	           byte b[] = new byte[1024];
	           int n;
	           while ((n = in.read(b)) != -1)
		  {               
			o.write(b, 0, n);
	           }
           		   o.close();
		           in.close();
		           out.print("File upload success!");          
	} 
	else
	{
              		out.print("No file!");
           }
       } 
	catch (IOException e)
	 {
	           out.print("upload error.");
           		e.printStackTrace();
       }
%>
</body>
</html>