package com.wjelogistics.edi.esb.interceptor;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.api.interceptor.Interceptor;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.context.notification.MuleContextNotification;
import org.mule.context.notification.NotificationException;
import org.mule.processor.AbstractInterceptingMessageProcessor;

public class BrokerNotReadyInterceptor extends AbstractInterceptingMessageProcessor implements Interceptor, MuleContextNotificationListener<MuleContextNotification>, Initialisable {
	private volatile boolean brokerReady = false;

    @Override
    public void onNotification(MuleContextNotification notification) {
        int action = notification.getAction();
        if (action == MuleContextNotification.CONTEXT_STARTED) {
            brokerReady = true;
        } else if (action == MuleContextNotification.CONTEXT_STOPPED) {
            brokerReady = false;
        }
    }

    @Override
    public void initialise() throws InitialisationException {
        try {
            muleContext.registerListener(this);
        } catch (NotificationException ne) {
            throw new RuntimeException(ne);
        }
    }

    @Override
    public MuleEvent process(MuleEvent event) throws MuleException {
        if (!brokerReady) {
            throw new IllegalStateException("Invocation of service " + event.getFlowConstruct().getName() + " impossible at this time!");
        }

        return next.process(event);
    }
}
