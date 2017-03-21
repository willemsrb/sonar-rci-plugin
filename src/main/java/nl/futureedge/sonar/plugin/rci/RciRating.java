package nl.futureedge.sonar.plugin.rci;

/**
 * Rules compliance rating.
 */
public final class RciRating {
    private final int a;
    private final int b;
    private final int c;
    private final int d;

    /**
     * Constructor.
     *
     * @param a
     *            minimal value for rating A
     * @param b
     *            minimal value for rating B
     * @param c
     *            minimal value for rating C
     * @param d
     *            minimal value for rating D
     */
    RciRating(final int a, final int b, final int c, final int d) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Determine the rating based on the rci.
     *
     * @param rci
     *            rci
     * @return rating
     */
    public int getRating(final double rci) {
        final int result;

        if (rci >= a) {
            result = 1;
        } else if (rci >= b) {
            result = 2;
        } else if (rci >= c) {
            result = 3;
        } else if (rci >= d) {
            result = 4;
        } else {
            result = 5;
        }
        return result;
    }
}