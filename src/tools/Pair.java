package tools;

import java.io.Serializable;

public class Pair<E, F> implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    public E left;
    public F right;
    
    public Pair(final E left, final F right) {
        this.left = left;
        this.right = right;
    }
    
    public E getLeft() {
        return this.left;
    }
    
    public F getRight() {
        return this.right;
    }
    
    @Override
    public String toString() {
        return this.left.toString() + ":" + this.right.toString();
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.left == null) ? 0 : this.left.hashCode());
        result = prime * result + ((this.right == null) ? 0 : this.right.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair)obj;
        if (this.left == null) {
            if (other.left != null) {
                return false;
            }
        }
        else if (!this.left.equals(other.left)) {
            return false;
        }
        if (this.right == null) {
            if (other.right != null) {
                return false;
            }
        }
        else if (!this.right.equals(other.right)) {
            return false;
        }
        return true;
    }
}
