package tmp.learning;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LearnHashFIle {

	public static void main(String[] args) {
		
		by_apache_commons_codec();
	}
	static void by_apache_commons_codec(){
		System.out.println(DigestUtils.sha1Hex(""));
	}
	static void by_java_message_digest() throws IOException, NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		try (InputStream is = Files.newInputStream(Paths.get("file.txt"));
		     DigestInputStream dis = new DigestInputStream(is, md)) 
		{
		  /* Read decorated stream (dis) to EOF as normal... */
		}
		byte[] digest = md.digest();
	}

}
