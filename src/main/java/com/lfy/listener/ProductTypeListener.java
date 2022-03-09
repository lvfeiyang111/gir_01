package com.lfy.listener;

import com.lfy.domain.ProductType;
import com.lfy.service.Impl.ProductTypeServiceImpl;
import com.lfy.service.ProductTypeService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * 用于回显增加商品栏的类别
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//手动从Spring容器中取出typeserviceimpl
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
        ProductTypeService typeService = (ProductTypeService)ac.getBean("productTypeServiceImpl");
        List<ProductType> typelist = typeService.getAll();
        //放入全局域
        servletContextEvent.getServletContext().setAttribute("typelist",typelist);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
