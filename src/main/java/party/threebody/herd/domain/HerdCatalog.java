package party.threebody.herd.domain;

public class HerdCatalog {
	String name;
	String localPath;
	String urlPrefix;

	public HerdCatalog() {
	}

	public HerdCatalog(String name, String localPath, String urlPrefix) {
		this.name = name;
		this.localPath = localPath;
		this.urlPrefix = urlPrefix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

}