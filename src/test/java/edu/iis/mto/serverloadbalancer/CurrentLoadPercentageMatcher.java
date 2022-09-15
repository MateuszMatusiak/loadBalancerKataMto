package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {
    private double expectedLoadPercentage;

    public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
        this.expectedLoadPercentage = expectedLoadPercentage;
    }

    public static CurrentLoadPercentageMatcher hasCyrrentLoadOf(double expectedLoadPercentage) {
        return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
    }

    @Override
    protected boolean matchesSafely(Server server) {
        return doublesAreEqual(expectedLoadPercentage, server.currentLoadPercentage);
    }

    private boolean doublesAreEqual(double d1, double d2) {
        return d1 == d2 || Math.abs(d1 - d2) < 0.01d;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a server with load percentage of ").appendValue(expectedLoadPercentage);
    }

}
