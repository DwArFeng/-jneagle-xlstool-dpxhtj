package com.jneagle.xlstool.dpxhtj.structure;

/**
 * 进度状态。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class ProgressStatus {

    public static final ProgressStatus IDLE = new ProgressStatus(100, 100);
    public static final ProgressStatus UNCERTAIN = new ProgressStatus(-1, 100);

    private final int current;
    private final int total;

    public ProgressStatus(int current, int total) {
        this.current = current;
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "ProgressStatus{" +
                "current=" + current +
                ", total=" + total +
                '}';
    }
}
