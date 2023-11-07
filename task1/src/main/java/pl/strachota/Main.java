package pl.strachota;

public class Main {
    public static void main(String[] args) {
        Trunk deciduousTrunk = new Trunk(20);
        Branches deciduousBranches = new Branches(10);
        Leaves deciduousLeaves = new Leaves(100);

        DeciduousTree deciduousTree = new DeciduousTree(10, 5, deciduousTrunk, deciduousBranches, deciduousLeaves);

        Trunk coniferousTrunk = new Trunk(15);
        Branches coniferousBranches = new Branches(8);
        Leaves coniferousLeaves = new Leaves(0);

        ConiferousTree coniferousTree = new ConiferousTree(20, 8, coniferousTrunk, coniferousBranches, coniferousLeaves);

        deciduousTree.grow();
        coniferousTree.grow();

        System.out.println("Deciduous tree age: " + deciduousTree.getAge());
        System.out.println("Coniferous tree height: " + coniferousTree.getHeight());

        deciduousTree.bloom();
        coniferousTree.bloom();

        deciduousTree.shedLeaves();
        coniferousTree.produceCones();
    }
}