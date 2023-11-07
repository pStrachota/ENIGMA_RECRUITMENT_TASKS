package pl.strachota;

import lombok.Data;

@Data
public class DeciduousTree extends Tree {

    public DeciduousTree(int age, int height, Trunk trunk, Branches branches, Leaves leaves) {
        super(age, height, trunk, branches, leaves);
    }

    @Override
    public void grow() {
        System.out.println("Deciduous tree is growing.");
    }

    void shedLeaves() {
        System.out.println("Leaves are shed by the deciduous tree.");
    }

    @Override
    public void bloom() {
        System.out.println("Beautiful flowers are blooming on the deciduous tree.");
    }

}