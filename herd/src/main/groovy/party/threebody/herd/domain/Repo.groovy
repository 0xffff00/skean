package party.threebody.herd.domain

import java.time.LocalDateTime

class Repo {
	String name
	String absPath
	String url
	String type
	String desc
	LocalDateTime saveTime
	String state
}

class RepoFile{
	String hash
	String repoName
	String absPath
	String type
	String subtype
	String desc
	Integer size
	LocalDateTime syncTime

	//DTO fields
	List<String> paths

	void setTypeAndSubtype(TypeAndSubType tast){
		type=tast.type
		subtype=tast.subtype
	}

}
class RepoFilePath{
	String hash
	String path
	String type
	String repoName

	RepoFilePath(String hash, String path, String type, String repoName) {
		this.hash = hash
		this.path = path
		this.type = type
		this.repoName = repoName
	}
}

class TypeAndSubType {
	String type
	String subtype
}