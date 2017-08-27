package party.threebody.herd.domain

import java.time.LocalDateTime;

class Repo {
	String name
	String absPath
	String url
	String type
	String desc
	LocalDateTime saveTime
}

class RepoFile{
	String hash
	String repoName
	String absPath
	String type
	String subtype
	String desc
	int size
	LocalDateTime syncTime



}
class PicFile{
	String hash
	int height
	int width
}
class PhotoInfo{
	String hash
	int height
	int width
	LocalDateTime shootTime
	String gpsLon	//NS
	String gpsLat	//EW
	String gpsAlt	//height above the sea
}