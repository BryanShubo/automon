package org.automon.monitors;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * Created by stevesouza on 2/26/15.
 */
public class Jamon implements OpenMon<Monitor> {

    @Override
    public Monitor start(String label) {
        return MonitorFactory.start(label);
    }

    @Override
    public void stop(Monitor mon) {
        mon.stop();
    }

    @Override
    public void exception(String label) {
        MonitorFactory.add(label, "Exception", 1);
    }

}