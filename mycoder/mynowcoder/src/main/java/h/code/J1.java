package h.code;

import java.util.*;

public class J1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine());
        Set<Integer> index = new TreeSet<>();
        List<C> all = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] line = sc.nextLine().split(",");

            C c = new C();
            c.setId(Integer.parseInt(line[0]));
            c.setTime(Integer.parseInt(line[1]));
            c.setDistance(Integer.parseInt(line[2]));
            c.setAnumber(line[3]);
            c.setRnumber(line[4]);
            all.add(c);
            if (!c.getAnumber().equals(c.getRnumber())) {
                //记录第一种错误
                index.add(i);
            }
        }

        //处理第二种错误


        for (int i = 0; i < all.size() - 1; i++) {
            for (int j = i+1; j < all.size(); j++) {
                //去重
                if (Math.abs(all.get(i).getTime() - all.get(j).getTime()) < 60 &&
                        Math.abs(all.get(i).getDistance() - all.get(j).getDistance()) > 5) {
                    index.add(i);
                    index.add(j);
                }
            }
        }
        //error去重
        //

        int i = 1;
        if (index.size() > 0) {
            String s = "";
            for (int j = 0; j < all.size(); j++) {
                if (index.contains(j)) {
                    s = s + all.get(j).toString() + ";";
                }
            }
            s = s.substring(0, s.length()-1);
            System.out.println(s);

        } else {
            System.out.println("null");
        }

    }

}

class C {


    private int id;
    private int time;
    private int distance;
    private String anumber;
    private String rnumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getAnumber() {
        return anumber;
    }

    public void setAnumber(String anumber) {
        this.anumber = anumber;
    }

    public String getRnumber() {
        return rnumber;
    }

    public void setRnumber(String rnumber) {
        this.rnumber = rnumber;
    }

    @Override
    public String toString() {
        return
                id + "," + time +
                        "," + distance +
                        "," + anumber +
                        "," + rnumber
                ;
    }
}
