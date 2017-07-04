package tmp.experiment;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ExifLearn {

	static String dirpath="C:\\Users\\Administrator\\Pictures\\test1";
	public static void main(String[] args){
		Collection<File> files=FileUtils.listFiles(new File(dirpath), null, true);
		files.forEach(f->pp(f));
	}
	
	static void pp(File f){
		 System.out.println("========================");
		 System.out.println(f);
		Metadata metadata;
		try {
			metadata = ImageMetadataReader.readMetadata(f);
			for (Directory directory : metadata.getDirectories()) {
			    for (Tag tag : directory.getTags()) {
			        System.out.println(tag);
			    }
			}
		} catch (ImageProcessingException | IOException e) {
			e.printStackTrace();
		}
	}
}
