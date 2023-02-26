import java.util.*;
//Implement Huffman encoding and decoding.

//Huffman tree node
class MinHeapNode{
    char data;
    int freq;
    MinHeapNode left,right;

    MinHeapNode(char data,int freq){
        left=right=null;
        this.data=data;
        this.freq=freq;
    }
}

//For comparison of two nodes.
class Compare implements Comparator<MinHeapNode>{
    public int compare(MinHeapNode l,MinHeapNode r){
        return l.freq-r.freq;
    }

}


public class Q_N_6_a {
    //Print Huffman Codes
    static void printCodes(MinHeapNode root,String str){
        //If root is Null then return.
        if(root==null){
            return;
        }
        //If the node's data is not '$' that means it's not an internal node and print the string.
        if(root.data!='$'){
            System.out.println(root.data+": "+str);
        }

        printCodes(root.left,str+"0");
        printCodes(root.right,str+"1");
    }

    //Build Huffman Tree
    static void HuffmanCodes(char data[],int freq[],int size){

        MinHeapNode left,right,top,tmp;

        //create a min heap.
        PriorityQueue<MinHeapNode> minheap = new PriorityQueue<>(size,new Compare());

        // For each character create a leaf node and insert each leaf node in the heap.
        for(int i=0;i<size;i++){
            minheap.add(new MinHeapNode(data[i],freq[i]));
        }

        //Iterate while size of min heap doesn't become 1
        while(minheap.size()!=1){
            //Extract two nodes from the heap.
            left = minheap.poll();

            right = minheap.poll();

            tmp = new MinHeapNode('$',left.freq+right.freq);
            tmp.left = left;
            tmp.right = right;

            minheap.add(tmp);
        }
        //Calling function to print the codes.
        printCodes(minheap.peek()," ");
    }

    public static void main(String[] args) {

        char arr[] = {'a','b','c','d','e'};
        int freq[] = {10,5,2,14,15};

        int size=5;
        HuffmanCodes(arr,freq,size);

    }

}
