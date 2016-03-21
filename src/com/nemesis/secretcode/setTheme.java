
package com.nemesis.secretcode;

import java.io.*;

public class setTheme {
	
	
    void write(int n) throws IOException
	{

    	File myFile = new File("/mnt/sdcard/theme.txt");

    	if(!myFile.exists()){
    	    myFile.createNewFile();
    	}
    	if(myFile.exists()){
    		myFile.delete();
    		myFile.createNewFile();
    	}
            FileWriter Fr = new FileWriter(myFile);
            BufferedWriter Br =new BufferedWriter(Fr);
            PrintWriter P =new PrintWriter(Br);
            P.println(n);
            P.close();
        
	}
	static int read() throws IOException
	{
		
			int mnum=0;
	    	File myFile = new File("/mnt/sdcard/theme.txt");
	    	if (!myFile.exists()) {
	    	    return 1;
	    	}
            FileReader fr = new FileReader(myFile);
            BufferedReader br = new BufferedReader(fr);
            @SuppressWarnings("unused")
			String txt = "";
            while ((txt = br.readLine()) != null) 
            {
                mnum=Integer.parseInt(br.readLine());
            }
            br.close();
            return mnum;
       
      
		
	}

}
