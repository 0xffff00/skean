package party.threebody.herd.dao

import org.springframework.stereotype.Repository

import party.threebody.herd.domain.HerdCatalog

@Repository
class HerdCatalogDao {

	List<HerdCatalog> list(){
		[
			new HerdCatalog("hzk图片","C:/Users/hzk/Pictures","http://localhost:8080/pic2"),
			new HerdCatalog("E:/ARC1","E:/ARC1","http://localhost:8080/pic1")
		]
	}
}
