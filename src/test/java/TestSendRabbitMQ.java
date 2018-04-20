import com.zlst.Application;
import com.zlst.send.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by songcj on 2018/4/9.
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSendRabbitMQ {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void testSendRabbit(){

//        for(int j=0;j<100;j++) {
//            new Thread() {
//                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        helloSender.send();
                    }
//                }
//            }.run();
//        }

    }

}
