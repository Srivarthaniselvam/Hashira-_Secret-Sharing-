import java.math.BigInteger;
import java.util.*;

public class ShamirSecret {

    public static BigInteger interpolateAtZero(List<int[]> points) {
        BigInteger result = BigInteger.ZERO;
        int k = points.size();
        for (int i = 0; i < k; i++) {
            int xi = points.get(i)[0];
            BigInteger yi = BigInteger.valueOf(points.get(i)[1]);
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;
            for (int j = 0; j < k; j++) {
                if (i == j) continue;
                int xj = points.get(j)[0];
                num = num.multiply(BigInteger.valueOf(-xj));
                den = den.multiply(BigInteger.valueOf(xi - xj));
            }
            result = result.add(yi.multiply(num).divide(den));
        }
        return result;
    }

    public static List<int[]> findWrongShares(List<int[]> shares, int k) {
        List<int[]> wrongShares = new ArrayList<>();
        BigInteger secret = null;
        for (int i = 0; i < shares.size(); i++) {
            List<int[]> subset = new ArrayList<>();
            for (int j = 0; j < shares.size(); j++) {
                if (i == j) continue;
                if (subset.size() < k) subset.add(shares.get(j));
            }
            BigInteger candidate = interpolateAtZero(subset);
            if (secret == null) secret = candidate;
            else if (!secret.equals(candidate)) wrongShares.add(shares.get(i));
        }
        return wrongShares;
    }

    public static void main(String[] args) {
        int k1 = 3;
        List<int[]> shares1 = new ArrayList<>();
        shares1.add(new int[]{1, 4});
        shares1.add(new int[]{2, 7});
        shares1.add(new int[]{3, 12});
        shares1.add(new int[]{6, 39});
        BigInteger secret1 = interpolateAtZero(shares1.subList(0, k1));
        System.out.println("Secret1 = " + secret1);
        List<int[]> wrong1 = findWrongShares(shares1, k1);
        for (int[] ws : wrong1) System.out.println("Wrong1: x=" + ws[0] + ", y=" + ws[1]);

        int k2 = 3;
        List<int[]> shares2 = new ArrayList<>();
        shares2.add(new int[]{1, 5});
        shares2.add(new int[]{2, 11});
        shares2.add(new int[]{3, 19});
        shares2.add(new int[]{4, 33});
        shares2.add(new int[]{5, 50});
        BigInteger secret2 = interpolateAtZero(shares2.subList(0, k2));
        System.out.println("Secret2 = " + secret2);
        List<int[]> wrong2 = findWrongShares(shares2, k2);
        for (int[] ws : wrong2) System.out.println("Wrong2: x=" + ws[0] + ", y=" + ws[1]);
    }
}
