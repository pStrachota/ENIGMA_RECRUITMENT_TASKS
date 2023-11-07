package pl.strachota;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Tree {
    private int age;
    private int height;
    private Trunk trunk;
    private Branches branches;
    private Leaves leaves;

    protected Tree(int age, int height, Trunk trunk, Branches branches, Leaves leaves) {
        this.age = age;
        this.height = height;
        this.trunk = trunk;
        this.branches = branches;
        this.leaves = leaves;
    }

    public abstract void grow();
    public abstract void bloom();
}

@Data
class Trunk {
    private int diameter;

    public Trunk(int diameter) {
        this.diameter = diameter;
    }
}

@Data
class Branches {
    private int count;

    public Branches(int count) {
        this.count = count;
    }
}

@Data
class Leaves {
    private int count;

    public Leaves(int count) {
        this.count = count;
    }
}