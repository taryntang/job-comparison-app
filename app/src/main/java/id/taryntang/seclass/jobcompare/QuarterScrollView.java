package id.taryntang.seclass.jobcompare;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * ScrollView whose scroll bar thumb is always a quarter of the track length,
 * instead of being proportional to the content size.
 */
public class QuarterScrollView extends ScrollView {
    private static final int THUMB_FRACTION = 4;

    public QuarterScrollView(Context context) {
        super(context);
    }

    public QuarterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuarterScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        return (int) (super.computeVerticalScrollOffset() * fakeMax / realMax);
    }
}
