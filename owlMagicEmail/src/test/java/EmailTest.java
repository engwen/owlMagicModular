import com.owl.email.EmailBase;
import org.junit.Test;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/6.
 */
public class EmailTest {

    @Test
    public void sendEmail(){
        EmailBase base = new EmailBase();
        base.setContext("t是的范德萨发第三方啊对方答复");
        base.setFrom("1490418412@qq.com");
        base.setTo("xiachanzou@outlook.com");
        base.setSubject("ddd");
        base.setHost("smtp.qq.com");//smtp.qq.mail，smtp.163.com
        base.setPort("465");
        base.setUserName("1490418412@qq.com");
        base.setUserPassword("jnzwmdimklhujdeb");
        base.send();
    }
}
