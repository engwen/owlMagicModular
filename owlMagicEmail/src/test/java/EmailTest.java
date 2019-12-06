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
        EmailBase base =  EmailBase.getInstances("xiachanzou@outlook.com","1490418412@qq.com","jnzwmdimklhujdeb");
        base.init("这是一个测试文件","t是的范德萨发第三方啊对方答复");
        base.setUserName("英文");
        base.setHost("smtp.qq.com");//smtp.qq.mail，smtp.163.com
        base.send();
    }
}
