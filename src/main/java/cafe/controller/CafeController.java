package cafe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafe.bean.jpa.CafeDTO;
import cafe.bean.mybatis.AnalyticVisitDTO;
import cafe.bean.mybatis.CafeDTOCoordTemp;
import cafe.bean.mybatis.CafeDTOMybatis;
import cafe.bean.mybatis.CafeitemDTO;
import cafe.bean.mybatis.UserDateDTO;
import cafe.bean.mybatis.PaymentDTO;
import cafe.bean.mybatis.CafesDTO;
import cafe.bean.mybatis.Cafes_picsDTO;
import cafe.bean.mybatis.ProductsDTO;
import cafe.bean.mybatis.UserProfileDTO;
import cafe.bean.mybatis.UsersDTO;
import cafe.bean.mybatis.Cafes_product_listDTO;
import cafe.bean.mybatis.DistanceCount;
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

  @GetMapping(value = "/cafe/cafeDistLocation")
  public List<CafeDTOMybatis> getCafeDistLocation() {
    return cafeService.getCafeDistLocation();
  }

  @GetMapping(value = "/cafe/listAlllWithCoordMybatis")
  public List<CafeDTOCoordTemp> getCafesListWithCoord(@RequestParam(value = "userLong") double userLong,
      @RequestParam(value = "userLat") double userLat) {
    List<CafeDTOCoordTemp> list = cafeService.getCafesListWithCoordMybatis(userLong, userLat);
    return list;
  }

  @GetMapping(value = "/cafe/NickNameCheck")
  public String NickNameCheck(@RequestParam Map<String, String> map) {
    String check = cafeService.NickNameCheck(map);
    String findNickName;
    if (check == null) {
      findNickName = "ok";
    } else {
      findNickName = "fail";
    }

    return findNickName;
  }

  @GetMapping(value = "/cafe/UserCheck")
  public UsersDTO UserCheck(@RequestParam Map<String, String> map) {
    return cafeService.UserCheck(map);
  }

  @GetMapping(value = "/cafe/EmailCheck")
  public String EmailCheck(@RequestParam Map<String, String> map) {
    UsersDTO userDTO = cafeService.EmailCheck(map);
    String findEmail;
    if (userDTO == null) {
      findEmail = "ok";
    } else {
      findEmail = "fail";
    }
    return findEmail;
  }

  @PostMapping(value = "/cafe/createMember")
  public void createMember(@RequestParam Map<String, String> map) {
    System.out.println(map);
    UsersDTO usersDTO = null;
    String business_address = map.get("business_address");

    String user_type = map.get("user_type");
    if (user_type == "") {
      user_type = "user";
    } else if (Integer.parseInt(user_type) == 1) {
      user_type = "business";
    }
    map.put("user_type", user_type);
    // 회원가입과 동시에 user_id가져오기 위한 select
    Map<String, String> map1 = new HashMap<String, String>();
    String user_id1 = map.get("email");
    map1.put("user_id", user_id1);
    int num = cafeService.createMember(map);

    if (num != 0) {
      usersDTO = cafeService.getMember(map1);
    }
    // cafes생성
    if (map.get("user_type").equals("business")) {
      String address[] = business_address.split(" ");
      String address1 = address[0];
      String address2 = address[1];
      String address3 = address[2] + address[3];
      String address4 = map.get("business_address1");

      int user_id = usersDTO.getUser_id();
      Map<String, Object> map2 = new HashMap<String, Object>();
      map2.put("user_id", user_id);
      map2.put("cafe_name", map.get("business_name"));
      map2.put("address1", address1);
      map2.put("address2", address2);
      map2.put("address3", address3);
      map2.put("address4", address4);
      map2.put("about", "");
      cafeService.InsertCafes(map2);
    }
  }

  @PostMapping(value = "/cafe/updateMember")
  public int updateMember(@RequestParam Map<String, String> map) {
    System.out.println(map);
    return cafeService.updateMember(map);
  }

  @PostMapping(value = "/cafe/Login")
  public UsersDTO Login(@RequestParam Map<String, String> map) {
    UsersDTO userDTO = cafeService.Login(map);
    return userDTO;
  }

  @GetMapping(value = "/cafe/listBoundary3000Mybatis")
  public List<CafeDTOCoordTemp> getCafesListBoundary3000(@RequestParam(value = "userLong") double userLong,
      @RequestParam(value = "userLat") double userLat) {

    List<CafeDTOCoordTemp> list = cafeService.getCafesListBoundary3000Mybatis(userLong, userLat);
    return list;
  }

  @GetMapping(value = "/cafe/listBoundaryMybatis")
  public List<CafeDTOCoordTemp> getCafesListBoundary(
      @RequestParam(value = "userLong") double userLong,
      @RequestParam(value = "userLat") double userLat,
      @RequestParam(value = "boundary") int boundary,
      @RequestParam(value = "openFilter") Boolean openFilter,
      @RequestParam(value = "petFilter") Boolean petFilter,
      @RequestParam(value = "parkingFilter") Boolean parkingFilter) {

    List<CafeDTOCoordTemp> list = cafeService.getCafesListBoundary(userLong, userLat, boundary, openFilter, petFilter,
        parkingFilter);
    return list;
  }

  @GetMapping(value = "/cafe/getCafeitemList")
  public List<CafeitemDTO> getCafeitemList(@RequestParam Map<String, String> map) {
    return cafeService.getCafeitemList(map);
  }

  @GetMapping(value = "/cafe/updateCoordMybatis")
  public void updateCoord(@RequestParam(value = "longitude") double longitude,
      @RequestParam(value = "latitude") double latitude, @RequestParam(value = "cafe_id") long cafe_id) {
    cafeService.updateCoordMybatis(longitude, latitude, cafe_id);

    return;
  }

  @GetMapping(value = "/cafe/getCafeitem")
  public List<CafeitemDTO> getCafeitem(@RequestParam Map<String, String> map) {
    return cafeService.getCafeitem(map);
  }

  @PostMapping("/cafe/getMember")
  public UsersDTO getMember(@RequestParam Map<String, String> map) {
    return cafeService.getMember(map);
  }

  @PostMapping("/cafe/InsertProfileimg")
  public void InsertProfileimg(@RequestParam Map<String, String> map) {
    cafeService.InsertProfileimg(map);
  }

  @PostMapping("/cafe/selectProfileimg")
  public UserProfileDTO selectProfileimg(@RequestParam Map<String, String> map) {
    return cafeService.selectProfileimg(map);
  }

  @PostMapping("/cafe/updateProfileimg")
  public void updateProfileimg(@RequestParam Map<String, String> map) {
    cafeService.updateProfileimg(map);
  }

  @GetMapping(value = "/cafe/updateCafeinfo")
  public void updateCafeinfo(
      @RequestParam(value = "opentime") int opentime,
      @RequestParam(value = "closetime") int closetime,
      @RequestParam(value = "pet") String pet,
      @RequestParam(value = "parking") String parking,
      @RequestParam(value = "cafe_id") int cafe_id) {
    cafeService.updateCafeinfo(opentime, closetime, pet, parking, cafe_id);
  }

  @PostMapping(value = "/cafe/getcafes")
  public CafesDTO getcafes(@RequestParam Map<String, String> map) {
    return cafeService.getcafes(map);
  }

  @PostMapping(value = "/cafe/getcafefics")
  public List<Cafes_picsDTO> getcafefics(@RequestParam Map<String, String> map) {
    return cafeService.getcafefics(map);
  }

  @PostMapping(value = "/cafe/getcafeficsprofile")
  public Cafes_picsDTO getcafeficsprofile(@RequestParam Map<String, String> map) {
    return cafeService.getcafeficsprofile(map);
  }

  @PostMapping(value = "/cafe/insertCafepics")
  public void insertCafepics(@RequestParam Map<String, String> map) {
    cafeService.insertCafepics(map);
  }

  @PostMapping(value = "/cafe/updateCafepics")
  public void updateCafepics(@RequestParam Map<String, String> map) {
    cafeService.updateCafepics(map);
  }

  @PostMapping(value = "/cafe/insertcafes_product_list")
  public int insertcafes_product_list(@RequestParam Map<String, String> map) {
    return cafeService.insertcafes_product_list(map);
  }

  @PostMapping(value = "/cafe/insertproducts")
  public int insertproducts(@RequestParam Map<String, String> map) {
    return cafeService.insertproducts(map);
  }

  @PostMapping(value = "/cafe/insertcafes_product_list_items")
  public void insertcafes_product_list_items(@RequestParam Map<String, String> map) {
    System.out.println(map);
    cafeService.insertcafes_product_list_items(map);
  }

  @PostMapping(value = "/cafe/insertproducts_img")
  public void insertproducts_img(@RequestParam Map<String, String> map) {
    System.out.println(map);
    cafeService.insertproducts_img(map);
  }

  @PostMapping(value = "/cafe/selectcafes_product_list")
  public List<Cafes_product_listDTO> selectcafes_product_list(@RequestParam Map<String, String> map) {
    System.out.println(cafeService.selectcafes_product_list(map));
    return cafeService.selectcafes_product_list(map);
  }

  @PostMapping(value = "/cafe/selectproducts")
  public List<ProductsDTO> selectproducts(@RequestParam Map<String, String> map) {
    System.out.println(cafeService.selectproducts(map));
    return cafeService.selectproducts(map);
  }

  @PostMapping(value = "/cafe/cafesUpdate")
  public void cafesUpdate(@RequestParam Map<String, String> map) {
    cafeService.cafesUpdate(map);
  }

  @PostMapping(value = "/cafe/usersinstaupdate")
  public void usersinstaupdate(@RequestParam Map<String, String> map) {
    cafeService.usersinstaupdate(map);
  }

  @GetMapping(value = "/cafe/getAllUserMybatis")
  public List<UsersDTO> getAllUser() {
    return cafeService.getAllUser();
  }

  @GetMapping(value = "/cafe/updateUserMybatis")
  public void updateUser(
      @RequestParam(value = "user_id") int user_id,
      @RequestParam(value = "business_reg_num", required = false) int business_reg_num,
      @RequestParam(value = "user_type") String user_type,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "nickname") String nickname,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "password") String password,
      @RequestParam(value = "business_name", required = false) String business_name,
      @RequestParam(value = "business_address", required = false) String business_address) {
    cafeService.updateUser(
        user_id, business_reg_num, user_type, name, nickname, email, password, business_name, business_address);
    return;
  }

  @GetMapping(value = "/cafe/getCafeByInstaMybatis")
  public CafeDTO getCafeByInsta(@RequestParam(value = "insta_account") String insta_account) {
    CafeDTO cafe = cafeService.getCafeByInsta(insta_account);
    return cafe;
  }

  // 웅비 해당 제품 정보 가져오기
  @GetMapping(value = "/order/getProductInfo")
  public List<CafeitemDTO> getProductInfo(@RequestParam(value = "product_id") String product_id) {
    return cafeService.getProductInfo(product_id);
  }

  // 웅비 결제 정보 입력하기
  @PostMapping(value = "/order/paymentList")
  void paymentList(@RequestParam Map<String, String> map) {
    cafeService.paymentList(map);
  }

  // 웅비 결제 정보 불러오기
  @GetMapping(value = "/admin/getOrderList")
  public List<PaymentDTO> getOrderList(@RequestParam(value = "user_id") String user_id) {
    return cafeService.getOrderList(user_id);
  }

  // 웅비 결제 정보 삭제하기
  @PostMapping(value = "/admin/deleteOrderList")
  void deleteOrderList(@RequestParam Map<String, String> map) {
    cafeService.deleteOrderList(map);
  }

  @GetMapping(value = "/user/getUserAnalyticMybatis")
  public List<UserDateDTO> getUserAnalytic(
      @RequestParam(value = "user_type") String user_type,
      @RequestParam(value = "date_type") String date_type) {
    List<UserDateDTO> list = cafeService.getUserAnalytic(user_type, date_type);
    return list;
  }

  @GetMapping(value = "/cafe/increaeVisitMybatis")
  public void increaeVisit() {
    cafeService.increaeVisit();

    return;
  }

  @GetMapping(value = "/cafe/getVisitAnalyticMybatis")
  public List<AnalyticVisitDTO> getVisitAnalytic(
      @RequestParam(value = "dateFilter") String dateFilter) {
    return cafeService.getVisitAnalytic(dateFilter);
  }

  @GetMapping(value = "/cafe/getDistanceCount")
  public List<DistanceCount> getDistanceCount(@RequestParam(value = "userLong") double userLong,
      @RequestParam(value = "userLat") double userLat) {
    return cafeService.getDistanceCount(userLong, userLat);
  }

  @GetMapping(value = "/cafe/getCafeListByDistance")
  public List<CafeDTOCoordTemp> getCafeListByDistance(@RequestParam(value = "userLong") double userLong,
      @RequestParam(value = "userLat") double userLat, @RequestParam(value = "distanceKey") int distanceKey) {
    return cafeService.getCafeListByDistance(userLong, userLat, distanceKey);
  }

}
