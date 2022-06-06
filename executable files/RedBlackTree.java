import java.util.Scanner;

class Node{
    int data;
    boolean color;//false-RED; true-BLACK
    Node left,right,parent;
    
    Node(int data,boolean color,Node left,Node right,Node parent){
        this.data = data;
        this.color = color;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
}

class RedBlackTree{
    Node root;
    int black_height;
    int sc;
    
    RedBlackTree(Node root){
        this.root = root;
        this.black_height=0;
        this.sc=20;
    }
    public void print_subtree(Node current,int space){
        if(current==null)return;
        space+=sc;
        print_subtree(current.right,space);
        for(int i=sc;i<space;i++) System.out.print(" ");
        if(current.color) System.out.println(current.data+"(B)");
        else System.out.println(current.data+"(R)");
        print_subtree(current.left,space);
    }
    public void print_tree(){
        print_subtree(root,0);
    }
    public void swap_colors(Node a, Node b){
        boolean x = a.color;
        a.color = b.color;
        b.color = x;
    }
    public void right_rotate(Node current){
        if(current==null)return;
        if(current.left==null)return;
        Node parent=current.parent;
        Node left=current.left;
        Node left_right = current.left.right;
        if(parent!=null){
            if(parent.left==current) parent.left=left;
            else parent.right=left;
        }
        left.parent=parent;
        if(left_right!=null) left_right.parent=current;
        current.left=left_right;
        left.right=current;
        current.parent=left;
        if(root==current){
            root=left;
        }
    }
    public void left_rotate(Node current){
        if(current==null)return;
        if(current.right==null)return;
        Node parent=current.parent;
        Node right=current.right;
        Node right_left = current.right.left;
        if(parent!=null){
            if(parent.left==current) parent.left=right;
            else parent.right=right;
        }
        right.parent=parent;
        if(right_left!=null) right_left.parent=current;
        current.right=right_left;
        right.left=current;
        current.parent=right;
        if(root==current){
            root=right;
        }
    }
    public void insert_fixup(Node newnode){
        if(root==newnode){
            root.color=true;
            black_height++;
        }
        else if(newnode.parent.color==false){
            Node parent = newnode.parent;
            Node grandparent = parent.parent;
            Node uncle;
            if(parent==grandparent.left){
                uncle=grandparent.right;
            }
            else{
                uncle=grandparent.left;
            }
            if(uncle==null || uncle.color==true){
                if(grandparent.left==parent && parent.left==newnode){
                    right_rotate(grandparent);
                    swap_colors(parent,grandparent);
                }
                else if(grandparent.left==parent && parent.right==newnode){
                    left_rotate(parent);
                    right_rotate(grandparent);
                    swap_colors(parent,grandparent);
                }
                else if(grandparent.right==parent && parent.right==newnode){
                    left_rotate(grandparent);
                    swap_colors(parent,grandparent);
                }
                else{
                    right_rotate(parent);
                    left_rotate(grandparent);
                    swap_colors(parent,grandparent);
                }
            }
            else if(uncle.color==false){
                parent.color=true;
                uncle.color=true;
                grandparent.color=false;
                insert_fixup(grandparent);
            }
        }
    }
    
    public boolean insert(Node newnode){
        if(root==null){
            root=newnode;
        }
        else{
            Node temp = root;
            Node prev = null;
            while(temp!=null){
                prev=temp;
                if(newnode.data>=temp.data){
                    temp=temp.right;
                }
                else{
                    temp=temp.left;
                }
            }
            newnode.parent=prev;
            if(newnode.data>=prev.data){
                prev.right=newnode;
            }
            else{
                prev.left=newnode;
            }
        }
        insert_fixup(newnode);
        return true;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        RedBlackTree rb = new RedBlackTree(null);
        System.out.print("Enter no. of insertions: ");
        int n = sc.nextInt();
        for(int i=0;i<n;i++){
            System.out.print("Enter a number: ");
            int c = sc.nextInt();
            Node x = new Node(c,false,null,null,null);
            rb.insert(x);
            rb.print_tree();
            System.out.println("\n");
        }
    }
}