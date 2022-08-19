package cafe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafe.bean.jpa.CafeDTO;
import cafe.bean.mybatis.CafeDTOCoordTemp;
import cafe.bean.mybatis.CafeDTOMybatis;
import cafe.bean.mybatis.CafePointTest;
import cafe.service.CafeService;

@RestController
@CrossOrigin(origins = "*")
public class CafeController {

	@Autowired
	private CafeService cafeService;
		
	@GetMapping(value = "/cafe/listAll")
	public List<CafeDTO> getCafeListAll() {
		return cafeService.getCafeListAll();
	}
	
	@GetMapping(value = "/cafe/listAllMybatis")
	public List<CafeDTOMybatis> getCafeIdOne() {
		return cafeService.getCafeListAllMybatis();
	}
	
	@GetMapping(value ="/cafe/listBoundaryMybatis")
	public List<CafePointTest> getCafeListBoundary(@RequestParam(value = "longitude") double userLong, @RequestParam(value = "latitude") double userLat){
	  
	  System.out.println("\n @Poong Log@ 받아온 데이터 : " + userLong +  " , " + userLat );
	  List<CafePointTest> list = cafeService.getCafeListBoundary(userLong, userLat);
	  
	  return list;
	}
	
	@GetMapping(value = "/cafe/listAlllWithCoord")
	public List<CafeDTOCoordTemp> getCafesListWithCoord(@RequestParam(value = "userLong") double userLong, @RequestParam(value = "userLat") double userLat){
	  List<CafeDTOCoordTemp> list = cafeService.getCafesListWithCoord(userLong, userLat);
	  
	  return list;
	}

	@GetMapping(value = "/cafe/listBoundary3000")
	public List<CafeDTOCoordTemp> getCafesListBoundary3000(@RequestParam(value = "userLong") double userLong, @RequestParam(value = "userLat") double userLat){
	  System.out.println("\n @PH LOG@ listBoundary3000... 넘어온 사용자 경위도 : " + userLong + "\t" + userLat);
    
	  List<CafeDTOCoordTemp> list = cafeService.getCafesListBoundary3000(userLong, userLat);
	  
	  return list;
	}
	
	@GetMapping(value = "/cafe/updateCoord")
	public void updateCoord(@RequestParam(value = "longitude") double longitude, @RequestParam(value = "latitude") double latitude, @RequestParam(value = "cafe_id") long cafe_id) {
	  cafeService.updateCoord(longitude, latitude, cafe_id);
	  
	  return ;
	}
	
}
