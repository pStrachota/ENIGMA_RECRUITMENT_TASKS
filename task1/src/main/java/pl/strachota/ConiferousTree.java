package pl.strachota;

import lombok.Data;

@Data
public class ConiferousTree extends Tree {

    public ConiferousTree(int age, int height, Trunk trunk, Branches branches, Leaves leaves) {
        super(age, height, trunk, branches, leaves);
    }

    @Override
    public void grow() {
        System.out.println("Coniferous tree is growing.");
    }

    @Override
    public void bloom() {
        System.out.println("Coniferous tree is blooming.");
    }

    public void produceCones() {
        System.out.println("Cones are produced by the coniferous tree.");
    }

}