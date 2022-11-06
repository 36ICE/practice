package hw.code;


import java.util.Scanner;
import java.util.Stack;

/**
 *
 * 排列组合非递归实现
 *
 *采用树的非递归深度优先遍历(树的遍历)
 */
public class H16PermutationAndCombination_NonRecursive {


    private static void printstack(Stack<Integer> stack) {
        if (stack == null) {
            return;
        }
        for (int i = 0; i < stack.size(); i++) {
            System.out.print(stack.get(i));
            if (i < stack.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private static void DFS(int n, int m) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        while (!stack.empty()) {
            if (stack.size() == m) {
                printstack(stack);
                int topVar = stack.peek();
                if (topVar + 1 <= n) {
                    stack.pop();
                    stack.push(topVar + 1);
                } else {
                    stack.pop();
                    if (stack.empty()) {
                        return;
                    }
                    int sectopVar = stack.pop();
                    stack.push(sectopVar + 1);
                }
            } else {
                int topVar = stack.peek();
                if (topVar + 1 <= n) {
                    stack.push(topVar + 1);
                } else {
                    stack.pop();
                    if (stack.empty()) {
                        return;
                    }
                    int sectopVar = stack.pop();
                    stack.push(sectopVar + 1);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            DFS(n, m);
        }
    }

}
