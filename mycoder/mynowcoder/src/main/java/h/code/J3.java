package h.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class J3 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int M = sc.nextInt();
        int N = sc.nextInt();
        String X = sc.next();

        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 8);

        double[] res=new double[N];
        int[][] ints = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ints[i][j] = 0;

            }
            res[i]=M;
        }


        for (int i = 0; i < X.length(); i++) {
            for (int j = 0; j < N; j++) {
                int use = map.get(X.charAt(i)+"");
                if(res[j]>=use){
                    res[j]=res[j]-use;
                    int v = use;
                    for (int k = 0; k < M; k++) {
                        if(ints[j][k]!=1){
                            for (int l = k; l < k+v; l++) {
                                ints[j][l]=1;
                            }
                            break;
                        }
                    }
                }else {

                }
            }
            for (int y = 0; y < N; y++) {
                for (int j = 0; j < M; j++) {
                    System.out.print(ints[y][j]);
                }
                System.out.println();
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(ints[i][j]);
            }
            System.out.println();
        }



    }
}
