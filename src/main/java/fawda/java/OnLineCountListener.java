package fawda.java;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class OnLineCountListener
 *
 */
@WebListener
public class OnLineCountListener implements HttpSessionListener, HttpSessionAttributeListener {


    /**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        OnLineCounter.addCounter();
    }

    /**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        OnLineCounter.reduceCounter();
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent arg0) {
        // TODO Auto-generated method stub
        
    }

}
