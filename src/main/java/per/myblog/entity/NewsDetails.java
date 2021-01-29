package per.myblog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class NewsDetails {

  @Id
  private long id;
  private String newsCode;
  private String newsTitle;
  private long newsAuthId;
  private String newsImg;
  private Date newsTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getNewsCode() {
    return newsCode;
  }

  public void setNewsCode(String newsCode) {
    this.newsCode = newsCode;
  }


  public String getNewsTitle() {
    return newsTitle;
  }

  public void setNewsTitle(String newsTitle) {
    this.newsTitle = newsTitle;
  }


  public long getNewsAuthId() {
    return newsAuthId;
  }

  public void setNewsAuthId(long newsAuthId) {
    this.newsAuthId = newsAuthId;
  }


  public String getNewsImg() {
    return newsImg;
  }

  public void setNewsImg(String newsImg) {
    this.newsImg = newsImg;
  }


  public Date getNewsTime() {
    return newsTime;
  }

  public void setNewsTime(Date newsTime) {
    this.newsTime = newsTime;
  }

}
