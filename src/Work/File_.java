package Work;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class File_ {
	
	public String ReadFile(String name) throws IOException {
		String string = "";
		
		byte[] all = Files.readAllBytes(Paths.get(name));
		string = new String(all);
		return string;
	}
	
	public boolean CanReadFile(String name) {
		String string = "";
		try(FileReader reader = new FileReader(name)) {
            int c;
            while((c=reader.read())!=-1){
            	string = string + (char)c;
            }
            return true;
        }
        catch(IOException ex){
        	return false;
        }
		
	}
	
	public void WriteFile(String filename, String text) throws IOException {
		
		try (FileWriter writer = new FileWriter(new File(filename))) {
			writer.write(text);
			writer.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//	    FileOutputStream fos = null;
//	    boolean tr;
//	    try {
//	        fos = new FileOutputStream(filename);
//	        fos.write(text.getBytes(bytes));//"ASCII"));//UTF-8"));
//	        tr = true;
//	        close(fos);
//	    } catch (IOException e) {
//	    	tr = false;
//	    	close(fos);
//	        throw e;
//	    }
//	    return tr;
	}
	
//	private void close(Closeable closeable) {
//	    try {
//	        closeable.close();
//	    } catch(IOException ignored) {
//	    }
//	}
	
	public int CountOfFiles() {
		int i = 0;
		boolean can = true;
		while (can) {
			try {
				can = CanReadFile(System.getProperty("user.dir") + "\\files\\names\\" + i + ".name");
				//System.out.println(i + " " + can + " " + string);
				//if(string.equals("")) {
				//	can = false;
				//}
			} catch (Exception e) {
				can = false;
			}
			i++;
			
		}
		return i-2;
	}
	
	public void SaveImage(String p, BufferedImage bufferedImage2) throws IOException {
		BufferedImage bufferedImage = bufferedImage2;
		byte[] currentImage = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", baos);
			baos.flush();
			currentImage = baos.toByteArray();
			baos.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}   
		
		FileOutputStream newFile = new FileOutputStream(p);
				newFile.write(currentImage);
				newFile.close();
	}
	
}
