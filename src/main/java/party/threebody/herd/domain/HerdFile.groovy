package party.threebody.herd.domain

import java.time.LocalDateTime

class HerdFile {

	String hash
	String catalog
	String relPath
	int fileSize
	LocalDateTime syncTime 
	public HerdFile(String hash, String catalog, String relPath, int fileSize) {
		this.hash = hash;
		this.catalog = catalog;
		this.relPath = relPath;
		this.fileSize = fileSize;
	}
	
	
}
