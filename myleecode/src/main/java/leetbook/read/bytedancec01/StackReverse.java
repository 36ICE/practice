package leetbook.read.bytedancec01;

import java.util.Stack;

public class StackReverse {



    public static Object getreverse(Stack stack){
        if(stack.isEmpty()){
            return null;
        }
        Object pop = stack.pop();
        Object reverse = getreverse(stack);
        if(reverse!=null){
            stack.push(reverse);
        }
        return pop;
    }
    public static void reverse(Stack stack) {

        Object reverse = getreverse(stack);
        if(reverse!=null){
            stack.push(reverse);
        }
    }

    public static void main(String[] args) {

        Stack stack = new Stack();


        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.forEach(System.out::print);

        reverse(stack);
        stack.forEach(System.out::print);

    }


}
