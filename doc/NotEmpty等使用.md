## NotEmpty,NotBlank,NotNull注释的使用
@NotEmpty : 用在集合类上面的属性的注释，需要搭配@Vaild注释作用  
@NotBlank : 用在String上面的属性的注释，需要搭配@Vaild注释作用  
@NotNull : 用在基本类型的包装类型上面的属性注释  

案例
```Java
import javax.validation.constraints.*;
import java.util.List;

public class User {

    // 基本类型的包装类型（如 Integer、Double）用 @NotNull
    @NotNull(message = "年龄不能为空")
    private Integer age;

    // String 类型用 @NotBlank（确保非空且至少有一个非空白字符）
    @NotBlank(message = "姓名不能为空")
    private String name;

    // 集合类（如 List、Set）用 @NotEmpty（确保集合不为空）
    @NotEmpty(message = "至少需要一个电话号码")
    private List<String> phoneNumbers;

    // Getter 和 Setter（省略）
    // ...
}
```
使用
```Java
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok("用户创建成功: " + user.getName());
    }
}
```