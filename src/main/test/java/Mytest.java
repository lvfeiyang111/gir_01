import com.lfy.dao.ProductInfoMapper;
import com.lfy.domain.ProductInfo;
import com.lfy.domain.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/applicationContext.xml"})
public class Mytest {
    @Autowired
ProductInfoMapper mapper;
    @Test
    public void test(){
        ProductInfoVo productInfoVo = new ProductInfoVo();
        productInfoVo.setPname("4");
        List<ProductInfo> list = mapper.selectCondition(productInfoVo);
        for (ProductInfo p:list
             ) {
            System.out.println(p);
        }

    }
}
