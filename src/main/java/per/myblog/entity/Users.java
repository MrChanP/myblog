package per.myblog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Users implements Serializable {
    @Id
    private long Id;
    private String loginName;
    private String password;
    private String salt;
}
