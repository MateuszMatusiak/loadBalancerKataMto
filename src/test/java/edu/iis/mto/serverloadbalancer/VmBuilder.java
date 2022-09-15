package edu.iis.mto.serverloadbalancer;

public class VmBuilder implements Builder<Vm> {
    private int size;

    public static VmBuilder vm() {
        return new VmBuilder();
    }

    public Vm build() {
        return new Vm(size);
    }

    public VmBuilder ofSize(int size) {
        this.size = size;
        return this;
    }


}
