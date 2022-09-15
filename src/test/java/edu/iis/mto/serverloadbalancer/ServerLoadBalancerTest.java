package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class ServerLoadBalancerTest {
    @Test
    public void itCompiles() {
        assertThat(true, equalTo(true));
    }

    @Test
    public void balancingServerWithNoVms_serverStaysEmpty() {
        Server theServer = a(ServerBuilder.server().withCapacity(1));
        balancing(aServerListWith(theServer), anEmptyListOfVms());
        assertThat(theServer, CurrentLoadPercentageMatcher.hasCyrrentLoadOf(0.0d));
    }

    @Test
    public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsServerWithTheVm() {
        Server theServer = a(ServerBuilder.server().withCapacity(1));
        Vm theVm = a(VmBuilder.vm().ofSize(1));
        balancing(aServerListWith(theServer), aVmsListWith(theVm));
        assertThat(theServer, CurrentLoadPercentageMatcher.hasCyrrentLoadOf(100.0d));
        assertThat("server should contain the vm", theServer.contains(theVm));
    }

    @Test
    public void balancingOneServerWithTenSlotsCapacity_andOneSlotVm_fillsTheServerWithTenPercent() {
        Server theServer = a(ServerBuilder.server().withCapacity(10));
        Vm theVm = a(VmBuilder.vm().ofSize(1));
        balancing(aServerListWith(theServer), aVmsListWith(theVm));
        assertThat(theServer, CurrentLoadPercentageMatcher.hasCyrrentLoadOf(10.0d));
        assertThat("server should contain the vm", theServer.contains(theVm));
    }

    @Test
    public void balancingServerWithEnoughRoom_fillsTheServerWithAllVms() {
        Server theServer = a(ServerBuilder.server().withCapacity(100));
        Vm theFirstVm = a(VmBuilder.vm().ofSize(1));
        Vm theSecondVm = a(VmBuilder.vm().ofSize(1));
        balancing(aServerListWith(theServer), aVmsListWith(theFirstVm, theSecondVm));
        assertThat(theServer, ServerVmsCountMatcher.hasAVmsCountOf(2));
        assertThat("server should contain the first vm", theServer.contains(theFirstVm));
        assertThat("server should contain the second vm", theServer.contains(theSecondVm));
    }

    private Vm[] aVmsListWith(Vm... vms) {
        return vms;
    }

    private void balancing(Server[] servers, Vm[] vms) {
        new ServerLoadBalancer().balance(servers, vms);
    }

    private Vm[] anEmptyListOfVms() {
        return new Vm[0];
    }

    private Server[] aServerListWith(Server... servers) {
        return servers;
    }

    private <T> T a(Builder<T> builder) {
        return builder.build();
    }

}
