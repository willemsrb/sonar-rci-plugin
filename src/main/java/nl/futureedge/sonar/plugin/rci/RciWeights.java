package nl.futureedge.sonar.plugin.rci;

/**
 * Issue weights.
 */
public final class RciWeights {
    private final int blocker;
    private final int critical;
    private final int major;
    private final int minor;
    private final int info;

    /**
     * Constructor.
     *
     * @param blocker
     *            weight of a blocker issue
     * @param critical
     *            weight of a critical issue
     * @param major
     *            weight of a major issue
     * @param minor
     *            weight of a minor issue
     * @param info
     *            weight of a info issue
     */
    RciWeights(final int blocker, final int critical, final int major, final int minor, final int info) {
        super();
        this.blocker = blocker;
        this.critical = critical;
        this.major = major;
        this.minor = minor;
        this.info = info;
    }

    /**
     * @return weight of a blocker issue
     */
    public int getBlocker() {
        return blocker;
    }

    /**
     * @return weight of a critical issue
     */
    public int getCritical() {
        return critical;
    }

    /**
     * @return weight of a major issue
     */
    public int getMajor() {
        return major;
    }

    /**
     * @return weight of a minor issue
     */
    public int getMinor() {
        return minor;
    }

    /**
     * @return weight of a info issue
     */
    public int getInfo() {
        return info;
    }
}