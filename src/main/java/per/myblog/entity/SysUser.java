package per.myblog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SysUser {

  @Id
  private long id;
  private long wxTokenId;
  private String userName;
  private String userPhone;
  private Integer gender;
  private String city;
  private String headUrl;
  private String userPassword;
  private String sign;
  private Integer loginState;
  private String email;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getWxTokenId() {
    return wxTokenId;
  }

  public void setWxTokenId(long wxTokenId) {
    this.wxTokenId = wxTokenId;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }


  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }


  public String getHeadUrl() {
    return headUrl;
  }

  public void setHeadUrl(String headUrl) {
    this.headUrl = headUrl;
  }


  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }


  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }


  public Integer getLoginState() {
    return loginState;
  }

  public void setLoginState(Integer loginState) {
    this.loginState = loginState;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
