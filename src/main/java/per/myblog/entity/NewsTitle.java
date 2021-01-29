package per.myblog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NewsTitle {

  @Id
  private long id;
  private String newsCode;
  private String newsTitle;


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

}
