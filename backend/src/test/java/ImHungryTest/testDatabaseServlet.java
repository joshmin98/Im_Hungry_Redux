package test.java.ImHungryTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ImHungryServlet.DatabaseServlet;


public class testDatabaseServlet extends Mockito {

    @Before
    public void setUp() { 
         
    }

    @Test
    public void testDatabaseServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);

        
    }

}
