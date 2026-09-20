package id.taryntang.seclass.jobcompare;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ListView whose scroll bar thumb is always a quarter of the track length,
 * instead of being proportional to the number of items.
 */
public class QuarterListView extends ListView {
    private static final int THUMB_FRACTION = 4;

    public QuarterListView(Context context) {
        super(context);
    }

    public QuarterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isScrollable() {
        return super.computeVerticalScrollRange() > super.computeVerticalScrollExtent();
    }

    @Override
    protected int computeVerticalScrollRange() {
        return isScrollable() ? super.computeVerticalScrollExtent() * THUMB_FRACTION
                : super.computeVerticalScrollRange();
    }

    @Override
    protected int computeVerticalScrollOffset() {
        if (!isScrollable()) {
            return super.computeVerticalScrollOffset();
        }
        long extent = super.computeVerticalScrollExtent();
        long realMax = super.computeVerticalScrollRange() - extent;
        long fakeMax = extent * (THUMB_FRACTION - 1);
        long offset = Math.max(0, Math.min(realMax, super.computeVerticalScrollOffset()));
        return (int) (offset * fakeMax / realMax);
    }
}
